package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)

public class UserServiceImplTest {
   
    private UserRepo userRepo;
    @InjectMocks
    private UserServicesImpl userServices;
    @Mock
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
                .imageName("loardshiva.png")
                .password("Monu12@20")
                .build();
       
    }

    @Test
    public void getUserByidTest() {
        String userId="123klk";
        Mockito.when(userRepo.findById("123klk")).thenReturn(Optional.of(user));
        UserDto AcutalUserDto = userServices.getUserByid(userId);
        Assertions.assertNotNull(AcutalUserDto);
        Assertions.assertEquals(user.getUserId(), AcutalUserDto.getUserId(), "userId not found");

    }

    @Test
    public void getUserByemailTest() {
        String email = "ram123@gamail.com";
        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto actualemail = userServices.getUserByemail(email);
        Assertions.assertNotNull(actualemail);
        Assertions.assertEquals(user.getEmail(), actualemail.getEmail());

    }

    @Test
    public void updateUserTest() {
        UserDto userDto = UserDto.builder()
                .name("Monu Sharma")
                .about("I am softEng.")
                .gender("Male")
                .imageName("loardshiva.png")
                .build();
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        // UserDto actualUserDto1 = userServices.updateUser(userDto, userId);
        UserDto actualDto = mapper.map(user, UserDto.class);
        Assertions.assertNotNull(actualDto);
        Assertions.assertEquals(userDto.getName(), actualDto.getName());


    }


    @Test
    public void createUserTest() {
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userServices.createUser(mapper.map(user, UserDto.class));
        Assertions.assertNotNull(user1);
        //System.out.println(user1.getName());
        Assertions.assertEquals("Monu", user1.getName(), "user not found ");


    }

    @Test
    public void deleteUserTest() throws IOException {
        String userId="klkkuyy";
        Mockito.when(userRepo.findById("klkkuyy")).thenReturn(Optional.of(user));
        userServices.deleteUser(userId);
        Mockito.verify(userRepo,Mockito.times(1)).delete(user);
    }

    @Test
    public void getall() {

    }
}
