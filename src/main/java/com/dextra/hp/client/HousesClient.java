package com.dextra.hp.client;

import com.dextra.hp.entity.House;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "hp-houses", url = "https://www.potterapi.com/v1/houses")
public interface HousesClient {

    @GetMapping
    List<House> getHouses();

    @GetMapping("/{id}")
    House getHouse(@PathVariable("id") String id);

}
