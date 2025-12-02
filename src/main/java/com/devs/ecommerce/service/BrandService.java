package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.BrandRequestDTO;
import com.devs.ecommerce.dto.BrandResponseDTO;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.BrandMapper;
import com.devs.ecommerce.model.Brand;
import com.devs.ecommerce.repositories.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Transactional
    public BrandResponseDTO createBrand(BrandRequestDTO brandRequest) {
        Brand brand = brandMapper.toEntity(brandRequest);
        brand = brandRepository.save(brand);
        return brandMapper.toResponse(brand);
    }

    public List<BrandResponseDTO> getAllBrands() {
        return brandMapper.toResponses(brandRepository.findAll());
    }

    public BrandResponseDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        return brandMapper.toResponse(brand);
    }

    @Transactional
    public BrandResponseDTO updateBrand(Long id, BrandRequestDTO brandRequest) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        existingBrand.setName(brandRequest.getName());
        Brand updatedBrand = brandRepository.save(existingBrand);
        return brandMapper.toResponse(updatedBrand);
    }

    @Transactional
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found");
        }
        brandRepository.deleteById(id);
    }
}
