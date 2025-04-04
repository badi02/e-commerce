package com.devs.ecommerce.mapper;

import com.devs.ecommerce.dto.BrandRequestDTO;
import com.devs.ecommerce.dto.BrandResponseDTO;
import com.devs.ecommerce.dto.CategoryRequestDTO;
import com.devs.ecommerce.dto.CategoryResponseDTO;
import com.devs.ecommerce.model.Brand;
import com.devs.ecommerce.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target = "id", ignore = true)
    Brand toEntity(BrandRequestDTO brandRequest);
    @Mapping(target = "id", ignore = true)
    Brand toEntity(BrandResponseDTO brandRequest);

    //@Mapping(target = "products", ignore = true)
    BrandResponseDTO toResponse(Brand brand);

    List<BrandResponseDTO> toResponses(List<Brand> brands);
}
