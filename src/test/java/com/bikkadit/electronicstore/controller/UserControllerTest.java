package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.services.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper mapper;
    @MockBean
    private UserService userService;
    private User user;


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
    public void saveUserTest() throws Exception {
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());


    }

    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Test
    public void updateUserTest() throws Exception {
        String userId = "12345";
        UserDto dto = this.mapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());


    }
    @Test
    public void  getUserByidTest() throws Exception {
        String userId = "12345";
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByid(userId)).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    public void getallUserTest() throws Exception {
        UserDto object1 = UserDto.builder().name("Rahul").email("rha45@gmail.com").password("R156@").about("java devloper").build();
        UserDto object2 = UserDto.builder().name("Sonu").email("sha45@gmail.com").password("R656@").about("java devloper").build();
        UserDto object3 = UserDto.builder().name("Ram").email("raa45@gmail.com").password("R457@").about("java devloper").build();

        PegeableResponse<UserDto> pegeableResponse = new PegeableResponse<>();
        pegeableResponse.setContent(Arrays.asList(object1, object2, object3));
        pegeableResponse.setLastPage(false);
        pegeableResponse.setPageNumber(10);
        pegeableResponse.setPageSize(10);
        pegeableResponse.setTotalElements(100);

        Mockito.when(userService.getall(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pegeableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/userAll/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(pegeableResponse))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


}

