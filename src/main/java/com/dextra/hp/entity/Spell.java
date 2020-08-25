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
public class Spell {
    private String _id;
    private String spell;
    private String type;
    private String effect;
    private float __v;
}
