package com.dextra.hp.client;

import com.dextra.hp.entity.House;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HousesFeignRepo {

    private final HousesClient housesClient;

    public HousesFeignRepo(HousesClient housesClient) {
        this.housesClient = housesClient;
    }

    public House getHouse(String id){
        return housesClient.getHouse(id);
    }

    public List<House> getHouses(){
        return housesClient.getHouses();
    }
}
