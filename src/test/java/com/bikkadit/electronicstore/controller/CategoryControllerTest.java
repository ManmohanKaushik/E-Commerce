package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.services.CategoryService;
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
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper mapper;

    @MockBean
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("mobile")
                .coverImage("mobile.jpg")
                .description("Mobile Show")
                .build();
    }

    @Test
    public void createCategoryTest() throws Exception {
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/apiCat/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void updateCategoryTest() throws Exception {
        String categoryId = "goat786";
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(categoryDto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/apiCat/category/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());


    }

    @Test
    public void getAllCategoryTest() throws Exception {
        CategoryDto o1 =CategoryDto.builder().title("mobile").description("new brand mobile").coverImage("mobile.png").build();
        CategoryDto o2 =CategoryDto.builder().title("LedTv").description("new brand ledtv").coverImage("led.png").build();
        CategoryDto o3 =CategoryDto.builder().title("earphone").description("new brand earphone").coverImage("earphone.png").build();

        PegeableResponse<CategoryDto> pegeableResponse = new PegeableResponse<>();
        pegeableResponse.setContent(Arrays.asList(o1, o2, o3));
        pegeableResponse.setLastPage(false);
        pegeableResponse.setPageNumber(10);
        pegeableResponse.setPageSize(10);
        pegeableResponse.setTotalElements(100);

        Mockito.when(categoryService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pegeableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/apiCat/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(pegeableResponse))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
