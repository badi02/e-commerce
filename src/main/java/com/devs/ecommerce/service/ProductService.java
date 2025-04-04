package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.ProductDTO;
import com.devs.ecommerce.dto.ProductListDTO;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.CategoryMapper;
import com.devs.ecommerce.mapper.ProductMapper;
import com.devs.ecommerce.model.Brand;
import com.devs.ecommerce.model.Category;
import com.devs.ecommerce.model.Product;
import com.devs.ecommerce.repositories.BrandRepository;
import com.devs.ecommerce.repositories.CategoryRepository;
import com.devs.ecommerce.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private static final String UPLOADED_DIR = "src/main/resources/static/images/";

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image) throws IOException  {
        Product product = productMapper.toEntity(productDTO);
        if (image != null && !image.isEmpty()){
            String fileName = saveImage(image);
            product.setImage("/images/"+fileName);
        }
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile image) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
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
        if (image != null && !image.isEmpty()){
            String fileName = saveImage(image);
            existingProduct.setImage("/images/"+fileName);
        }
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException(("Product not existing"));
        }

        productRepository.deleteById(id);
    }

    public ProductDTO getProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("product not found"));
        return productMapper.toDTO(product);
    }

    public Page<ProductListDTO> getAllProducts(@PageableDefault(size = 15) Pageable pageable){
        return productRepository.findAllWithoutComments(pageable);
    }

    private String saveImage(MultipartFile image) throws IOException{
        String fileName =UUID.randomUUID().toString()+"_"+image.getOriginalFilename();
        Path path = Paths.get(UPLOADED_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());
        return fileName;
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
}
