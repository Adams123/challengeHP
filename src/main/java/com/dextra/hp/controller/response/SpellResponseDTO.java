package com.dextra.hp.controller.response;

import com.dextra.hp.entity.Spell;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpellResponseDTO {

    private String id;
    private String spell;
    private String effect;
    private String type;
    
    public SpellResponseDTO(Spell spell) {
        this.id = spell.get_id();
        this.spell = spell.getSpell();
        this.effect = spell.getEffect();
        this.type = spell.getType();
    }
}
