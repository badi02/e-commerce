package com.devs.ecommerce.controller;

import com.devs.ecommerce.dto.ProductDTO;
import com.devs.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductService productService;

        @Test
        void createProduct_Success() throws Exception {
                ProductDTO productDTO = new ProductDTO();
                // set fields...

                when(productService.createProduct(any(ProductDTO.class), any(MultipartFile[].class)))
                                .thenReturn(productDTO);

                MockMultipartFile productPart = new MockMultipartFile("product", "", "application/json",
                                "{\"name\":\"Test\"}".getBytes());
                MockMultipartFile imagePart = new MockMultipartFile("images", "test.jpg", "image/jpeg",
                                "data".getBytes());

                mockMvc.perform(multipart("/api/products")
                                .file(productPart)
                                .file(imagePart)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                                .andExpect(status().isOk());
        }

        @Test
        void updateProduct_Success() throws Exception {
                ProductDTO productDTO = new ProductDTO();
                // set fields...

                when(productService.updateProduct(anyLong(), any(ProductDTO.class), any(MultipartFile[].class)))
                                .thenReturn(productDTO);

                MockMultipartFile productPart = new MockMultipartFile("product", "", "application/json",
                                "{\"name\":\"Updated\"}".getBytes());
                MockMultipartFile imagePart = new MockMultipartFile("images", "test.jpg", "image/jpeg",
                                "data".getBytes());

                mockMvc.perform(multipart("/api/products/{id}", 1L).with(request -> {
                        request.setMethod("PUT");
                        return request;
                })
                                .file(productPart)
                                .file(imagePart)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                                .andExpect(status().isOk());
        }

        @Test
        void deleteProduct_Success() throws Exception {
                doNothing().when(productService).deleteProduct(anyLong());

                mockMvc.perform(delete("/api/products/{id}", 1L))
                                .andExpect(status().isNoContent());
        }

        @Test
        void getProduct_Success() throws Exception {
                ProductDTO productDTO = new ProductDTO();
                // set fields...

                when(productService.getProduct(anyLong())).thenReturn(productDTO);

                mockMvc.perform(get("/api/products/{id}", 1L))
                                .andExpect(status().isOk());
        }

        @Test
        void getAllProducts_Success() throws Exception {
                Page<ProductListDTO> page = new PageImpl<>(List.of(new ProductListDTO()));

                when(productService.getAllProducts(any(Pageable.class))).thenReturn(page);

                mockMvc.perform(get("/api/products"))
                                .andExpect(status().isOk());
        }

        @Test
        void searchProducts_Success() throws Exception {
                Page<ProductListDTO> page = new PageImpl<>(List.of(new ProductListDTO()));

                when(productService.searchProducts(any(Pageable.class), any(), any(), any(), any())).thenReturn(page);

                mockMvc.perform(get("/api/products/search?query=test"))
                                .andExpect(status().isOk());
        }
}