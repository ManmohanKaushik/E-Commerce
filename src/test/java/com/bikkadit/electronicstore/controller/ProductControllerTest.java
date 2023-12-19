package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.helper.PegeableResponse;
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

import java.util.Arrays;

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
        String productId = "jklmn";
        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/apiPro/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    public void getByProductIdTest() throws Exception {
        String productId = "jklmn";
        ProductDto dto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.getByid(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/apiPro/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    public void getAllProductTest() throws Exception {
        ProductDto pro1 = ProductDto.builder().title("electronics").stock(true).live(true).price(5000).discountPrice(4500).productImageName("pro.png").quantity(50).build();
        ProductDto pro2 = ProductDto.builder().title("electronicsTv").stock(true).live(true).price(10000).discountPrice(9500).productImageName("prod.png").quantity(500).build();
        ProductDto pro3 = ProductDto.builder().title("electronicsEarphone").stock(true).live(true).price(150).discountPrice(125).productImageName("proE.png").quantity(200).build();
        ProductDto pro4 = ProductDto.builder().title("electronicsCharger").stock(true).live(true).price(500).discountPrice(450).productImageName("proC.png").quantity(700).build();

        PegeableResponse<ProductDto> pegeableResponse = new PegeableResponse<>();
        pegeableResponse.setContent(Arrays.asList(pro1, pro2, pro3, pro4));
        pegeableResponse.setLastPage(false);
        pegeableResponse.setPageNumber(10);
        pegeableResponse.setPageSize(10);
        pegeableResponse.setTotalElements(100);

        Mockito.when(productService.getAllProduct(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pegeableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/apiPro/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(pegeableResponse))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductTest() throws Exception {
        String productId = "jklmn";
        ProductDto dto = mapper.map(product, ProductDto.class);
        Mockito.doNothing().when(productService).deleteProduct(productId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/apiPro/product/" + productId))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
