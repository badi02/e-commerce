package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.CategoryRequestDTO;
import com.devs.ecommerce.dto.CategoryResponseDTO;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.CategoryMapper;
import com.devs.ecommerce.model.Category;
import com.devs.ecommerce.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @CacheEvict(value = "categories", allEntries = true)
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Cacheable(value = "categories")
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryMapper.toResponses(categoryRepository.findAll());
    }

    @Cacheable(value = "categories", key = "#id")
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return categoryMapper.toResponse(category);
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toResponse(updatedCategory);
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}