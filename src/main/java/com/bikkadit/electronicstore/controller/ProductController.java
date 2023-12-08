package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstants;
import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.payload.ImageResponse;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.services.FileService;
import com.bikkadit.electronicstore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/apiPro")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    /**
     * @param productDto
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To create Product data in database
     * @since 1.0v
     */
    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        log.info("Request is sending in service layer for create product ");
        ProductDto product = this.productService.createProduct(productDto);
        log.info("Response has received from service layer for create product ");
        return new ResponseEntity<ProductDto>(product, HttpStatus.CREATED);
    }
    /**
     * @param productId
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To update product data in database
     * @since 1.0v
     */
    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId) {
        log.info("Request is sending in service layer for update product containing productId:{}", productId);
        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
        log.info("Response has received from service layer for updated product containing productId:{}", productId);
        return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.CREATED);
    }
    /**
     * @param productId
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To get by productId data from database
     * @since 1.0v
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getByProductId(@PathVariable String productId) {
        log.info("Request is sending in service layer for get by  product containing productId:{}", productId);
        ProductDto dto = this.productService.getByid(productId);
        log.info("Response has received from service layer for get by product containing productId:{}", productId);
        return new ResponseEntity<ProductDto>(dto, HttpStatus.OK);
    }
    /**
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To get all product data from database
     * @since 1.0v
     */
    @GetMapping("/products")
    public ResponseEntity<PegeableResponse<ProductDto>> getAllProduct
            (@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_SUBTITLE, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        log.info("Request is sending in service layer for get all product ");
        PegeableResponse<ProductDto> allProduct = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        log.info("Response has received from service layer for get all product ");
        return new ResponseEntity<PegeableResponse<ProductDto>>(allProduct, HttpStatus.OK);
    }
    /**
     * @param productId
     * @return ApiResponse
     * @author Manmohan Sharma
     * @apiNote To delete data from database
     * @since 1.0v
     */
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        log.info("Request is sending in service layer for delete product containing productId:{}", productId);
        this.productService.deleteProduct(productId);
        ApiResponse response = ApiResponse.builder()
                .message(MessageConstants.PRODUCT_DELETE)
                .Success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Response has received from service layer for delete product containing productId:{}", productId);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
    /**
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To get all live product data from database
     * @since 1.0v
     */
    @GetMapping("/live")
    public ResponseEntity<PegeableResponse<ProductDto>> getAllLive
    (@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        log.info("Request is sending in service layer for get all live product ");
        PegeableResponse<ProductDto> live = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("Response has received from service layer for get all live product ");
        return new ResponseEntity<PegeableResponse<ProductDto>>(live, HttpStatus.OK);
    }
    /**
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To search  product by title data from database
     * @since 1.0v
     */
    @GetMapping("/search")
    public ResponseEntity<PegeableResponse<ProductDto>> searchByTitle
            (@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir,
             @RequestParam(value = "subTitle", defaultValue = AppConstants.SORT_SUBTITLE, required = false) String subTitle ){
        log.info("Request is sending in service layer for get all live product ");
        PegeableResponse<ProductDto> title = this.productService.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        log.info("Response has received from service layer for get all live product ");
        return new ResponseEntity<PegeableResponse<ProductDto>>(title, HttpStatus.OK);
    }
    /**
     * @param productId
     * @return ImageResponse
     * @author Manmohan Sharma
     * @apiNote To uploadImage in database
     * @since 1.0v
     */

    @GetMapping("/image/productId")
    public ResponseEntity<ImageResponse> uploadImage
            (@PathVariable String productId,
             @RequestParam("productImage")MultipartFile image) throws IOException {
        log.info("Request is sending in service layer for uploadImage containing productId:{}", productId);
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto dto = productService.getByid(productId);
        dto.setProductImageName(fileName);
        ProductDto updateProduct = productService.updateProduct(dto, productId);
        ImageResponse response = ImageResponse.builder()
                .imageName(updateProduct.getProductImageName())
                .message(MessageConstants.PRODUCT_IMAGE).status(HttpStatus.CREATED)
                .Success(true)
                .build();
        log.info("Response has received from service layer for uploadImage of product containing productId:{}", productId);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
