package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstants;
import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.payload.ImageResponse;
import com.bikkadit.electronicstore.services.FileService;
import com.bikkadit.electronicstore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @param userDto
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To create data in database
     * @since 1.0v
     */
    @PostMapping("/user")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("Request is sending in service layer for save user.");
        UserDto save = this.userService.createUser(userDto);
        log.info("Response has received from service layer for save user.");
        return new ResponseEntity<UserDto>(save, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To update data in database
     * @since 1.0v
     */
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Request is sending in service layer for update user containing userId:{}", userId);
        UserDto dto = this.userService.updateUser(userDto, userId);
        log.info("Response has received from service layer for updated user containing userId:{}", userId);
        return new ResponseEntity<UserDto>(dto, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To get data by id from database
     * @since 1.0v
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserByid(@PathVariable String userId) {
        log.info("Request is sending in service layer for get user by userId:{}", userId);
        UserDto byid = this.userService.getUserByid(userId);
        log.info("Response has received from service layer for get user by  userId:{}", userId);
        return new ResponseEntity<UserDto>(byid, HttpStatus.OK);
    }

    /**
     * @param email
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To get data by email from database
     * @since 1.0v
     */
    @GetMapping("/userEmail/{email}")
    public ResponseEntity<UserDto> getUserByemail(@PathVariable String email) {
        log.info("Request is sending in service layer for get user by  email:{}", email);
        UserDto byemail = this.userService.getUserByemail(email);
        log.info("Response has received from service layer for get user by  email:{}", email);
        return new ResponseEntity<UserDto>(byemail, HttpStatus.OK);
    }

    /**
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To get all data from database
     * @since 1.0v
     */
    @GetMapping("/userAll")
    public ResponseEntity<PegeableResponse<UserDto>> getall(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {

        log.info("Request is sending in service layer for get all user data ");
        PegeableResponse<UserDto> getall = this.userService.getall(pageNumber, pageSize, sortBy, sortDir);

        log.info("Response has received from service layer for get all user data ");
        return new ResponseEntity<PegeableResponse<UserDto>>(getall, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return ApiResponse
     * @apiNote Data deleted Successfully in database
     * @author Manmohan Sharma
     * @since 1.0v
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> deleteByid(@PathVariable String userId) throws IOException {
        log.info("Request is sending in service layer for delete user  userId:{}", userId);
        this.userService.deleteUser(userId);
        log.info("Response has received from service layer for delete user userId:{}", userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(MessageConstants.RESOURCEDELETE, true), HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To search data by keyword from database
     * @since 1.0v
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Request is sending in service layer for search user by keyword:{}", keyword);
        List<UserDto> searchUser = this.userService.searchUser(keyword);
        log.info("Response has received from service layer for search user by keyword:{}", keyword);
        return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return ImageResponse
     * @author Manmohan Sharma
     * @apiNote To uploadUserImage in database
     * @since 1.0v
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam ("userImage")MultipartFile image, @PathVariable String userId) throws IOException {
        log.info("Request is sending in service layer for uploadUserImage with userId:{}", userId);
        String imageName = this.fileService.uploadFile(image, imageUploadPath);
        UserDto userDto = this.userService.getUserByid(userId);
        userDto.setImageName(imageName);
        UserDto updatedImage = this.userService.updateUser(userDto, userId);
        ImageResponse imageResponse = ImageResponse.builder().message(MessageConstants.USER_IMAGE).status(HttpStatus.CREATED).imageName(imageName).Success(true).build();
        log.info("Response has received from service layer for uploadUserImage with userId:{}", userId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void serverUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = this.userService.getUserByid(userId);
        log.info("User image name :{}", user.getImageName());
        InputStream resource = this.fileService.getResource(imageUploadPath, user.getImageName());
       // response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
       // response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
