package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.ProductDTO;
import com.devs.ecommerce.dto.ProductListDTO;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.ProductMapper;
import com.devs.ecommerce.model.Brand;
import com.devs.ecommerce.model.Category;
import com.devs.ecommerce.model.Product;
import com.devs.ecommerce.repositories.BrandRepository;
import com.devs.ecommerce.repositories.CategoryRepository;
import com.devs.ecommerce.repositories.ProductRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    // private static final String UPLOADED_DIR =
    // "src/main/resources/static/images/";
    private static final String UPLOADED_DIR = System.getProperty("user.dir") + "/uploads/";

    @PostConstruct
    private void init() throws IOException {
        Files.createDirectories(Paths.get(UPLOADED_DIR));
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile[] images) throws IOException {
        Product product = productMapper.toEntity(productDTO);

        Category category = categoryRepository.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Brand brand = brandRepository.findById(productDTO.getBrand().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        product.setCategory(category);
        product.setBrand(brand);

        List<String> imagePaths = new ArrayList<>();
        if (images != null && images.length > 0) {
            // List<String> imagePaths = new ArrayList<>();
            for (MultipartFile image : images) {
                String fileName = saveImage(image);
                imagePaths.add("/images/" + fileName);
            }
        } else {
            // System.out.println("Images are Null or empty"+ images);
            logger.info("No new images provided for product update; keeping existing or empty list.");
        }
        product.setImages(imagePaths);

        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile[] images) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (existingProduct.getImages() != null) {
            deleteImages(existingProduct.getImages());
        }

        Category category = categoryRepository.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Brand brand = brandRepository.findById(productDTO.getBrand().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setCategory(category);
        existingProduct.setBrand(brand);
        List<String> imagePaths = new ArrayList<>();
        if (images != null && images.length > 0) {
            for (MultipartFile image : images) {
                String fileName = saveImage(image);
                imagePaths.add("/images/" + fileName);
            }
        } else {
            // System.out.println("No image has been added "+ images);
            logger.info("No new images provided for product update; keeping existing or empty list.");
        }
        existingProduct.setImages(imagePaths);

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    private String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path path = Paths.get(UPLOADED_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());
        return fileName;
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (product.getImages() != null) {
            deleteImages(product.getImages()); // Delete existing image if any
        }
        productRepository.deleteById(id);
    }

    public ProductDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found"));
        return productMapper.toDTO(product);
    }

    public Page<ProductListDTO> getAllProducts(@PageableDefault(size = 15) Pageable pageable) {
        return productRepository.findAllWithoutComments(pageable);
    }

    public Page<ProductListDTO> searchProducts(@PageableDefault(size = 15) Pageable pageable,
            String query,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Long categoryId) {
        boolean isQueryEmpty = (query == null || query.trim().isEmpty());
        boolean isPriceRangeEmpty = (minPrice == null && maxPrice == null);
        boolean isCategoryEmpty = (categoryId == null);

        // If no query, price range, or category filter is provided, return all products
        if (isQueryEmpty && isPriceRangeEmpty && isCategoryEmpty) {
            return productRepository.findAllWithoutComments(pageable);
        }

        // Apply search criteria based on provided parameters
        if (isCategoryEmpty) {
            // If categoryId is not provided, search without category filter
            return productRepository.filterProduct(pageable, null, query, minPrice, maxPrice);
        }
        // If categoryId is provided, search with category filter
        return productRepository.filterProduct(pageable, categoryId, query, minPrice, maxPrice);
    }

    private void deleteImages(List<String> imagePaths) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            for (String imagePath : imagePaths) {
                Path path = Paths.get(UPLOADED_DIR + imagePath);
                try {
                    Files.deleteIfExists(path); // Delete the image file if it exists
                } catch (IOException e) {
                    logger.error("Failed to delete image at path: {}", path, e);
                    // Log the error or handle it as necessary
                    e.printStackTrace();
                }
            }
        }
    }

}
