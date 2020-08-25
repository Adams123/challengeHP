package com.dextra.hp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class House {
    private String _id;
    private HouseName name;
    private String mascot;
    private String headOfHouse;
    private String houseGhost;
    private String founder;
    private float __v;
    private String school;
    List<String> members;
    List<String> values;
    List<String> colors;
}
