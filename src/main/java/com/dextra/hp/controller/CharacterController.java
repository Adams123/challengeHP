package com.dextra.hp.controller;

import com.dextra.hp.controller.request.CharacterRequestDTO;
import com.dextra.hp.controller.response.CharacterResponseDTO;
import com.dextra.hp.controller.validators.CharacterDTOValidator;
import com.dextra.hp.exception.ApiError;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.service.HpCharacterService;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/characters")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
})
@Api(value = "Character API", tags = {"Character controller"})
public class CharacterController {

    private final HpCharacterService service;
    private final CharacterDTOValidator characterDTOValidator;

    public CharacterController(HpCharacterService service,
                               CharacterDTOValidator characterDTOValidator) {
        this.service = service;
        this.characterDTOValidator = characterDTOValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(characterDTOValidator);
    }

    @GetMapping
    @Operation(summary = "Find all characters with or without sort and filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of characters",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CharacterResponseDTO.class)))})
    })
    public ResponseEntity<Page<CharacterResponseDTO>> getCharacters(Pageable pageable,
                                                                    @RequestParam(required = false) Map<String, String> params) {
        return ResponseEntity.ok(service.findAll(pageable, params));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find character by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the character",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CharacterResponseDTO.class))}),
            @ApiResponse(responseCode = "403", description = "Unauthorized access to deleted entity",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))})
    })
    public ResponseEntity<CharacterResponseDTO> findById(@PathVariable("id") String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.findCharacterByIdAsDto(id));
    }

    @PostMapping
    @Operation(summary = "Create a new character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created the character",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CharacterResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
    })
    public ResponseEntity<CharacterResponseDTO> createCharacter(@Valid @RequestBody CharacterRequestDTO dto) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.createCharacter(dto));
    }

    @PutMapping
    @Operation(summary = "Update a character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the character",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CharacterResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "403", description = "Unauthorized access to deleted entity",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))})
    })
    public ResponseEntity<CharacterResponseDTO> updateCharacter(@Valid @RequestBody CharacterRequestDTO hpCharacter) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.updateCharacter(hpCharacter));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted character",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "403", description = "Unauthorized access to deleted entity",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))})
    })
    public ResponseEntity<String> deleteCharacter(@PathVariable("id") String id) throws UnauthorizedEntityAccessException {
        return ResponseEntity.ok(service.deleteCharacter(id));
    }
}
