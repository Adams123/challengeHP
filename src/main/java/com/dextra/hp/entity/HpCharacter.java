package com.dextra.hp.entity;

import com.dextra.hp.controller.request.CharacterRequestDTO;
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
    private String house;
    private String school;
    private boolean ministryOfMagic;
    private boolean orderOfThePhoenix;
    private boolean dumbledoresArmy;
    private boolean deathEater;
    private String bloodStatus;
    private String species;
    private String patronus;
    private String boggart;
    private String wand;
    private String alias;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private House belongingHouse;

    public void defineBelongingHouse(House belongingHouse) {
        this.belongingHouse = belongingHouse;
        if(Objects.nonNull(belongingHouse))
            belongingHouse.addMember(   this);
    }

    public HpCharacter(CharacterRequestDTO dto) {
        this.set_id(StringUtils.defaultString(dto.getId(), UUID.randomUUID().toString()));
        this.set__v(Float.parseFloat(StringUtils.defaultString(dto.get__v(), "0")));
        this.setName(dto.getName());
        this.setRole(dto.getRole());
        this.setSchool(dto.getSchool());
        this.setPatronus(dto.getPatronus());
    }

    public void updateFromDto(CharacterRequestDTO dto) {
        if(StringUtils.isNotBlank(dto.get__v()))
            this.set__v(Float.parseFloat(dto.get__v()));

        this.setName(dto.getName());
        this.setRole(dto.getRole());
        this.setSchool(dto.getSchool());
        this.setPatronus(dto.getPatronus());
    }
}
