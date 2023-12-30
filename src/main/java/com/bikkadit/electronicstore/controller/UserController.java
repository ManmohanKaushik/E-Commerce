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

import static com.bikkadit.electronicstore.constants.UriConstants.*;


@RestController
@RequestMapping(USER_URI)
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
    @PostMapping(SAVE_USER)
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("Initiated request  for save user.");
        UserDto save = this.userService.createUser(userDto);
        log.info("Completed request for save user.");
        return new ResponseEntity<UserDto>(save, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To update data in database
     * @since 1.0v
     */
    @PutMapping(UPDATE_USER)
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Initiated request  for update user with userId:{}", userId);
        UserDto dto = this.userService.updateUser(userDto, userId);
        log.info("Completed request for updated user with userId:{}", userId);
        return new ResponseEntity<UserDto>(dto, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To get data by id from database
     * @since 1.0v
     */
    @GetMapping(GET_USER_BY_ID)
    public ResponseEntity<UserDto> getUserByid(@PathVariable String userId) {
        log.info("Initiated request  for get user with userId:{}", userId);
        UserDto byid = this.userService.getUserByid(userId);
        log.info("Completed request for get user with  userId:{}", userId);
        return new ResponseEntity<UserDto>(byid, HttpStatus.OK);
    }

    /**
     * @param email
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To get data by email from database
     * @since 1.0v
     */
    @GetMapping(GET_USER_BY_EMAIL)
    public ResponseEntity<UserDto> getUserByemail(@PathVariable String email) {
        log.info("Initiated request  for get user with email:{}", email);
        UserDto byemail = this.userService.getUserByemail(email);
        log.info("Completed request for get user with  email:{}", email);
        return new ResponseEntity<UserDto>(byemail, HttpStatus.OK);
    }

    /**
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To get all data from database
     * @since 1.0v
     */
    @GetMapping(GET_ALL_USER)
    public ResponseEntity<PegeableResponse<UserDto>> getallUser(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {

        log.info("Initiated request  for get all user data ");
        PegeableResponse<UserDto> getall = this.userService.getall(pageNumber, pageSize, sortBy, sortDir);

        log.info("Completed request for get all user data ");
        return new ResponseEntity<PegeableResponse<UserDto>>(getall, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return ApiResponse
     * @apiNote Data deleted Successfully in database
     * @author Manmohan Sharma
     * @since 1.0v
     */
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<ApiResponse> deleteByid(@PathVariable String userId) throws IOException {
        log.info("Initiated request  for delete user with userId:{}", userId);
        this.userService.deleteUser(userId);
        log.info("Completed request for delete user  with userId:{}", userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(MessageConstants.RESOURCEDELETE, true), HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return UserDto
     * @author Manmohan Sharma
     * @apiNote To search data by keyword from database
     * @since 1.0v
     */
    @GetMapping(SEARCH_USER)
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Initiated request  for search user with keyword:{}", keyword);
        List<UserDto> searchUser = this.userService.searchUser(keyword);
        log.info("Completed request for search user with keyword:{}", keyword);
        return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return ImageResponse
     * @author Manmohan Sharma
     * @apiNote To uploadUserImage in database
     * @since 1.0v
     */
    @PostMapping(UPLOAD_USER_IMAGE)
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam ("userImage")MultipartFile image, @PathVariable String userId) throws IOException {
        log.info("Initiated request  for upload UserImage with userId:{}", userId);
        String imageName = this.fileService.uploadFile(image, imageUploadPath);
        UserDto userDto = this.userService.getUserByid(userId);
        userDto.setImageName(imageName);
        UserDto updatedImage = this.userService.updateUser(userDto, userId);
        ImageResponse imageResponse = ImageResponse.builder().message(MessageConstants.USER_IMAGE).status(HttpStatus.CREATED).imageName(imageName).Success(true).build();
        log.info("Completed request for upload UserImage with userId:{}", userId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping(SERVER_USER_IMAGE)
    public void serverUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = this.userService.getUserByid(userId);
        log.info("Initiated request  for User image name :{}", user.getImageName());
        InputStream resource = this.fileService.getResource(imageUploadPath, user.getImageName());
       // response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
       // response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("Completed request for user image name :{} ",user.getImageName());

    }
}
