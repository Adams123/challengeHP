package com.dextra.hp.consumer;

import com.dextra.hp.entity.Spell;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpellsFeignRepository {

    private final SpellsConsumer spellsConsumer;

    public SpellsFeignRepository(SpellsConsumer spellsConsumer) {
        this.spellsConsumer = spellsConsumer;
    }

    public List<Spell> getSpells(){
        return spellsConsumer.getSpells();
    }
}
