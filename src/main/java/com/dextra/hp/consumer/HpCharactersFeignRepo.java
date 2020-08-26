package com.dextra.hp.consumer;

import com.dextra.hp.entity.HpCharacter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HpCharactersFeignRepo {

    public final CharactersConsumer charactersConsumer;

    public HpCharactersFeignRepo(CharactersConsumer charactersConsumer) {
        this.charactersConsumer = charactersConsumer;
    }

    public List<HpCharacter> getCharacters() {
        return charactersConsumer.getCharacters();
    }

    public HpCharacter getCharacter(String id) {
        return charactersConsumer.getCharacter(id);
    }
}
