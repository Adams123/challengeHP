package com.dextra.hp.entity;

import com.dextra.hp.entity.converter.SetConverterString;
import com.dextra.hp.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;

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
    @OneToMany(cascade = ALL, mappedBy = "belongingHouse", orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Where(clause = "deleted = false")
    Set<HpCharacter> persistedMembers;
    @Column
    @Convert(converter = SetConverterString.class)
    Set<String> values;
    @Column
    @Convert(converter = SetConverterString.class)
    Set<String> colors;

    @JsonValue
    public List<String> getMembers(){
        return Utils.orDefaultSet(persistedMembers).stream().map(HpCharacter::get_id).collect(Collectors.toList());
    }

    public void addMember(HpCharacter character) {
        if(CollectionUtils.isEmpty(persistedMembers)) {
            persistedMembers = new LinkedHashSet<>();
        }
        persistedMembers.add(character);
        character.setBelongingHouse(this);
    }

    public void setPersistedMembers(Set<HpCharacter> characters){
        for (HpCharacter character : Utils.orDefaultSet(characters)) {
            addMember(character);
        }
    }

}
