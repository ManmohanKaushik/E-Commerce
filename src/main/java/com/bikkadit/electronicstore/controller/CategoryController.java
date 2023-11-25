package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiCat")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * @author Manmohan Sharma
     * @apiNote To create data in database
     * @param categoryDto
     * @since 1.0v
     * @return categoryDto
     */
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        log.info("Request is sending in service layer for create Category.");
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        log.info("Response has received from service layer for create Category");
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To update data in database
     * @param categoryDto,categoryId
     * @since 1.0v
     * @return categoryDto
     */
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable String categoryId){
        log.info("Request is sending in service layer for update  categoryId :{}",categoryId);
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        log.info("Response has received from service layer for update categoryId :{}",categoryId);
        return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To delete data in database
     * @param categoryId
     * @since 1.0v

     */
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
        log.info("Request is sending in service layer for delete categoryId :{}",categoryId);
        this.categoryService.deleteCategory(categoryId);
        log.info("Response has received from service layer for delete categoryId :{}",categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(MessageConstants.RESOURCEDELETE,true),HttpStatus.OK);
    }
}
