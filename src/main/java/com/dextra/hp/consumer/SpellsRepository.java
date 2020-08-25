package com.dextra.hp.consumer;

import com.dextra.hp.entity.Spell;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpellsRepository {

    private final SpellsConsumer spellsConsumer;

    public SpellsRepository(SpellsConsumer spellsConsumer) {
        this.spellsConsumer = spellsConsumer;
    }

    public List<Spell> getSpells(){
        return spellsConsumer.getSpells();
    }
}
