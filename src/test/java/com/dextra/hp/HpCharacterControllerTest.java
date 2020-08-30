package com.dextra.hp;

import com.dextra.hp.controller.request.CharacterRequestDTO;
import com.dextra.hp.entity.House;
import com.dextra.hp.entity.HouseName;
import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.repository.HouseRepository;
import com.dextra.hp.repository.HpCharacterRepository;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dextra.hp.exception.ExceptionLocalization.DELETED_ENTITY_MESSAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class HpCharacterControllerTest extends BaseIntegrationTestingSetup {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HpCharacterRepository repository;

    @Autowired
    private MessageSource messageSource;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DataSet(value = "characters_controller.xml")
    public void findAllReturnsCorrectDTO() throws Exception {
        mockMvc.perform(get("/api/characters/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(3)) //should not return deleted
                .andExpect(jsonPath("$.content.[0].id").value("some_id"))
                .andExpect(jsonPath("$.content.[0].house").value(HouseName.Gryffindor.name()))
                .andExpect(jsonPath("$.content.[1].id").value("other_id"))
                .andExpect(jsonPath("$.content.[1].house").value(HouseName.Slytherin.name()))
                .andExpect(jsonPath("$.content.[2].id").value("third_id"))
                .andExpect(jsonPath("$.content.[2].house").value(HouseName.Ravenclaw.name()));

    }

    @Test
    @DataSet(value = "characters_controller.xml")
    public void filteringReturnsCorrectCharacters() throws Exception {
        mockMvc.perform(get("/api/characters/").queryParam("house", "slytherin_id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(jsonPath("$.content.[0].id").value("other_id"))
                .andExpect(jsonPath("$.content.[0].house").value(HouseName.Slytherin.name()));
    }

    @Test
    @DataSet(value = "characters_controller.xml")
    @Transactional
    public void filteringAndSortingReturnsCorrectCharacters() throws Exception {
        House slytherinHouse = houseRepository.findById("slytherin_id").get();
        List<String> names = Stream.of("one name", "two name", "dont know", "test name").collect(Collectors.toList());
        List<HpCharacter> characters = new ArrayList<>();
        for (String name : names) {
            characters.add(createCharacter(name, slytherinHouse));
        }
        houseRepository.save(slytherinHouse);
        repository.saveAll(characters);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        names.add("some other name");
        names.sort(Comparator.naturalOrder());

        mockMvc.perform(getBuilder().queryParam("page","0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.content.[0].name").value(names.get(0)))
                .andExpect(jsonPath("$.content.[1].name").value(names.get(1)));

        mockMvc.perform(getBuilder().queryParam("page","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.content.[0].name").value(names.get(2)))
                .andExpect(jsonPath("$.content.[1].name").value(names.get(3)));

        mockMvc.perform(getBuilder().queryParam("page","2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(jsonPath("$.content.[0].name").value(names.get(4)));

        mockMvc.perform(getBuilder().queryParam("page","3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(0));
    }

    @Test
    @DataSet(value = "characters_controller.xml")
    public void accessingDeletedEntityIsNotAllowed() throws Exception {
        String message = messageSource.getMessage(DELETED_ENTITY_MESSAGE, new String[]{"deleted_id"}, null, Locale.getDefault());
        mockMvc.perform(get("/api/characters/deleted_id"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").value(Objects.requireNonNull(message)));
    }

    @Test
    @DataSet(value = "characters_controller.xml")
    public void createCharacter() throws Exception {
        CharacterRequestDTO dto = CharacterRequestDTO.builder().id("random id").name("random name").house("ravenclaw_id").__v("0").build();

        mockMvc.perform(post("/api/characters")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(dto.getId()))
        .andExpect(jsonPath("$.house").value(HouseName.Ravenclaw.name()))
        .andExpect(jsonPath("$.name").value(dto.getName()));

        mockMvc.perform(get("/api/characters/" + dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }

    @Test
    @DataSet(value = "characters_controller.xml")
    public void cannotCreateCharacterWithoutName() throws Exception {
        CharacterRequestDTO dto = CharacterRequestDTO.builder().id("random id").house("ravenclaw_id").__v("0").build();
        String message = messageSource.getMessage("field.required", new String[]{"name"}, Locale.getDefault());

        mockMvc.perform(post("/api/characters")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0]").value(message));
    }

    private HpCharacter createCharacter(String name, House house) {
        HpCharacter character = new HpCharacter();
        character.set_id(UUID.randomUUID().toString());
        character.setName(name);
        character.defineBelongingHouse(house);
        return character;
    }

    private MockHttpServletRequestBuilder getBuilder(){
        return get("/api/characters/")
                .queryParam("house", "slytherin_id")
                .queryParam("sort", "name,asc")
                .queryParam("size", "2");
    }

}
