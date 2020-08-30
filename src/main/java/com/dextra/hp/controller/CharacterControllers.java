package com.dextra.hp.controller;

import com.dextra.hp.controller.request.CharacterRequestDTO;
import com.dextra.hp.controller.response.CharacterResponseDTO;
import com.dextra.hp.controller.validators.CharacterDTOValidator;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.service.HpCharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/characters")
public class CharacterControllers {

    private final HpCharacterService service;
    private final CharacterDTOValidator characterDTOValidator;

    public CharacterControllers(HpCharacterService service,
                                CharacterDTOValidator characterDTOValidator) {
        this.service = service;
        this.characterDTOValidator = characterDTOValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(characterDTOValidator);
    }

    @GetMapping
    public ResponseEntity<Page<CharacterResponseDTO>> getCharacters(Pageable pageable,
                                                                    @RequestParam(required = false) Map<String, String> params) {
        return ResponseEntity.ok(service.findAll(pageable, params));

    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDTO> findById(@PathVariable("id") String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.findCharacterByIdAsDto(id));
    }

    @PostMapping
    public ResponseEntity<CharacterResponseDTO> createCharacter(@Valid @RequestBody CharacterRequestDTO dto) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.createCharacter(dto));
    }

    @PutMapping
    public ResponseEntity<CharacterResponseDTO> updateCharacter(@Valid @RequestBody CharacterRequestDTO hpCharacter) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.updateCharacter(hpCharacter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable("id") String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.deleteCharacter(id));
    }
}
