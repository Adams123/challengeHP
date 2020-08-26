package com.dextra.hp.client;

import com.dextra.hp.entity.Spell;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "hp-spells", url = "https://www.potterapi.com/v1/spells")
public interface SpellsClient {

    @GetMapping
    List<Spell> getSpells();

}
