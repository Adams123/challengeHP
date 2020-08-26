package com.dextra.hp.controller;

import com.dextra.hp.controller.dto.CharacterDTO;
import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.service.HpCharacterService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/characters")
public class CharactersControllers {

    private final HpCharacterService service;
    private final MessageSource messageSource;
    private final Validator characterDTOValidator;

    public CharactersControllers(HpCharacterService service, MessageSource messageSource,
                                 @Qualifier("characterDtoValidator") Validator characterDTOValidator) {
        this.service = service;
        this.messageSource = messageSource;
        this.characterDTOValidator = characterDTOValidator;
    }

    @InitBinder("characterRequest")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(characterDTOValidator);
    }

    @GetMapping
    public ResponseEntity<Page<HpCharacter>> getCharacters(Pageable pageable, @RequestParam Map<String, String> parameters){
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HpCharacter> findById(@PathVariable("id") String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.findCharacterById(id));
    }

    @PostMapping
    public ResponseEntity<HpCharacter> createCharacter(@Valid @RequestBody CharacterDTO dto) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.createCharacter(dto));
    }

    @PutMapping
    public ResponseEntity<HpCharacter> updateCharacter(@Valid @RequestBody CharacterDTO hpCharacter) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.updateCharacter(hpCharacter));
    }

    @DeleteMapping
    public ResponseEntity<String> updateCharacter(@RequestParam String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.deleteCharacter(id));
    }
}
