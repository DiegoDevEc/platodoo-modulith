package com.playtodoo.modulith.sportcomplex.presentation;

import com.playtodoo.modulith.sportcomplex.application.SportComplexService;
import com.playtodoo.modulith.sportcomplex.exception.ApiErrorResponse;
import com.playtodoo.modulith.sportcomplex.validation.LandingPageComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.SportComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.CreateSportComplexDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/sport-complexes")
@RequiredArgsConstructor
@Tag(name = "Sport Complex", description = "API para la gestión de complejos deportivos y reservas.")
public class SportComplexController {

    private final SportComplexService service;

    /**
     * Fetches a page of sport complexes
     *
     * @param page     the page number to fetch (0-indexed)
     * @param size     the number of sport complexes per page
     * @param sortBy   the field to sort the sport complexes by
     * @param direction the direction to sort the sport complexes
     * @return a page of sport complexes
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<SportComplexDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(service.findAll(page, size, sortBy, direction));
    }

    /**
     * Fetches a page of sport complexes that are closest to the given user position
     *
     * @param page     the page number to fetch (0-indexed)
     * @param size     the number of sport complexes per page
     * @param sortBy   the field to sort the sport complexes by
     * @param direction the direction to sort the sport complexes
     * @param latitude the latitude of the user's position
     * @param longitude the longitude of the user's position
     * @return a page of sport complexes
     */
    @GetMapping("/position-user")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Page<SportComplexDto>> findAllByPositionUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        //TODO: Metodo nuevo para buscar posicionamiento de complejos
        return ResponseEntity.ok(service.findAllByPositionUser(page, size, sortBy, direction, latitude, longitude));
    }

    /**
     * Fetches a sport complex by its ID
     *
     * @param id the ID of the sport complex to fetch
     * @return the sport complex with the given ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<SportComplexDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Creates a new sport complex.
     *
     * @param dto the data transfer object containing the details of the sport complex to create
     * @return the created sport complex
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<SportComplexDto> create(@Valid @RequestBody CreateSportComplexDto dto) {
        SportComplexDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Updates a sport complex with the given ID.
     *
     * @param id the ID of the sport complex to update
     * @param dto the data transfer object containing the updated details of the sport complex
     * @return the updated sport complex
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<SportComplexDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody CreateSportComplexDto dto) {

        SportComplexDto updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Updates the landing page configuration for a sport complex with the given ID.
     *
     * @param id the ID of the sport complex to update
     * @param landingPageConfig the JSON string containing the new landing page configuration
     * @return the updated sport complex
     */
    @PatchMapping("/{id}/landing-page-config")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<SportComplexDto> updateLandingPageConfig(
            @PathVariable UUID id,
            @RequestBody String landingPageConfig) {

        SportComplexDto updated = service.updateLandingPageConfig(id, landingPageConfig);
        return ResponseEntity.ok(updated);
    }

    /**
     * Returns the landing page configuration for a sport complex with the given ID.
     *
     * @param id the ID of the sport complex to fetch the landing page configuration for
     * @return the landing page configuration as a structured object
     */
    @GetMapping("/{id}/landing-page")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(
            summary = "Obtener configuración de la landing page del complejo",
            description = "Retorna la configuración de la landing page deserializada como un objeto estructurado.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID del complejo deportivo",
                            required = true,
                            example = "b7f86e2a-3a7b-4a90-b1f8-9a0d8c0e4a0c"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Configuración de la landing page obtenida con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LandingPageComplexDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error de validación en la solicitud",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Complejo no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<LandingPageComplexDto> getLandingPage(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getLandingPage(id));
    }

    /**
     * Deletes a sport complex by its ID.
     *
     * @param id the ID of the sport complex to delete
     * @return a 204 response if the sport complex is deleted successfully
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

