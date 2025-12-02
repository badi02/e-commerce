package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.TownDTO;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.TownMapper;
import com.devs.ecommerce.model.Town;
import com.devs.ecommerce.repositories.TownRepository;
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
class TownServiceTest {

    @Mock
    private TownRepository townRepository;

    @Mock
    private TownMapper townMapper;

    @InjectMocks
    private TownService townService;

    private Town town;
    private TownDTO townDTO;

    @BeforeEach
    void setUp() {
        town = new Town(1L, "Test Town", 10.0);

        townDTO = new TownDTO();
        townDTO.setId(1L);
        townDTO.setName("Test Town");
        townDTO.setDeliveryTax(10.0);
    }

    @Test
    void createTown_Success() {
        when(townMapper.toEntity(any(TownDTO.class))).thenReturn(town);
        when(townRepository.save(any(Town.class))).thenReturn(town);
        when(townMapper.toDTO(any(Town.class))).thenReturn(townDTO);

        TownDTO result = townService.createTown(townDTO);

        assertNotNull(result);
        verify(townRepository).save(any(Town.class));
    }

    @Test
    void updateTown_Success() {
        when(townRepository.findById(1L)).thenReturn(Optional.of(town));
        when(townRepository.save(any(Town.class))).thenReturn(town);
        when(townMapper.toDTO(any(Town.class))).thenReturn(townDTO);

        TownDTO result = townService.updateTown(1L, townDTO);

        assertNotNull(result);
        verify(townRepository).save(any(Town.class));
    }

    @Test
    void updateTown_NotFound() {
        when(townRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> townService.updateTown(1L, townDTO));
    }

    @Test
    void deleteTown_Success() {
        when(townRepository.existsById(1L)).thenReturn(true);

        townService.deleteTown(1L);

        verify(townRepository).deleteById(1L);
    }

    @Test
    void deleteTown_NotFound() {
        when(townRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> townService.deleteTown(1L));
    }

    @Test
    void getTownById_Success() {
        when(townRepository.findById(1L)).thenReturn(Optional.of(town));
        when(townMapper.toDTO(any(Town.class))).thenReturn(townDTO);

        TownDTO result = townService.getTownById(1L);

        assertNotNull(result);
    }

    @Test
    void getTownById_NotFound() {
        when(townRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> townService.getTownById(1L));
    }

    @Test
    void getAllTowns_Success() {
        when(townRepository.findAll()).thenReturn(List.of(town));
        when(townMapper.toDTOs(anyList())).thenReturn(List.of(townDTO));

        List<TownDTO> result = townService.getAllTowns();

        assertEquals(1, result.size());
    }
}