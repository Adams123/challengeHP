package com.dextra.hp.entity;

import com.dextra.hp.controller.dto.CharacterDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class HpCharacter extends BaseEntity{
    private String name;
    private String role;
    @Transient
    private HouseName house;
    private String school;
    private boolean ministryOfMagic;
    private boolean orderOfThePhoenix;
    private boolean dumbledoresArmy;
    private boolean deathEater;
    private String bloodStatus;
    private String species;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    @EqualsAndHashCode.Exclude
    private House belongingHouse;

    public void defineBelongingHouse(House belongingHouse) {
        this.belongingHouse = belongingHouse;
        if(Objects.nonNull(belongingHouse))
            belongingHouse.addMember(   this);
    }

    public HpCharacter(CharacterDTO dto) {
        this.set_id(StringUtils.defaultString(dto.getId(), UUID.randomUUID().toString()));
        this.set__v(Float.parseFloat(StringUtils.defaultString(dto.get__v(), "0")));
        this.setName(dto.getName());
        this.setRole(dto.getRole());
        this.setSchool(dto.getSchool());
        this.setMinistryOfMagic(dto.isMinistryOfMagic());
        this.setOrderOfThePhoenix(dto.isOrderOfThePhoenix());
        this.setDumbledoresArmy(dto.isDumbledoresArmy());
        this.setDeathEater(dto.isDeathEater());
        this.setBloodStatus(dto.getBloodStatus());
        this.setSpecies(dto.getSpecies());
    }

    public void updateFromDto(CharacterDTO dto) {
        if(StringUtils.isNotBlank(dto.get__v()))
            this.set__v(Float.parseFloat(dto.get__v()));

        this.setName(dto.getName());
        this.setRole(dto.getRole());
        this.setSchool(dto.getSchool());
        this.setMinistryOfMagic(dto.isMinistryOfMagic());
        this.setOrderOfThePhoenix(dto.isOrderOfThePhoenix());
        this.setDumbledoresArmy(dto.isDumbledoresArmy());
        this.setDeathEater(dto.isDeathEater());
        this.setBloodStatus(dto.getBloodStatus());
        this.setSpecies(dto.getSpecies());
    }
}
