package com.sectors.sectorsbackend.repository;

import com.sectors.sectorsbackend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByName() {
        String userName = "testUser";
        String userPassword = "testPassword";
        User user = new User();
        user.setName(userName);
        user.setPassword(userPassword);
        userRepository.save(user);

        User foundUser = userRepository.findByName(userName).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(userName);
    }

    @Test
    public void testFindAll() {
        User user1 = new User();
        user1.setName("testUser1");
        user1.setPassword("testPassword1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("testUser2");
        user2.setPassword("testPassword2");
        userRepository.save(user2);

        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(2).contains(user1, user2);
    }

}
