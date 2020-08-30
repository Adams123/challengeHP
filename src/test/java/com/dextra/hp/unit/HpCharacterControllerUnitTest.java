package com.dextra.hp.unit;

import com.dextra.hp.config.AppConfig;
import com.dextra.hp.controller.CharacterController;
import com.dextra.hp.controller.request.CharacterRequestDTO;
import com.dextra.hp.controller.validators.CharacterDTOValidator;
import com.dextra.hp.exception.GlobalControllerExceptionHandler;
import com.dextra.hp.service.HpCharacterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {CharacterController.class, CharacterDTOValidator.class,
        GlobalControllerExceptionHandler.class})
@WebMvcTest
@Import(AppConfig.class)
class HpCharacterControllerUnitTest{

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HpCharacterService hpCharacterService;

    @Test
    void idCannotContainSpace() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CharacterRequestDTO dto = CharacterRequestDTO.builder().name("some name").id("some id").build();

        String expectedMessage = messageSource.getMessage("id.space", new String[]{"id"}, Locale.getDefault());

        mockMvc.perform(post("/api/characters")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.[0]").value(expectedMessage));
    }

    @Test
    void nameIsRequired() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CharacterRequestDTO dto = CharacterRequestDTO.builder().id("some_id").build();

        String expectedMessage = messageSource.getMessage("field.required", new String[]{"name"}, Locale.getDefault());

        mockMvc.perform(post("/api/characters")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0]").value(expectedMessage));
    }

}
