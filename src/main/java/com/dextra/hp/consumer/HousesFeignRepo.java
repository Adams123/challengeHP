package com.dextra.hp.consumer;

import com.dextra.hp.entity.House;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HousesFeignRepo {

    private final HousesConsumer housesConsumer;

    public HousesFeignRepo(HousesConsumer housesConsumer) {
        this.housesConsumer = housesConsumer;
    }

    public House getHouse(String id){
        return housesConsumer.getHouse(id);
    }

    public List<House> getHouses(){
        return housesConsumer.getHouses();
    }
}
