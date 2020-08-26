package com.dextra.hp.client;

import com.dextra.hp.entity.HpCharacter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HpCharactersFeignRepo {

    public final CharactersClient charactersClient;

    public HpCharactersFeignRepo(CharactersClient charactersClient) {
        this.charactersClient = charactersClient;
    }

    public List<HpCharacter> getCharacters() {
        return charactersClient.getCharacters();
    }

    public HpCharacter getCharacter(String id) {
        return charactersClient.getCharacter(id);
    }
}
