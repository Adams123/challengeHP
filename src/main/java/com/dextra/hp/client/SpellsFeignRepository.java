package com.dextra.hp.client;

import com.dextra.hp.entity.Spell;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpellsFeignRepository {

    private final SpellsClient spellsClient;

    public SpellsFeignRepository(SpellsClient spellsClient) {
        this.spellsClient = spellsClient;
    }

    public List<Spell> getSpells(){
        return spellsClient.getSpells();
    }
}
