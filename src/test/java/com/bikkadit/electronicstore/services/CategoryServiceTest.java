package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.repository.CategoryRepository;
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
public class CategoryServiceTest {
    @Autowired
    private ModelMapper mapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    Category category;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("mobile")
                .coverImage("mobile.jpg")
                .description("Mobile Show")
                .build();
    }

    @Test
    public void createCategoryTest() {

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto category1 = categoryService.createCategory(mapper.map(category, CategoryDto.class));
        String actualTitle = category1.getTitle();
        System.out.println(actualTitle);
        String expectedTile = "mobile";
        Assertions.assertEquals(expectedTile, actualTitle);

    }

    @Test
    public void getCategoryIdTest() {
        String categoryId = "154klm";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        CategoryDto actualDto = categoryService.getCategoryId(categoryId);
        // System.out.println(actualDto);
        Assertions.assertNotNull(categoryId);
        Assertions.assertEquals(category.getCategoryId(), actualDto.getCategoryId());


    }

    @Test
    public void updateCategoryTest() {
        CategoryDto expectedsDto = CategoryDto.builder()
                .title("mobile")
                .description("Mobile Show")
                .coverImage("mobile.jpg")
                .build();
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        CategoryDto actualDto = mapper.map(category, CategoryDto.class);
        Assertions.assertNotNull(actualDto);
        Assertions.assertEquals(expectedsDto.getTitle(), actualDto.getTitle());
    }

    @Test
    public void deleteCategoryTest() {
        String categoryId = "jk963";
        Mockito.when(categoryRepository.findById("jk963")).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId);
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);

    }
    @Test
    public void getAllTest(){
        Category category1 = Category.builder()
                .title("LedTv")
                .coverImage("mobile.jpg")
                .description("LedTv Show")
                .build();
        Category category2 = Category.builder()
                .title("EarPhone")
                .coverImage("mobile.jpg")
                .description("EarPhone Show")
                .build();
        List<Category> categories= Arrays.asList(category,category1,category2);
        Page<Category> page=new PageImpl<>(categories);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PegeableResponse<CategoryDto> actualList = categoryService.getAll(1, 2, "name", "asc");
        Assertions.assertEquals(3,actualList.getContent().size());
    }
}
