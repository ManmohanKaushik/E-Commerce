package com.bikkadit.electronicstore.services.impl;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.Role;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.repository.RoleRepository;
import com.bikkadit.electronicstore.repository.UserRepo;
import com.bikkadit.electronicstore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${normal.role.id}")
    private String normalRoleId;

    @Value("${admin.role.id}")
    private String adminRoleId;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Initiating DAO call for save user ");
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findById(normalRoleId).get();
        User user = this.modelMapper.map(userDto, User.class);
        user.getRoles().add(role);
        User save = this.userRepo.save(user);
        log.info("Completed  DAO call for save user");
        return this.modelMapper.map(save, UserDto.class);

    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Initiating DAO call for update user with userId:{}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        user.setGender(userDto.getGender());
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        User save = this.userRepo.save(user);
        log.info("Completed  DAO call for update user with userId:{}", userId);
        return this.modelMapper.map(save, UserDto.class);
    }

    @Override
    public UserDto getUserByid(String userId) {
        log.info("Initiating DAO call for get user with userId:{}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        log.info("Completed  DAO call for get user with userId:{}", userId);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByemail(String email) {
        log.info("Initiating DAO call for get user with  email:{}", email);
        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.EMAILID_NOTFOUND));
        log.info("Completed  DAO call for get user with email:{}", email);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public PegeableResponse<UserDto> getall(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating DAO call for get all user ");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> all = this.userRepo.findAll(pageable);

        PegeableResponse<UserDto> response = Helper.pegeableResponse(all, UserDto.class);
        log.info("Completed  DAO call for get all user ");
        return response;
    }

    @Override
    public void deleteUser(String userId) {
        log.info("Initiating DAO call for delete user with userId:{}", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        String fullPath = imagePath + user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        } catch (IOException ei) {
            ei.printStackTrace();
        }
        this.userRepo.deleteById(userId);
        log.info("Completed  DAO call for delete user with userId:{}", userId);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Initiating DAO call for search user with keyword:{}", keyword);
        List<UserDto> collectDto = this.userRepo.findByNameContaining(keyword)
                .stream()
                .map((e) -> this.modelMapper.map(e, UserDto.class))
                .collect(Collectors.toList());
        log.info("Completed  DAO call for search user with keyword:{}", keyword);
        return collectDto;
    }


}
