package com.dextra.hp.entity;

import com.dextra.hp.entity.converter.SetConverterString;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class House extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private HouseName name;
    private String mascot;
    private String headOfHouse;
    private String houseGhost;
    private String founder;
    private String school;

    @Transient
    List<String> members;
    @OneToMany(cascade = MERGE, mappedBy = "belongingHouse", orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    Set<HpCharacter> persistedMembers;
    @Column
    @Convert(converter = SetConverterString.class)
    Set<String> values;
    @Column
    @Convert(converter = SetConverterString.class)
    Set<String> colors;

    public Set<HpCharacter> getMembers(){
        return persistedMembers;
    }

    public void addMember(HpCharacter character) {
        if(CollectionUtils.isEmpty(persistedMembers)) {
            persistedMembers = new LinkedHashSet<>();
        }
        persistedMembers.add(character);
        character.setBelongingHouse(this);
    }
}
