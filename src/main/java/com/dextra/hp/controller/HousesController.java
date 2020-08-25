package com.dextra.hp.controller;

import com.dextra.hp.consumer.HousesRepository;
import com.dextra.hp.entity.House;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HousesController {

    private final HousesRepository houseConsumer;

    public HousesController(HousesRepository houseConsumer) {
        this.houseConsumer = houseConsumer;
    }

    @GetMapping
    public List<House> getHouses(){
        return houseConsumer.getHouses();
    }

    @GetMapping("/{id}")
    public House findHouseById(@PathVariable("id") String id) {
        return houseConsumer.getHouse(id);
    }

}
