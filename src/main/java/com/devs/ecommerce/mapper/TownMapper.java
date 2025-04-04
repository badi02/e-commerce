package com.devs.ecommerce.mapper;

import com.devs.ecommerce.dto.TownDTO;
import com.devs.ecommerce.model.Town;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TownMapper {
    @Mapping(target = "id", source = "town.id")
    @Mapping(target = "name", source = "town.name")
    @Mapping(target = "deliveryTax", source = "town.deliveryTax")
    TownDTO toDTO(Town town);

    @Mapping(target = "id", source = "townDTO.id")
    @Mapping(target = "name", source = "townDTO.name")
    @Mapping(target = "deliveryTax", source = "townDTO.deliveryTax")
    Town toEntity(TownDTO townDTO);

    @IterableMapping(elementTargetType = TownDTO.class)
    List<TownDTO> toDTOs(List<Town> towns);

    @IterableMapping(elementTargetType = Town.class)
    List<Town> toEntities(List<TownDTO> townDTOs);
}

