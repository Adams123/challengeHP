package com.dextra.hp.controller;

import com.dextra.hp.consumer.SortingHatFeignRepo;
import com.dextra.hp.entity.HouseName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sortingHat")
public class SortingHatController {

    public final SortingHatFeignRepo sortingHatConsumer;

    public SortingHatController(SortingHatFeignRepo sortingHatConsumer) {
        this.sortingHatConsumer = sortingHatConsumer;
    }

    @GetMapping
    public HouseName getSortingHat(){
        return sortingHatConsumer.getSortingHat();
    }
}
