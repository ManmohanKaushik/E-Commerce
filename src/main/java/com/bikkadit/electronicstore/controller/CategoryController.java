package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstants;
import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.controller.payload.ApiResponse;
import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
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
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable String categoryId){
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
        ApiResponse apiResponse = ApiResponse.builder().message(MessageConstants.RESOURCEDELETE).Success(true).status(HttpStatus.OK).build();
        log.info("Response has received from service layer for delete categoryId :{}",categoryId);
        return new ResponseEntity<ApiResponse>( apiResponse,HttpStatus.OK);
    }

/**
 * @author Manmohan Sharma
 * @apiNote To delete data in database
 * @param categoryId
 * @since 1.0v
 */
@GetMapping("/category/{categoryId}")
public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
    log.info("Request is sending in service layer for get categoryId :{}",categoryId);
    CategoryDto getCategory = this.categoryService.getCategoryId(categoryId);
    log.info("Response has received from service layer for get categoryId :{}",categoryId);
    return new ResponseEntity<CategoryDto>(getCategory,HttpStatus.OK);
}

    /**
     * @author Manmohan Sharma
     * @apiNote To get all data from database
     * @since 1.0v
     */
    @GetMapping("/category")
public ResponseEntity<PegeableResponse<CategoryDto>> getAllCategory
        ( @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)int pageNumber,
          @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)int pageSize,
          @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
          @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir)
{
    log.info("Request is sending in service layer for get  all category");
    PegeableResponse<CategoryDto> allCategory = this.categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
    log.info("Response has received from service layer for get all category");
    return new ResponseEntity<PegeableResponse<CategoryDto>>(allCategory,HttpStatus.OK);
}
}
