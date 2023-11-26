package com.sectors.sectorsbackend.service;

import com.sectors.sectorsbackend.domain.User;
import com.sectors.sectorsbackend.dto.UserDTO;
import com.sectors.sectorsbackend.exception.UserAlreadyExistsException;
import com.sectors.sectorsbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user1 = new User();
        user1.setName("User1");
        user1.setPassword("Password1");

        User user2 = new User();
        user2.setName("User2");
        user2.setPassword("Password2");

        userList = Arrays.asList(user1, user2);
    }

    @Test
    public void saveUserTest() {
        UserDTO userDTO = UserDTO.builder()
                .username("User3")
                .password("Password3")
                .build();

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("EncodedPassword3");
        when(userRepository.findByName(userDTO.getUsername())).thenReturn(java.util.Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        UserDTO savedUserDTO = userService.saveUser(userDTO);

        assertEquals(userDTO.getUsername(), savedUserDTO.getUsername());
        assertEquals("EncodedPassword3", savedUserDTO.getPassword());
    }

    @Test
    public void saveUserThrowsUserAlreadyExistsExceptionTest() {
        UserDTO userDTO = UserDTO.builder()
                .username("User1")
                .password("Password1")
                .build();

        when(userRepository.findByName(userDTO.getUsername())).thenReturn(java.util.Optional.of(userList.get(0)));

        try {
            userService.saveUser(userDTO);
        } catch (UserAlreadyExistsException e) {
            assertEquals("User already exists with name: " + userDTO.getUsername(), e.getMessage());
        }
    }

    @Test
    public void findAllUsersTest() {
        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> users = userService.findAllUsers();

        assertEquals(2, users.size());
        assertEquals("User1", users.get(0).getUsername());
        assertEquals("User2", users.get(1).getUsername());
    }
}