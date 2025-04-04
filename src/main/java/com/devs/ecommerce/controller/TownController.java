package com.devs.ecommerce.controller;

import com.devs.ecommerce.dto.TownDTO;
import com.devs.ecommerce.service.TownService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/towns")
@RequiredArgsConstructor
@Tag(name = "Town Controller", description = "Operations related to towns")
public class TownController {

    private final TownService townService;

    @Operation(summary = "Create a new town", description = "Creates a new town in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TownDTO> createTown(@AuthenticationPrincipal UserDetails userDetails,
                                              @Valid @RequestBody TownDTO townDTO) {
        TownDTO createdTown = townService.createTown(townDTO);
        return ResponseEntity.ok(createdTown);
    }

    @Operation(summary = "Update town", description = "updates town in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TownDTO> updateTown(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long id, @Valid @RequestBody TownDTO townDTO) {
        TownDTO updatedTown = townService.updateTown(id, townDTO);
        return ResponseEntity.ok(updatedTown);
    }

    @Operation(summary = "Delete a town", description = "Deletes a town from the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTown(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable Long id) {
        townService.deleteTown(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a town by Id", description = "Retrieves a town by Id in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TownDTO> getTownById(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long id) {
        TownDTO townDTO = townService.getTownById(id);
        return ResponseEntity.ok(townDTO);
    }

    @Operation(summary = "Get all towns", description = "Retrieves all towns in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TownDTO>> getAllTowns(@AuthenticationPrincipal UserDetails userDetails) {
        List<TownDTO> towns = townService.getAllTowns();
        return ResponseEntity.ok(towns);
    }
}
