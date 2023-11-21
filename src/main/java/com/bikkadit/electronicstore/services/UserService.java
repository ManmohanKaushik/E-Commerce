package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    UserDto getUserByid(String userId );

    UserDto getUserByemail(String email);

    PegeableResponse<UserDto> getall(int pageNumber, int pageSize, String sortBy, String sortDir);

    void deleteUser(String userId );

    public List<UserDto> searchUser(String keyword);
}
