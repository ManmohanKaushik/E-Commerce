package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;


public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);
    public void deleteCategory(String categoryId);
    public CategoryDto getCategoryId(String categoryId);
    public PegeableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

}
