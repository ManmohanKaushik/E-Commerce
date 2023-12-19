package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.repository.UserRepo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepo userRepo;
    @Autowired
    private UserService userServices;

    @Autowired
    private ModelMapper mapper;
    User user;

    @BeforeEach
    public void init() {
        user = User.builder()
                .name("Monu")
                .email("ram123@gamail.com")
                .about("Testing Demo")
                .gender("Male")
                .imageName("monu.jpg")
                .password("Monu12@20")
                .build();

    }


    @Test
    public void getUserByidTest() {
        String userId = "123klk";
        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        UserDto actualUserDto = userServices.getUserByid(userId);
        assertNotNull(actualUserDto);
        Assertions.assertEquals(user.getUserId(), actualUserDto.getUserId(), "userId not found");
    }


    @Test
    public void getUserByemailTest() {
        String emailId = "ram123@gamail.com";
        Mockito.when(userRepo.findByEmail(emailId)).thenReturn(Optional.of(user));
        UserDto actualemail = userServices.getUserByemail(emailId);
        assertNotNull(actualemail);
        Assertions.assertEquals(user.getEmail(), actualemail.getEmail(), "Email not found");

    }

    @Test
    public void updateUserTest() {
        UserDto userDto = UserDto.builder()
                .name("Monu")
                .about("I am softEng.")
                .gender("Male")
                .imageName("monu.jpg")
                .build();
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        // UserDto actualUserDto1 = userServices.updateUser(userDto, userId);
        UserDto actualDto = mapper.map(user, UserDto.class);
        assertNotNull(actualDto);
        Assertions.assertEquals(userDto.getName(), actualDto.getName());


    }


    @Test
    public void createUserTest() {
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userServices.createUser(mapper.map(user, UserDto.class));
        String actualName=user1.getName();
       // assertNotNull(user1);
        System.out.println(actualName);
        String expectedName="Monu";
        Assertions.assertEquals(expectedName,actualName);


    }

    @Test
    public void deleteUserTest() throws IOException {
        String userId = "123klmn";
       // String imageName= user.getImageName();
        Mockito.when(userRepo.findById("123klmn")).thenReturn(Optional.of(user));
        userServices.deleteUser(userId);
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }

    @Test
    public void getallTest() {
     User   user1 = User.builder()
                .name("Sonu")
                .email("ram123@gamail.com")
                .about("Testing Demo")
                .gender("Male")
                .imageName("monu.jpg")
                .password("Monu12@20")
                .build();
        User user2 = User.builder()
                .name("Tonu")
                .email("ram123@gamail.com")
                .about("Testing Demo")
                .gender("Male")
                .imageName("monu.jpg")
                .password("Monu12@20")
                .build();
        List<User> userList= Arrays.asList(user,user1,user2);
        Page<User> page= new PageImpl<>(userList);
        Mockito.when(userRepo.findAll((Pageable)Mockito.any() )).thenReturn(page);
        PegeableResponse<UserDto> getall = userServices.getall(1,2,"name","asc");
        Assertions.assertEquals(3,getall.getContent().size());
    }

}
