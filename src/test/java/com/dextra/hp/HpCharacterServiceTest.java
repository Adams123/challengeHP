package com.dextra.hp;

import com.dextra.hp.controller.response.CharacterResponseDTO;
import com.dextra.hp.entity.House;
import com.dextra.hp.entity.HouseName;
import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.repository.HouseRepository;
import com.dextra.hp.repository.HpCharacterRepository;
import com.dextra.hp.service.HpCharacterService;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class HpCharacterServiceTest extends BaseIntegrationTest {

    @Autowired
    private HpCharacterRepository repository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HpCharacterService service;


    @Test
    public void deletedCharacterIsNotDisplayed() {
        HpCharacter hpCharacter = new HpCharacter();
        hpCharacter.set_id("some id");
        hpCharacter.setDeleted(true);
        HpCharacter saved = repository.save(hpCharacter);

        Page<CharacterResponseDTO> hpCharacters = service.findAll(PageRequest.of(0, 10), null);
        assertThat(hpCharacters.getTotalElements()).isZero();

        saved.setDeleted(false);
        repository.save(saved);

        hpCharacters = service.findAll(PageRequest.of(0, 10), null);
        assertThat(hpCharacters.getTotalElements()).isOne();
        assertThat(hpCharacters.getContent().get(0).getId()).isEqualTo(hpCharacter.get_id());
    }

    @Test
    public void characterIsFilteredByHouse(){
        House house = new House();
        house.set_id("house id");
        house.setName(HouseName.Gryffindor);
        HpCharacter hpCharacter = new HpCharacter();
        hpCharacter.set_id("some id");
        house.addMember(repository.save(hpCharacter));
        houseRepository.save(house);

        Map<String, String> params = new HashMap<>();
        params.put("house", house.get_id());
        Page<CharacterResponseDTO> characters = service.findAll(PageRequest.of(0, 10), params);
        assertThat(characters.getTotalElements()).isOne();
        assertThat(characters.getContent().get(0).getId()).isEqualTo(hpCharacter.get_id());

        params.replace("house", "random id");
        characters = service.findAll(PageRequest.of(0, 10), params);
        assertThat(characters.getTotalElements()).isZero();
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/characters.xml")
    public void bla(){
        assertTrue(repository.findById("some id").isPresent());
    }

    @Test
    public void wrongFilterIsIgnored(){
        House house = new House();
        house.set_id("house id");
        house.setName(HouseName.Gryffindor);
        HpCharacter hpCharacter = new HpCharacter();
        hpCharacter.set_id("some id");
        house.addMember(repository.save(hpCharacter));
        houseRepository.save(house);

        Map<String, String> params = new HashMap<>();
        params.put("houseId", house.get_id());
        Page<CharacterResponseDTO> characters = service.findAll(PageRequest.of(0, 10), params);
        assertThat(characters.getTotalElements()).isOne();
    }

}
