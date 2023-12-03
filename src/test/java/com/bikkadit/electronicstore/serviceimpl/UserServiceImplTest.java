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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserServicesImpl userServices;
    User user;
    @BeforeEach
public void init(){


}


    @Test
    public void getUserByidTest(){
        String userId="123klk";
        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        UserDto AcutalUserDto = userServices.getUserByid(userId);
        Assertions.assertNotNull(AcutalUserDto);
        Assertions.assertEquals(user.getUserId(),AcutalUserDto.getUserId(),"userId not found");

    }
    @Test
    public void createUserTest(){


    }
}
