package com.dextra.hp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class HpCharacter {
    @Id
    private String _id;
    private String name;
    private String role;
    private HouseName house;
    private String school;
    private float __v;
    private boolean ministryOfMagic;
    private boolean orderOfThePhoenix;
    private boolean dumbledoresArmy;
    private boolean deathEater;
    private String bloodStatus;
    private String species;
}
