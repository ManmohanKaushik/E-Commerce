package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstants;
import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * @author Manmohan Sharma
     * @apiNote To create data in database
     * @param userDto
     * @since 1.0v
     * @return UserDto
     */
    @PostMapping("/user")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        log.info("Request is sending in service layer for save user.");
        UserDto save = this.userService.createUser(userDto);
        log.info("Response has received from service layer for save user.");
        return new ResponseEntity<UserDto>(save, HttpStatus.CREATED);
    }

    /**
     * @author Manmohan Sharma
     * @apiNote To update data in database
     * @param userId
     * @since 1.0v
     * @return UserDto
     */
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Request is sending in service layer for update user containing userId:{}",userId);
        UserDto dto = this.userService.updateUser(userDto, userId);
        log.info("Response has received from service layer for updated user containing userId:{}",userId);
        return new ResponseEntity<UserDto>(dto, HttpStatus.CREATED);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To get data by id from database
     * @since 1.0v
     * @param userId
     * @return UserDto
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserByid(@PathVariable String userId) {
        log.info("Request is sending in service layer for get user by userId:{}",userId);
        UserDto byid = this.userService.getUserByid(userId);
        log.info("Response has received from service layer for get user by  userId:{}",userId);
        return new ResponseEntity<UserDto>(byid, HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To get data by email from database
     * @since 1.0v
     * @param email
     * @return UserDto
     */
    @GetMapping("/userEmail/{email}")
    public ResponseEntity<UserDto> getUserByemail(@PathVariable String email) {
        log.info("Request is sending in service layer for get user by  email:{}",email);
        UserDto byemail = this.userService.getUserByemail(email);
        log.info("Response has received from service layer for get user by  email:{}",email);
        return new ResponseEntity<UserDto>(byemail, HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To get all data from database
     * @since 1.0v
     * @return UserDto
     */
    @GetMapping("/userAll")
    public ResponseEntity<PegeableResponse<UserDto>> getall(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
    ) {

        log.info("Request is sending in service layer for get all user data ");
        PegeableResponse<UserDto> getall = this.userService.getall(pageNumber, pageSize, sortBy, sortDir);

        log.info("Response has received from service layer for get all user data ");
        return new ResponseEntity<PegeableResponse<UserDto>>(getall,HttpStatus.OK);
    }
    /**
     * @apiNote Data deleted Successfully in database
     * @author Manmohan Sharma
     * @param userId
     * @since 1.0v
     * @return ApiResponse
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> deleteByid(@PathVariable String userId) throws IOException {
        log.info("Request is sending in service layer for delete user  userId:{}",userId);
        this.userService.deleteUser(userId);
        log.info("Response has received from service layer for delete user userId:{}",userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(MessageConstants.RESOURCEDELETE, true), HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To search data by keyword from database
     * @since 1.0v
     * @param keyword
     * @return UserDto
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Request is sending in service layer for search user by keyword:{}",keyword);
        List<UserDto> searchUser = this.userService.searchUser(keyword);
        log.info("Response has received from service layer for search user by keyword:{}",keyword);
        return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
    }
}
