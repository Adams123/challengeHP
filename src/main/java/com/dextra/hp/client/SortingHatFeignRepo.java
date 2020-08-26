package com.dextra.hp.client;

import com.dextra.hp.entity.HouseName;
import org.springframework.stereotype.Component;

@Component
public class SortingHatFeignRepo {

    private final SortingHatClient sortingHatClient;

    public SortingHatFeignRepo(SortingHatClient sortingHatClient) {
        this.sortingHatClient = sortingHatClient;
    }

    public HouseName getSortingHat(){
        return sortingHatClient.getSortingHat();
    }
}
