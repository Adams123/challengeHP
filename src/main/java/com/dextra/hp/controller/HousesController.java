package com.dextra.hp.controller;

import com.dextra.hp.controller.response.HouseResponseDTO;
import com.dextra.hp.entity.House;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.service.HousesService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/houses")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
})
@Api(value = "Houses API", tags = {"Houses controller"})
public class HousesController {
    //TODO add bean validation

    private final HousesService housesService;

    public HousesController(HousesService housesService) {
        this.housesService = housesService;
    }

    @GetMapping
    @Operation(summary = "Find all houses with or without sort and filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of houses",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HouseResponseDTO.class)))})
    })
    public ResponseEntity<Page<HouseResponseDTO>> getHouses(Pageable pageable,
                                                            @RequestParam Map<String, String> searchParams){
        Page<House> houses = housesService.findAll(pageable);
        return ResponseEntity.ok(houses.map(HouseResponseDTO::new));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find house by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the house",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = HouseResponseDTO.class))}),
            @ApiResponse(responseCode = "403", description = "Unauthorized access to deleted entity",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))})
    })
    public ResponseEntity<HouseResponseDTO> findHouseById(@PathVariable("id") String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(new HouseResponseDTO(housesService.findHouseById(id)));
    }

}
