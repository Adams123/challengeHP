package com.dextra.hp.controller;

import com.dextra.hp.consumer.SpellsRepository;
import com.dextra.hp.entity.Spell;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spells")
public class SpellsController {

    private final SpellsRepository spellsConsumer;

    public SpellsController(SpellsRepository spellsConsumer) {
        this.spellsConsumer = spellsConsumer;
    }

    @GetMapping
    public List<Spell> getSpells(){
        return spellsConsumer.getSpells();
    }
}
