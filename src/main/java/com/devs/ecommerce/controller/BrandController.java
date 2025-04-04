package com.devs.ecommerce.controller;

import com.devs.ecommerce.dto.BrandRequestDTO;
import com.devs.ecommerce.dto.BrandResponseDTO;
import com.devs.ecommerce.dto.CategoryRequestDTO;
import com.devs.ecommerce.dto.CategoryResponseDTO;
import com.devs.ecommerce.service.BrandService;
import com.devs.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Tag(name = "Brand Controller", description = "Operations related to brands")
public class BrandController {
    private final BrandService brandService;

    // Create a new category
    @Operation(summary = "Create a new brand", description = "Creates a new brand in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandResponseDTO> createBrand(@AuthenticationPrincipal UserDetails userDetails,
                                                        @RequestBody BrandRequestDTO brandRequestDTO) {
        BrandResponseDTO brandResponse = brandService.createBrand(brandRequestDTO);
        return ResponseEntity.ok(brandResponse);
    }

    // Get all categories
    @Operation(summary = "Get all brands", description = "Retrieves all brands in the e-commerce platform")
    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        List<BrandResponseDTO> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    // Get a category by ID
    @Operation(summary = "Get a brand by Id", description = "Retrieves a brand by Id in the e-commerce platform")
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable Long id) {
        BrandResponseDTO brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

    // Update a category
    @Operation(summary = "Update brand", description = "updates brand in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandResponseDTO> updateBrand(@AuthenticationPrincipal UserDetails userDetails,
                                                              @PathVariable Long id,
                                                              @RequestBody BrandRequestDTO brandRequestDTO) {
        BrandResponseDTO updatedBrand = brandService.updateBrand(id, brandRequestDTO);
        return ResponseEntity.ok(updatedBrand);
    }

    // Delete a category
    @Operation(summary = "Delete a brand", description = "Deletes a brand from the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBrand(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
