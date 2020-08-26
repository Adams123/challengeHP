package com.dextra.hp.controller.response;

import com.dextra.hp.entity.HpCharacter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CharacterResponseDTO {

    private String id;
    private String name;
    private String role;
    private String school;
    private String house;
    private String patronus;

    public CharacterResponseDTO(HpCharacter hpCharacter) {
        this.id = hpCharacter.get_id();
        this.name = hpCharacter.getName();
        this.role = hpCharacter.getRole();
        this.school = hpCharacter.getSchool();
        if(hpCharacter.getBelongingHouse()!=null) {
            this.house = hpCharacter.getBelongingHouse().getName().name();
        }
        this.patronus = hpCharacter.getPatronus();
    }

}
