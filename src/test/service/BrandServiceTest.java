package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.BrandRequestDTO;
import com.devs.ecommerce.dto.BrandResponseDTO;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.BrandMapper;
import com.devs.ecommerce.model.Brand;
import com.devs.ecommerce.repositories.BrandRepository;
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
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandService brandService;

    private Brand brand;
    private BrandResponseDTO responseDTO;
    private BrandRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        brand = new Brand(1L, "Test", new ArrayList<>());

        responseDTO = new BrandResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test");

        requestDTO = new BrandRequestDTO();
        requestDTO.setName("Test");
    }

    @Test
    void createBrand_Success() {
        when(brandMapper.toEntity(any(BrandRequestDTO.class))).thenReturn(brand);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
        when(brandMapper.toResponse(any(Brand.class))).thenReturn(responseDTO);

        BrandResponseDTO result = brandService.createBrand(requestDTO);

        assertNotNull(result);
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    void getAllBrands_Success() {
        when(brandRepository.findAll()).thenReturn(List.of(brand));
        when(brandMapper.toResponses(anyList())).thenReturn(List.of(responseDTO));

        List<BrandResponseDTO> result = brandService.getAllBrands();

        assertEquals(1, result.size());
    }

    @Test
    void getBrandById_Success() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(brandMapper.toResponse(any(Brand.class))).thenReturn(responseDTO);

        BrandResponseDTO result = brandService.getBrandById(1L);

        assertNotNull(result);
    }

    @Test
    void getBrandById_NotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> brandService.getBrandById(1L));
    }

    @Test
    void updateBrand_Success() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
        when(brandMapper.toResponse(any(Brand.class))).thenReturn(responseDTO);

        BrandResponseDTO result = brandService.updateBrand(1L, requestDTO);

        assertNotNull(result);
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    void deleteBrand_Success() {
        when(brandRepository.existsById(1L)).thenReturn(true);

        brandService.deleteBrand(1L);

        verify(brandRepository).deleteById(1L);
    }

    @Test
    void deleteBrand_NotFound() {
        when(brandRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> brandService.deleteBrand(1L));
    }
}