package com.sectors.sectorsbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sectors.sectorsbackend.domain.User;
import com.sectors.sectorsbackend.dto.RequestFormDTO;
import com.sectors.sectorsbackend.dto.SectorDTO;
import com.sectors.sectorsbackend.dto.UserDTO;
import com.sectors.sectorsbackend.exception.UserNotFoundException;
import com.sectors.sectorsbackend.repository.UserRepository;
import com.sectors.sectorsbackend.service.RequestFormService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RequestFormController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RequestFormControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestFormService requestFormService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestFormDTO requestFormDTO;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setPassword("Password");
        user.setName("Test User");
        given(userService.saveUser(ArgumentMatchers.any())).willReturn(UserDTO.builder()
                .username(user.getName()).password(user.getPassword()).build());
        given(userRepository.findByName(user.getName())).willReturn(Optional.of(user));
        SectorDTO sector1 = SectorDTO.builder().name("Sector 1").id(1L).build();
        SectorDTO sector2 = SectorDTO.builder().name("Sector 2").id(2L).build();

        Set<SectorDTO> sectors = new HashSet<>();
        sectors.add(sector1);
        sectors.add(sector2);

        this.requestFormDTO = RequestFormDTO.builder()
                .user("Test User")
                .sectors(sectors)
                .termsAgreed(true)
                .build();
    }

    @Test
    public void canCreateRequestForm() throws Exception {
        given(requestFormService.createRequestForm(ArgumentMatchers.any())).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/requestForms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestFormDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void cannotCreateRequestFormWhenTermsNotAgreed() throws Exception {
        requestFormDTO.setTermsAgreed(false);

        ResultActions response = mockMvc.perform(post("/requestForms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestFormDTO)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void cannotCreateRequestFormWhenUserNotFound() throws Exception {
        given(requestFormService.createRequestForm(ArgumentMatchers.any())).willThrow(new UserNotFoundException("User not found"));

        ResultActions response = mockMvc.perform(post("/requestForms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestFormDTO)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void cannotCreateRequestFormWhenRequestBodyIsFaulty() throws Exception {
        mockMvc.perform(post("/requestForms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("faulty json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

