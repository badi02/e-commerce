package com.devs.ecommerce.mapper;

import com.devs.ecommerce.dto.CategoryRequestDTO;
import com.devs.ecommerce.dto.CategoryResponseDTO;
import com.devs.ecommerce.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequestDTO categoryRequest);
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryResponseDTO categoryRequest);

    //@Mapping(target = "products", ignore = true)
    CategoryResponseDTO toResponse(Category category);

    List<CategoryResponseDTO> toResponses(List<Category> categories);
}