package com.devs.ecommerce.controller;

import com.devs.ecommerce.dto.CategoryRequestDTO;
import com.devs.ecommerce.dto.CategoryResponseDTO;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category Controller", description = "Operations related to categories")
public class CategoryController {
    private final CategoryService categoryService;

    // Create a new category
    @Operation(summary = "Create a new category", description = "Creates a new category in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> createCategory(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryResponse = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.ok(categoryResponse);
    }

    // Get all categories
    @Operation(summary = "Get all categories", description = "Retrieves all categories in the e-commerce platform")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Get a category by ID
    @Operation(summary = "Get a category by Id", description = "Retrieves a category by Id in the e-commerce platform")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // Update a category
    @Operation(summary = "Update category", description = "updates category in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@AuthenticationPrincipal UserDetails userDetails,
                                                              @PathVariable Long id,
                                                              @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, categoryRequestDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete a category
    @Operation(summary = "Delete a category", description = "Deletes a category from the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}