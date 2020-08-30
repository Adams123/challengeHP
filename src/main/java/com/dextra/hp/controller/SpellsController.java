package com.dextra.hp.controller;

import com.dextra.hp.entity.Spell;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.service.SpellService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spells")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
})
@Api(value = "Spells API", tags = {"Spells controller"})
public class SpellsController {
    //TODO create DTOS to return, add bean validation

    private final SpellService spellService;

    public SpellsController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping
    @Operation(summary = "Find all spells with or without sort and filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of spells",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Spell.class)))})
    })
    public ResponseEntity<Page<Spell>> getSpells(Pageable pageable){
        return ResponseEntity.ok(spellService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find spell by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the spell",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Spell.class))}),
            @ApiResponse(responseCode = "403", description = "Unauthorized access to deleted entity",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))})
    })
    public ResponseEntity<Spell> getSpell(String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(spellService.findBySpellId(id));
    }
}
