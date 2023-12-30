package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstants;
import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.ProductDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.payload.ImageResponse;
import com.bikkadit.electronicstore.services.FileService;
import com.bikkadit.electronicstore.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static com.bikkadit.electronicstore.constants.UriConstants.*;

@RestController
@RequestMapping(PRODUCT_URI)
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.profile.image.path}")
    private String imagePath;

    /**
     * @param productDto
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To create Product data in database
     * @since 1.0v
     */
    @PreAuthorize(AUTH_ROLE)
    @PostMapping(CREATE_PRODUCT)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        log.info("Initiated request  for create product ");
        ProductDto product = this.productService.createProduct(productDto);
        log.info("Completed request for create product ");
        return new ResponseEntity<ProductDto>(product, HttpStatus.CREATED);
    }

    /**
     * @param productId
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To update product data in database
     * @since 1.0v
     */
    @PutMapping(UPDATE_PRODUCT)
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId) {
        log.info("Initiated request   for update product with productId:{}", productId);
        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
        log.info("Completed request for updated product with productId:{}", productId);
        return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.CREATED);
    }

    /**
     * @param productId
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To get by productId data from database
     * @since 1.0v
     */
    @GetMapping(GET_PRODUCT_BY_ID)
    public ResponseEntity<ProductDto> getByProductId(@PathVariable String productId) {
        log.info("Initiated request  for get by  product with productId:{}", productId);
        ProductDto dto = this.productService.getByid(productId);
        log.info("Completed request for get by product with productId:{}", productId);
        return new ResponseEntity<ProductDto>(dto, HttpStatus.OK);
    }

    /**
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To get all product data from database
     * @since 1.0v
     */
    @GetMapping(GET_ALL_PRODUCT)
    public ResponseEntity<PegeableResponse<ProductDto>> getAllProduct
    (@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_SUBTITLE, required = false) String sortBy,
     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        log.info("Initiated request  for get all product ");
        PegeableResponse<ProductDto> allProduct = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for get all product ");
        return new ResponseEntity<PegeableResponse<ProductDto>>(allProduct, HttpStatus.OK);
    }

    /**
     * @param productId
     * @return ApiResponse
     * @author Manmohan Sharma
     * @apiNote To delete data from database
     * @since 1.0v
     */
    @DeleteMapping(DELETE_PRODUCT)
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        log.info("Initiated request  for delete product with productId:{}", productId);
        this.productService.deleteProduct(productId);
        ApiResponse response = ApiResponse.builder()
                .message(MessageConstants.PRODUCT_DELETE)
                .Success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Completed request for delete product with productId:{}", productId);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    /**
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To get all live product data from database
     * @since 1.0v
     */
    @GetMapping(GET_LIVE)
    public ResponseEntity<PegeableResponse<ProductDto>> getAllLive
    (@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        log.info("Initiated request  for get all live product ");
        PegeableResponse<ProductDto> live = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for get all live product ");
        return new ResponseEntity<PegeableResponse<ProductDto>>(live, HttpStatus.OK);
    }

    /**
     * @return productDto
     * @author Manmohan Sharma
     * @apiNote To search  product by title data from database
     * @since 1.0v
     */
    @GetMapping(SEARCH_TITLE)
    public ResponseEntity<PegeableResponse<ProductDto>> searchByTitle
    (@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir,
     @RequestParam(value = "subTitle", defaultValue = AppConstants.SORT_SUBTITLE, required = false) String subTitle) {
        log.info("Initiated request  for search with title product ");
        PegeableResponse<ProductDto> title = this.productService.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for get all live product ");
        return new ResponseEntity<PegeableResponse<ProductDto>>(title, HttpStatus.OK);
    }

    /**
     * @param productId
     * @return ImageResponse
     * @author Manmohan Sharma
     * @apiNote To uploadImage in database
     * @since 1.0v
     */

    @PostMapping(UPLOAD_PRODUCT_IMAGE)
    public ResponseEntity<ImageResponse> uploadImage

    (@RequestParam("productImage") MultipartFile image, @PathVariable String productId) throws IOException {
        log.info("Initiated request  for upload Image with productId:{}", productId);
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto dto = productService.getByid(productId);
        dto.setProductImageName(fileName);
        ProductDto updateProduct = productService.updateProduct(dto, productId);
        ImageResponse response = ImageResponse.builder().message(MessageConstants.PRODUCT_IMAGE).status(HttpStatus.CREATED).imageName(updateProduct.getProductImageName()).Success(true).build();
        log.info("Completed request for uploadImage of product with productId:{}", productId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    /**
     * @param productId
     * @return ImageResponse
     * @author Manmohan Sharma
     * @apiNote To serverProductImage in database
     * @since 1.0v
     */

    @GetMapping(SERVER_PRODUCT_IMAGE)
    public void serverProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        log.info("Initiated request  for get Project Image with productId:{}", productId);
        ProductDto dto = this.productService.getByid(productId);
        log.info("Product image name :{}", dto.getProductImageName());
        InputStream resource = this.fileService.getResource(imagePath, dto.getProductImageName());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("Completed request for get Project Image of product with productId:{}", productId);
    }

    /**
     * @param categoryId
     * @return ProductDto
     * @author Manmohan Sharma
     * @apiNote To createWithCategory in database
     * @since 1.0v
     */
    @PostMapping(CREATE_WITH_CATEGORY)
    public ResponseEntity<ProductDto> createWithCategory(@PathVariable String categoryId, @RequestBody ProductDto productDto) {
        log.info("Initiated request  for create with categoryId:{}", categoryId);
        ProductDto productWithCategory = this.productService.createWithCategory(productDto, categoryId);
        log.info("Completed request for create with categoryId:{}", categoryId);
        return new ResponseEntity<ProductDto>(productWithCategory, HttpStatus.CREATED);
    }

    /**
     * @param categoryId,productId
     * @return ProductDto
     * @author Manmohan Sharma
     * @apiNote To updateWithCategory in database
     * @since 1.0v
     */
    @PutMapping(UPDATE_WITH_CATEGORY)
    public ResponseEntity<ProductDto> updateCategoryWithProduct
    (@RequestBody ProductDto productDto,
     @PathVariable String categoryId,
     @PathVariable String productId) {
        log.info("Initiated request  for update Category with categoryId:{} and productId:{}", categoryId, productId);
        ProductDto productDto1 = productService.updateCategory(productId, categoryId);
        log.info("Completed request for update Category with categoryId:{} and productId:{}", categoryId, productId);
        return new ResponseEntity<ProductDto>(productDto1, HttpStatus.OK);
    }
    /**
     * @param categoryId
     * @return PegeableResponse<ProductDto>
     * @author Manmohan Sharma
     * @apiNote To get All Product of Category from database
     * @since 1.0v
     */
    @GetMapping(GET_ALL_PRODUCT_OF_CATEGORY)
    public ResponseEntity<PegeableResponse<ProductDto>> getAllProductofCategory
            (@PathVariable String categoryId,
             @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
             )
    {
        log.info("Initiated request  for get all product of Category with categoryId:{}",categoryId);
        PegeableResponse<ProductDto> response = this.productService.getAllofCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed request for get all product of Category with categoryId:{}",categoryId);
        return new ResponseEntity<PegeableResponse<ProductDto>>(response,HttpStatus.OK);
    }

}

