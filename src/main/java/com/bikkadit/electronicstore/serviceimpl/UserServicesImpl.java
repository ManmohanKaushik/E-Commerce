package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.repository.UserRepo;
import com.bikkadit.electronicstore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServicesImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Request is sending into DAO layer for save user ");
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = this.modelMapper.map(userDto, User.class);
        User save = this.userRepo.save(user);
        log.info("Response has  received  from DAO layer for save user");
        return this.modelMapper.map(save, UserDto.class);

    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Request is sending into DAO layer for update user userId:{}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        user.setGender(userDto.getGender());
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        User save = this.userRepo.save(user);
        log.info("Response has  received  from DAO layer for update user userId:{}", userId);
        return this.modelMapper.map(save, UserDto.class);
    }

    @Override
    public UserDto getUserByid(String userId) {
        log.info("Request is sending into DAO layer for get user by userId:{}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        log.info("Response has  received  from DAO layer for get user by userId:{}", userId);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByemail(String email) {
        log.info("Request is sending into DAO layer for get user by email:{}", email);
        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.EMAILID_NOTFOUND));
        log.info("Response has  received  from DAO layer for get user by email:{}", email);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public PegeableResponse<UserDto> getall(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Request is sending into DAO layer for get all user ");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> all = this.userRepo.findAll(pageable);

        PegeableResponse<UserDto> response = Helper.pegeableResponse(all, UserDto.class);
        log.info("Response has  received  from DAO layer for get all user ");
        return response;
    }

    @Override
    public void deleteUser(String userId) throws IOException {
        log.info("Request is sending into DAO layer for delete user by userId:{}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        String fullPath = imagePath + user.getImageName();
       try {
           Path path = Paths.get(fullPath);
           Files.delete(path);
       }catch (NoSuchFileException e){
           e.printStackTrace();
       }catch (IOException ei){
           ei.printStackTrace();
       }
        this.userRepo.deleteById(userId);
        log.info("Response has  received  from DAO layer for delete user by userId:{}", userId);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Request is sending into DAO layer for search user by keyword:{}", keyword);
        List<UserDto> collectDto = this.userRepo.findByNameContaining(keyword)
                .stream()
                .map((e) -> this.modelMapper.map(e, UserDto.class))
                .collect(Collectors.toList());
        log.info("Response has  received  from DAO layer for search user by keyword:{}", keyword);
        return collectDto;
    }


}
