package com.dextra.hp.consumer;

import com.dextra.hp.entity.HpCharacter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "hp-characters", url = "https://www.potterapi.com/v1/characters")
public interface CharactersConsumer {

    @GetMapping
    List<HpCharacter> getCharacters();

    @GetMapping("/{id}")
    HpCharacter getCharacter(@PathVariable("id") String id);
}
