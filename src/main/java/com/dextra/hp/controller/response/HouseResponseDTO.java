package com.dextra.hp.controller.response;

import com.dextra.hp.entity.House;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseResponseDTO {
    private String name;
    private String school;
    private String id;
    private List<String> members;
    private Set<String> values;
    private Set<String> colors;

    public HouseResponseDTO(House house) {
        this.name = house.getName().name();
        this.school = house.getSchool();
        this.id = house.get_id();
        this.members = house.getMembers();
        this.values = house.getValues();
        this.colors = house.getColors();
    }
}
