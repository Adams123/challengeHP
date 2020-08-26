package com.dextra.hp.controller;

import com.dextra.hp.entity.House;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.service.HousesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/houses")
public class HousesController {

    private final HousesService housesService;

    public HousesController(HousesService housesService) {
        this.housesService = housesService;
    }

    @GetMapping
    public ResponseEntity<Page<House>> getHouses(Pageable pageable,
                                                 @RequestParam Map<String, String> searchParams){
        return ResponseEntity.ok(housesService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> findHouseById(@PathVariable("id") String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(housesService.findHouseById(id));
    }

}
