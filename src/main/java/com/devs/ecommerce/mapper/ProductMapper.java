package com.devs.ecommerce.mapper;

//import com.devs.ecommerce.dto.CommentDTO;

import com.devs.ecommerce.dto.ProductDTO;
import com.devs.ecommerce.dto.ProductListDTO;
import com.devs.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //@Mapping(target = "image", source = "image")
    @Mapping(source = "images", target = "images")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "brand", source = "brand")
    ProductDTO toDTO(Product product);

    //@Mapping(target = "image", source = "image")
    //@InheritInverseConfiguration
    //@Mapping(target = "images", ignore = true)
    @Mapping(target = "images", source = "images")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "brand", source = "brand")
    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandName", source = "brand.name")
    //@Mapping(source = "images", target = "images", qualifiedByName = "mapImagesToPaths")
    ProductListDTO toProductListDTO(Product product);

    List<ProductListDTO> toProductListDTOs(List<Product> products);
    List<ProductDTO> toProductDTOs(List<Product> products);

    /*@Named("mapImagesToPaths")
    static List<String> mapImagesToPaths(List<ProductImage> images) {
        if (images == null) return new ArrayList<>();
        return images.stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());
    }*/
}
