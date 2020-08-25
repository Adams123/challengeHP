package com.dextra.hp.controller;

import com.dextra.hp.consumer.CharactersRepository;
import com.dextra.hp.entity.HpCharacter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharactersControllers {

    public final CharactersRepository charactersConsumer;

    public CharactersControllers(CharactersRepository charactersConsumer) {
        this.charactersConsumer = charactersConsumer;
    }

    @GetMapping
    public List<HpCharacter> getCharacters(Pageable pageable){
        return charactersConsumer.getCharacters();
    }

    @GetMapping("/{id}")
    public HpCharacter findById(@PathVariable("id") String id) {
        return charactersConsumer.getCharacter(id);
    }
}
