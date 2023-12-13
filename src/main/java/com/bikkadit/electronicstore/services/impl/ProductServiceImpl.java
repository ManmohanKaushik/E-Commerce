package com.bikkadit.electronicstore.services.impl;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.entity.Category;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Request is sending into DAO layer for create product ");
        Product product = this.mapper.map(productDto, Product.class);
        String productId= UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        Product save = this.productRepository.save(product);
        log.info("Response has  received  from DAO layer for create product .");
        return mapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        log.info("Request is sending into DAO layer for update product containing productId :{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCTID_NOTFOUND));
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());
        product.setProductImageName(productDto.getProductImageName());
        Product update = this.productRepository.save(product);
        log.info("Response has  received  from DAO layer for update product containing  productId :{}",productId);
        return mapper.map(update,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        log.info("Request is sending into DAO layer for delete product containing productId :{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCTID_NOTFOUND));
        productRepository.delete(product);
        log.info("Response has  received  from DAO layer for delete product containing  productId :{}",productId);

    }

    @Override
    public ProductDto getByid(String productId) {
        log.info("Request is sending into DAO layer for get product containing productId :{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCTID_NOTFOUND));
        log.info("Response has  received  from DAO layer for get  product containing  productId :{}",productId);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PegeableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Request is sending into DAO layer for get all product  ");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable page  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = this.productRepository.findAll(page);
        log.info("Response has  received  from DAO layer for get all product .");
        return Helper.pegeableResponse(all, ProductDto.class);
    }

    @Override
    public PegeableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Request is sending into DAO layer for get all live product  ");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable page  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = this.productRepository.findByLiveTrue(page);
        log.info("Response has  received  from DAO layer for get all live  product .");
        return Helper.pegeableResponse(all, ProductDto.class);
    }

    @Override
    public PegeableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Request is sending into DAO layer for search  product by title :{}",subTitle);
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable page  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page1 = this.productRepository.findByTitleContaining(subTitle, page);
        log.info("Response has  received  from DAO layer for search product by title :{}",subTitle);
        return Helper.pegeableResponse(page1, ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        log.info("Request is sending into DAO layer for create with  categoryId :{}",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORYID_NOTFOUND));
        Product product = mapper.map(productDto, Product.class);
        String productId= UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        log.info("Response has  received  from DAO layer for create with  categoryId :{}",categoryId);
        return mapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        log.info("Request is sending into DAO layer for update Category with  categoryId :{} and productId:{}",categoryId,productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCTID_NOTFOUND));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORYID_NOTFOUND));
        product.setCategory(category);
        Product updateProduct = productRepository.save(product);
        log.info("Response has  received  from DAO layer for update category with  categoryId :{} and productId:{}",categoryId,productId);
        return mapper.map(updateProduct,ProductDto.class);
    }

    @Override
    public PegeableResponse<ProductDto> getAllofCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) {
        log.info("Request is sending into DAO layer for get All Category with  categoryId :{}",categoryId);
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CATEGORYID_NOTFOUND));
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable page  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page1 = this.productRepository.findByCategory(category,page);
        log.info("Response has  received  from DAO layer for get All category with  categoryId :{}",categoryId);
        return Helper.pegeableResponse(page1,ProductDto.class);
    }
}

