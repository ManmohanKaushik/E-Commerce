package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = this.mapper.map(productDto, Product.class);
        Product save = this.productRepository.save(product);
        return mapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCTID_NOTFOUND));
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDiscountPrice(productDto.getDiscountPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());
        Product update = this.productRepository.save(product);
        return mapper.map(update,ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_DELETE));
        productRepository.delete(product);

    }

    @Override
    public ProductDto getByid(String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCTID_NOTFOUND));
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PegeableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable page  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = this.productRepository.findAll(page);
        return Helper.pegeableResponse(all, ProductDto.class);
    }

    @Override
    public PegeableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable page  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> all = this.productRepository.findByLiveTrue(page);
        return Helper.pegeableResponse(all, ProductDto.class);
    }

    @Override
    public PegeableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable page  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page1 = this.productRepository.findByTitleContaining(subTitle, page);
        return Helper.pegeableResponse(page1, ProductDto.class);
    }
}
