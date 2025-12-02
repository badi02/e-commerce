package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.CategoryRequestDTO;
import com.devs.ecommerce.dto.CategoryResponseDTO;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.CategoryMapper;
import com.devs.ecommerce.model.Category;
import com.devs.ecommerce.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryResponseDTO responseDTO;
    private CategoryRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Test", "Desc", new ArrayList<>());

        responseDTO = new CategoryResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test");
        responseDTO.setDescription("Desc");

        requestDTO = new CategoryRequestDTO();
        requestDTO.setName("Test");
        requestDTO.setDescription("Desc");
    }

    @Test
    void createCategory_Success() {
        when(categoryMapper.toEntity(any(CategoryRequestDTO.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(responseDTO);

        CategoryResponseDTO result = categoryService.createCategory(requestDTO);

        assertNotNull(result);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void getAllCategories_Success() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(categoryMapper.toResponses(anyList())).thenReturn(List.of(responseDTO));

        List<CategoryResponseDTO> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
    }

    @Test
    void getCategoryById_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(responseDTO);

        CategoryResponseDTO result = categoryService.getCategoryById(1L);

        assertNotNull(result);
    }

    @Test
    void getCategoryById_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void updateCategory_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(responseDTO);

        CategoryResponseDTO result = categoryService.updateCategory(1L, requestDTO);

        assertNotNull(result);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void deleteCategory_Success() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.deleteCategory(1L);

        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void deleteCategory_NotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
}