package com.dextra.hp.controller;

import com.dextra.hp.entity.Spell;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.service.SpellService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spells")
public class SpellsController {
    //TODO create DTOS to return, add bean validation

    private final SpellService spellService;

    public SpellsController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping
    public ResponseEntity<Page<Spell>> getSpells(Pageable pageable){
        return ResponseEntity.ok(spellService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spell> getSpell(String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(spellService.findBySpellid(id));
    }
}
