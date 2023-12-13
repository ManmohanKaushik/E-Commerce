package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;

public interface ProductService {
    public ProductDto createProduct(ProductDto productDto);

    public ProductDto updateProduct(ProductDto productDto, String productId);

    public void deleteProduct(String productId);

    public ProductDto getByid(String productId);

    public PegeableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    public PegeableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    public PegeableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);

    public ProductDto createWithCategory(ProductDto productDto,String categoryId);

    public  ProductDto updateCategory(String productId,String categoryId);

    public  PegeableResponse<ProductDto> getAllofCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);

}
