package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductService productService;

    Product product;

    @BeforeEach
    public void init() {
        product = Product.builder()
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
    public void createProductTest() {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto dto = productService.createProduct(mapper.map(product, ProductDto.class));
        String actualTitle = dto.getTitle();
        System.out.println(actualTitle);
        String expectedTile = "phone";
        Assertions.assertEquals(expectedTile, actualTitle);


    }

    @Test
    public void getByidTest() {
        String productId="klm789";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDto actualDto = productService.getByid(productId);
        Assertions.assertNotNull(productId);
        Assertions.assertEquals(product.getProductId(),actualDto.getProductId());


    }
    @Test
    public void updateProduct(){
        ProductDto expectedDto = ProductDto.builder()
                .title("phone")
                .live(true)
                .price(3000)
                .description("New Brand")
                .quantity(2)
                .discountPrice(2500)
                .stock(true)
                .build();
        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));
        ProductDto actualDto = mapper.map(product, ProductDto.class);
        Assertions.assertNotNull(actualDto);
        System.out.println(expectedDto.getTitle());
        System.out.println(actualDto.getTitle());
        Assertions.assertEquals(expectedDto.getTitle(),actualDto.getTitle());


    }
    @Test
    public void deleteProduct(){
        String productId="489piu";
        Mockito.when(productRepository.findById("489piu")).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);
        Mockito.verify(productRepository,Mockito.times(1)).delete(product);
    }
    @Test
    public void getAllProductTest(){
        Product product1 = Product.builder()
                .title("LedTv")
                .live(true)
                .price(3000)
                .description("New Brand Sony")
                .quantity(5)
                .discountPrice(2500)
                .stock(true)
                .build();
        Product product2 = Product.builder()
                .title("Laptop")
                .live(true)
                .price(30000)
                .description("New Brand Dell")
                .quantity(20)
                .discountPrice(25000)
                .stock(true)
                .build();
        List<Product> products= Arrays.asList(product,product1,product2);
        Page<Product> page =new PageImpl<>(products);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PegeableResponse<ProductDto> allProduct = productService.getAllProduct(1, 2, "name", "asc");
        Assertions.assertEquals(3,allProduct.getContent().size());
    }
}
