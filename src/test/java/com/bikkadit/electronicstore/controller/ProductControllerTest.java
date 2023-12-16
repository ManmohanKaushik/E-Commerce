package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper mapper;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .productId("kldkl")
                .title("phone")
                .live(true)
                .price(3000)
                .description("New Brand")
                .quantity(2)
                .discountPrice(2500)
                .stock(true)
                .build();
    }

    @Test
    public void createProductTest() throws Exception {
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/apiPro/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJsonString(Object product) {
        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void updateProductTest() throws Exception {
        String productId="jklmn";
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.updateProduct(Mockito.any(),Mockito.anyString())).thenReturn(productDto);
this.mockMvc.perform(MockMvcRequestBuilders.put("/apiPro/product/"+productId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(convertObjectToJsonString(product))
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").exists());

    }


}
