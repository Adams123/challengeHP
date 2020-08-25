package com.dextra.hp.controller;

import com.dextra.hp.consumer.SortingHatRepository;
import com.dextra.hp.entity.HouseName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sortingHat")
public class SortingHatController {

    public final SortingHatRepository sortingHatConsumer;

    public SortingHatController(SortingHatRepository sortingHatConsumer) {
        this.sortingHatConsumer = sortingHatConsumer;
    }

    @GetMapping
    public HouseName getSortingHat(){
        return sortingHatConsumer.getSortingHat();
    }
}
