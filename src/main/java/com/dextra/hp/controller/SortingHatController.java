package com.dextra.hp.controller;

import com.dextra.hp.client.SortingHatFeignRepo;
import com.dextra.hp.entity.HouseName;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sortingHat")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
})
@Api(value = "Sorting Hat API", tags = {"Sorting Hat controller"})
public class SortingHatController {

    public final SortingHatFeignRepo sortingHatClient;

    public SortingHatController(SortingHatFeignRepo sortingHatClient) {
        this.sortingHatClient = sortingHatClient;
    }

    @GetMapping
    @Operation(summary = "Get a random house name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Random house name",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = HouseName.class))}),
    })
    public ResponseEntity<HouseName> getSortingHat(){
        return ResponseEntity.ok(sortingHatClient.getSortingHat());
    }
}
