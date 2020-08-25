package com.dextra.hp.consumer;

import com.dextra.hp.entity.HouseName;
import org.springframework.stereotype.Component;

@Component
public class SortingHatRepository {

    private final SortingHatConsumer sortingHatConsumer;

    public SortingHatRepository(SortingHatConsumer sortingHatConsumer) {
        this.sortingHatConsumer = sortingHatConsumer;
    }

    public HouseName getSortingHat(){
        return sortingHatConsumer.getSortingHat();
    }
}
