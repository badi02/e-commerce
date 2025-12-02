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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setQuantity(10);
        product.setImages(new ArrayList<>());
        product.setCategory(new Category(1L, "Category", "Desc", new ArrayList<>()));
        product.setBrand(new Brand(1L, "Brand", new ArrayList<>()));

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setQuantity(10);
        productDTO.setImages(new ArrayList<>());
        productDTO.setCategory(productService.productMapper.toResponse(product.getCategory()));
        productDTO.setBrand(productService.productMapper.toResponse(product.getBrand()));

        pageable = PageRequest.of(0, 15);
    }

    @Test
    void createProduct_Success() throws IOException {
        MultipartFile[] images = { new MockMultipartFile("image", "test.jpg", "image/jpeg", "data".getBytes()) };

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(product.getCategory()));
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(product.getBrand()));
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO, images);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_NoImages() throws IOException {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(product.getCategory()));
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(product.getBrand()));
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO, null);

        assertNotNull(result);
        assertTrue(result.getImages().isEmpty());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_CategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.createProduct(productDTO, null));
    }

    @Test
    void updateProduct_Success() throws IOException {
        MultipartFile[] images = { new MockMultipartFile("image", "test.jpg", "image/jpeg", "data".getBytes()) };

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(product.getCategory()));
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(product.getBrand()));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(1L, productDTO, images);

        assertNotNull(result);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(1L, productDTO, null));
    }

    @Test
    void deleteProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
    }

    @Test
    void getProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.getProduct(1L);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }

    @Test
    void getProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(1L));
    }

    @Test
    void getAllProducts_Success() {
        List<Product> products = List.of(product);
        Page<Product> productPage = new PageImpl<>(products, pageable, 1);
        List<ProductListDTO> listDTOS = List
                .of(new ProductListDTO(1L, "Test Product", "Desc", 100.0, 10, new ArrayList<>(), "Category", "Brand"));
        Page<ProductListDTO> dtoPage = new PageImpl<>(listDTOS, pageable, 1);

        when(productRepository.findAllWithoutComments(pageable)).thenReturn(dtoPage);

        Page<ProductListDTO> result = productService.getAllProducts(pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void searchProducts_NoFilters() {
        List<ProductListDTO> listDTOS = List
                .of(new ProductListDTO(1L, "Test Product", "Desc", 100.0, 10, new ArrayList<>(), "Category", "Brand"));
        Page<ProductListDTO> dtoPage = new PageImpl<>(listDTOS, pageable, 1);

        when(productRepository.findAllWithoutComments(pageable)).thenReturn(dtoPage);

        Page<ProductListDTO> result = productService.searchProducts(pageable, null, null, null, null);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void searchProducts_WithFilters() {
        List<ProductListDTO> listDTOS = List
                .of(new ProductListDTO(1L, "Test Product", "Desc", 100.0, 10, new ArrayList<>(), "Category", "Brand"));
        Page<ProductListDTO> dtoPage = new PageImpl<>(listDTOS, pageable, 1);

        when(productRepository.filterProduct(pageable, 1L, "test", BigDecimal.valueOf(50), BigDecimal.valueOf(150)))
                .thenReturn(dtoPage);

        Page<ProductListDTO> result = productService.searchProducts(pageable, "test", BigDecimal.valueOf(50),
                BigDecimal.valueOf(150), 1L);

        assertEquals(1, result.getTotalElements());
    }
}