package com.dextra.hp.controller;

import com.dextra.hp.consumer.SpellsFeignRepository;
import com.dextra.hp.entity.Spell;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spells")
public class SpellsController {

    private final SpellsFeignRepository spellsConsumer;

    public SpellsController(SpellsFeignRepository spellsConsumer) {
        this.spellsConsumer = spellsConsumer;
    }

    @GetMapping
    public List<Spell> getSpells(){
        return spellsConsumer.getSpells();
    }
}
