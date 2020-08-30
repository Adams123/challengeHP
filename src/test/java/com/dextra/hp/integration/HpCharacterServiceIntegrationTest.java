package com.dextra.hp.integration;

import com.dextra.hp.client.HpCharactersFeignRepo;
import com.dextra.hp.controller.request.CharacterRequestDTO;
import com.dextra.hp.controller.response.CharacterResponseDTO;
import com.dextra.hp.entity.House;
import com.dextra.hp.entity.HouseName;
import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.repository.HouseRepository;
import com.dextra.hp.repository.HpCharacterRepository;
import com.dextra.hp.service.HpCharacterService;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.transaction.TestTransaction;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dextra.hp.exception.ExceptionLocalization.CHARACTER_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

class HpCharacterServiceIntegrationTest extends BaseIntegrationTestingSetup {

    @Autowired
    private HpCharacterRepository repository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HpCharacterService service;

    @MockBean
    private HpCharactersFeignRepo hpCharactersFeignRepo;


    @Test
    @DataSet(value = "characters.xml")
    void deletedCharacterIsNotDisplayed() {

        Page<CharacterResponseDTO> hpCharacters = service.findAll(PageRequest.of(0, 10), null);
        assertThat(hpCharacters.getTotalElements()).isOne();
        assertThat(hpCharacters.getContent().get(0).getId()).isEqualTo("some_id");

        HpCharacter deletedChar = repository.findById("deleted_id").get();
        deletedChar.setDeleted(false);
        repository.save(deletedChar);

        hpCharacters = service.findAll(PageRequest.of(0, 10), null);
        assertThat(hpCharacters.getTotalElements()).isEqualTo(2);
        assertThat(hpCharacters.getContent().stream().map(CharacterResponseDTO::getId).collect(Collectors.toList()))
                .containsExactlyInAnyOrder("some_id", "deleted_id");
    }

    @Test
    @DataSet(value = "characters.xml")
    void characterIsFilteredByHouse(){
        Map<String, String> params = new HashMap<>();
        params.put("house", "gryffindor_id");
        Page<CharacterResponseDTO> characters = service.findAll(PageRequest.of(0, 10), params);
        assertThat(characters.getTotalElements()).isOne();
        assertThat(characters.getContent().get(0).getId()).isEqualTo("some_id");

        params.replace("house", "random id");
        characters = service.findAll(PageRequest.of(0, 10), params);
        assertThat(characters.getTotalElements()).isZero();
    }

    @Test
    @DataSet(value = "characters.xml")
    void accessingDeletedEntityThrowsException() {
        Exception exception =
                assertThrows(UnauthorizedEntityAccessException.class, () -> service.findCharacterByIdAsDto("deleted_id"));
        assertThatEntityIsNotAllowedToBeAccessed("deleted_id", exception);
    }

    @Test
    @DataSet(value = "characters.xml")
    void createNewCharacter() throws UnauthorizedEntityAccessException {
        CharacterRequestDTO dto = CharacterRequestDTO.builder().id("new id").house("slytherin_id").name("new name").__v("1.0").build();
        CharacterResponseDTO created = service.createCharacter(dto);

        assertThat(created.getId()).isEqualTo(dto.getId());
        assertThat(created.getHouse()).isEqualTo(HouseName.Slytherin.name());

        Optional<HpCharacter> createdOpt = repository.findById("new id");
        assertThat(createdOpt).isPresent();
        HpCharacter createdFromDb = createdOpt.get();

        assertThat(createdFromDb.getBelongingHouse().get_id()).isEqualTo("slytherin_id");
        assertThat(createdFromDb.get_id()).isEqualTo("new id");
    }

    @Test
    @DataSet(value = "characters.xml")
    void updateNewCharacter() throws UnauthorizedEntityAccessException {
        CharacterRequestDTO dto = CharacterRequestDTO.builder().id("some_id").house("slytherin_id").name("new name").__v("1.0").build();
        CharacterResponseDTO updatedDto = service.updateCharacter(dto);

        assertThat(updatedDto.getHouse()).isEqualTo(HouseName.Slytherin.name());

        HpCharacter updated = repository.findById("some_id").get();
        assertThat(updated.getBelongingHouse().get_id()).isEqualTo(dto.getHouse());
        assertThat(updated.getName()).isEqualTo(dto.getName());
        assertThat(updated.get__v()).isEqualTo(Float.valueOf(dto.get__v()));
    }

    @Test
    @DataSet(value = "characters.xml")
    @Transactional
    void deleteCharacter() throws UnauthorizedEntityAccessException {
        House house = houseRepository.findById("gryffindor_id").get();
        HpCharacter character = house.getPersistedMembers().stream().findFirst().get();
        assertThat(character.get_id()).isEqualTo("some_id");
        String deleted = service.deleteCharacter("some_id");
        assertThat(deleted).isEqualTo("some name");

        assertThat(repository.findById("some_id").get().isDeleted()).isTrue();
        assertThat(service.findAll(PageRequest.of(0, 10), null).getTotalElements()).isZero();

        TestTransaction.flagForCommit(); // need this, otherwise the next line does a rollback
        TestTransaction.end();
        TestTransaction.start();

        house = houseRepository.findById("gryffindor_id").get();
        assertThat(house.getPersistedMembers()).isEmpty();

        Exception deleteAgainException =
                assertThrows(UnauthorizedEntityAccessException.class, () -> service.deleteCharacter("some_id"));
        assertThatEntityIsNotAllowedToBeAccessed("some_id", deleteAgainException);

        Exception unableToAccessException =
                assertThrows(UnauthorizedEntityAccessException.class, () -> service.findCharacterByIdAsDto("some_id"));
        assertThatEntityIsNotAllowedToBeAccessed("some_id", unableToAccessException);

    }

    @Test
    @DataSet(value = "characters.xml")
    void characterNotFoundOnDbIsFetchedFromAPI() throws UnauthorizedEntityAccessException {
        HpCharacter hpCharacter = new HpCharacter();
        hpCharacter.set_id("id from API");
        hpCharacter.setHouse("hufflepuff_id");
        doReturn(hpCharacter).when(hpCharactersFeignRepo).getCharacter("id from API");

        CharacterResponseDTO created = service.findCharacterByIdAsDto("id from API");

        assertThat(repository.findById("id from API")).isPresent();

        assertThat(created.getHouse()).isEqualTo(HouseName.Hufflepuff.name());
        assertThat(created.getId()).isEqualTo("id from API");
    }

    @Test
    @DataSet(value = "characters.xml")
    void characterNotFoundThrowsException(){
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.findCharacterByIdAsDto("no id"));
        String message = messageSource.getMessage(CHARACTER_NOT_FOUND_MESSAGE, new String[]{"no id"}, null, Locale.getDefault());
        assertThat(exception.getMessage()).isEqualTo(message);
    }

}
