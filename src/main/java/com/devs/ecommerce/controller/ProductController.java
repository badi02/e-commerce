package com.devs.ecommerce.controller;

import com.devs.ecommerce.dto.ProductDTO;
import com.devs.ecommerce.dto.ProductListDTO;
import com.devs.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Operations related to products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Create a new product", description = "Creates a new product in the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestPart("product") @Valid ProductDTO productDTO,
                                                    @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
        return ResponseEntity.ok(productService.createProduct(productDTO, images));
    }

    @Operation(summary = "Update product", description = "Updates product")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable Long id,
                                                    @RequestPart("product") @Valid ProductDTO productDTO,
                                                    @RequestPart(value = "images", required = false) MultipartFile[] images)throws IOException{
        return  ResponseEntity.ok(productService.updateProduct(id, productDTO, images));
    }

    @Operation(summary = "Delete a product", description = "Deletes product from the e-commerce platform")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> deleteProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get product", description = "Retrieves product by Id in the e-commerce platform")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @Operation(summary = "Get all products", description = "Retrieves all the products in the e-commerce platform")
    @GetMapping
    public ResponseEntity<Page<ProductListDTO>> getAllProducts(Pageable pageable){
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @Operation(summary = "Search for products", description = "Searches for the products in the e-commerce platform")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductListDTO>> searchProducts(Pageable pageable,
                                                               @RequestParam(required = false) String query,
                                                               @RequestParam(required = false) BigDecimal minPrice,
                                                               @RequestParam(required = false) BigDecimal maxPrice,
                                                               @RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(productService.searchProducts(pageable, query, minPrice, maxPrice, categoryId));
    }
}
