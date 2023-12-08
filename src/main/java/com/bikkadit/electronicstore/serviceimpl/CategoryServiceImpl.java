package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.CategoryDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.serviceimpl.repository.CategoryRepository;
import com.bikkadit.electronicstore.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Request is sending in DAO layer for create Category.");
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);
        Category saveCategory = this.categoryRepository.save(category);
        log.info("Response has received from DAO layer for create Category.");
        return mapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Request is sending in DAO layer for update categoryId:{} ", categoryId);
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORYID_NOTFOUND));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updateCategory = this.categoryRepository.save(category);
        log.info("Response has received from DAO layer for update  categoryId:{} ", categoryId);
        return mapper.map(updateCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        log.info("Request is sending in DAO layer for delete categoryId:{} ", categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORYID_NOTFOUND));
        this.categoryRepository.deleteById(categoryId);
        log.info("Response has received from DAO layer for delete  categoryId:{} ", categoryId);

    }

    @Override
    public CategoryDto getCategoryId(String categoryId) {
        log.info("Request is sending in DAO layer for get categoryId:{} ", categoryId);
        Category getCategory = this.categoryRepository
                .findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORYID_NOTFOUND));
        log.info("Response has received from DAO layer for get  categoryId:{} ", categoryId);
        return mapper.map(getCategory, CategoryDto.class);
    }

    @Override
    public PegeableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Request is sending into DAO layer for get all category ");
        // Sort sort=(sortDir.equalsIgnoreCase("desc"))?Sort.by(sortBy).descending();
        //Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):
        PageRequest request = PageRequest.of(pageNumber, pageSize);
        Page<Category> all = this.categoryRepository.findAll(request);
        PegeableResponse<CategoryDto> response = Helper.pegeableResponse(all, CategoryDto.class);
        log.info("Response has  received  from DAO layer for get all category ");
        return response;
    }
}
