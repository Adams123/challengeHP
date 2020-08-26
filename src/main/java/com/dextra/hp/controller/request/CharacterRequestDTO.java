package com.dextra.hp.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CharacterRequestDTO {
    private String id;
    private String __v;
    private String name;
    private String role;
    private String house;
    private String school;
    private String patronus;
}
