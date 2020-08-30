package com.dextra.hp.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterRequestDTO {
    private String id;
    private String __v;
    private String name;
    private String role;
    private String house;
    private String school;
    private String patronus;
}
