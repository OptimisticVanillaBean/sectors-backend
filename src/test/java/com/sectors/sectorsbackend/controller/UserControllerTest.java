package com.sectors.sectorsbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sectors.sectorsbackend.dto.UserDTO;
import com.sectors.sectorsbackend.exception.UserAlreadyExistsException;
import com.sectors.sectorsbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<UserDTO> userList;

    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        UserDTO user1 = UserDTO.builder().username("Fred").password("pw1").build();
        UserDTO user2 = UserDTO.builder().username("Alice").password("pw2").build();
        this.userList.add(user1);
        this.userList.add(user2);

    }

    @Test
    public void canRegisterUser() throws Exception {
        given(userService.saveUser(ArgumentMatchers.any())).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userList.get(0))));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void cannotRegisterUserWhenUserAlreadyExists() throws Exception {
        given(userService.saveUser(ArgumentMatchers.any())).willThrow(new UserAlreadyExistsException("User already exists"));

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userList.get(0))))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void cannotRegisterUserWhenRequestBodyIsFaulty() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("faulty json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void canGetAllUsers() throws Exception {
        given(userService.findAllUsers()).willReturn(userList);

        mockMvc.perform(get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userList)));
    }

    @Test
    public void cannotGetAllUsersWhenDataAccessErrorOccurs() throws Exception {
        given(userService.findAllUsers()).willThrow(new DataAccessException("Error accessing data") {});

        mockMvc.perform(get("/users"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
