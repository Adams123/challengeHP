package com.dextra.hp.client;

import com.dextra.hp.entity.HouseName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "hp-sortingHat", url = "https://www.potterapi.com/v1/sortingHat")
public interface SortingHatClient {

    @GetMapping
    HouseName getSortingHat();
}
