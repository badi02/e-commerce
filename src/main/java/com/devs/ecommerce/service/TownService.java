package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.TownDTO;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.TownMapper;
import com.devs.ecommerce.model.Town;
import com.devs.ecommerce.repositories.TownRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TownService {

    private final TownRepository townRepository;
    private final TownMapper townMapper;
    @Transactional
    public TownDTO createTown(TownDTO townDTO) {
        Town town = townMapper.toEntity(townDTO);
        Town savedTown = townRepository.save(town);
        return townMapper.toDTO(savedTown);
    }
    @Transactional
    public TownDTO updateTown(Long id, TownDTO townDTO) {
        Town town = townRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Town not found with ID: " + id));
        System.out.println(townDTO.getDeliveryTax());
        town.setName(townDTO.getName());
        town.setDeliveryTax(townDTO.getDeliveryTax());

        Town updatedTown = townRepository.save(town);
        return townMapper.toDTO(updatedTown);
    }
    @Transactional
    public void deleteTown(Long id) {
        if (!townRepository.existsById(id)) {
            throw new ResourceNotFoundException("Town not found with ID: " + id);
        }
        townRepository.deleteById(id);
    }

    public TownDTO getTownById(Long id) {
        Town town = townRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Town not found with ID: " + id));
        return townMapper.toDTO(town);
    }

    public List<TownDTO> getAllTowns() {
        List<Town> towns = townRepository.findAll();
        return townMapper.toDTOs(towns);
    }
}

