package com.dextra.hp.consumer;

import com.dextra.hp.entity.HouseName;
import org.springframework.stereotype.Component;

@Component
public class SortingHatFeignRepo {

    private final SortingHatConsumer sortingHatConsumer;

    public SortingHatFeignRepo(SortingHatConsumer sortingHatConsumer) {
        this.sortingHatConsumer = sortingHatConsumer;
    }

    public HouseName getSortingHat(){
        return sortingHatConsumer.getSortingHat();
    }
}
