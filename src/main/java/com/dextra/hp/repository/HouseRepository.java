package com.dextra.hp.repository;

import com.dextra.hp.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, String>, JpaSpecificationExecutor<String> {

    Optional<House> findHouseByName(String name);
}
