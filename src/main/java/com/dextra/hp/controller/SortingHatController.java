package com.dextra.hp.controller;

import com.dextra.hp.client.SortingHatFeignRepo;
import com.dextra.hp.entity.HouseName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sortingHat")
public class SortingHatController {

    public final SortingHatFeignRepo sortingHatClient;

    public SortingHatController(SortingHatFeignRepo sortingHatClient) {
        this.sortingHatClient = sortingHatClient;
    }

    @GetMapping
    public ResponseEntity<HouseName> getSortingHat(){
        return ResponseEntity.ok(sortingHatClient.getSortingHat());
    }
}
