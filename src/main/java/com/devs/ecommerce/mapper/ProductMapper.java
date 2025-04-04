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
    @Mapping(target = "image", source = "image")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "brand", source = "brand")
    ProductDTO toDTO(Product product);

    @Mapping(target = "image", source = "image")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "brand", source = "brand")
    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandName", source = "brand.name")
    ProductListDTO toProductListDTO(Product product);

    List<ProductListDTO> toProductListDTOs(List<Product> products);
    List<ProductDTO> toProductDTOs(List<Product> products);
    /*
    @Mapping(target = "userId", source = "user.id")
    CommentDTO toDTO(Comment comment);
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product", ignore = true)
    Comment toEntity(CommentDTO commentDTO);
     */
}
