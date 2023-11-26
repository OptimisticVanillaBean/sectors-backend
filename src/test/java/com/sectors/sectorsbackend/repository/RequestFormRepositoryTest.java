package com.sectors.sectorsbackend.repository;

import com.sectors.sectorsbackend.domain.RequestForm;
import com.sectors.sectorsbackend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RequestFormRepositoryTest {

    @Autowired
    private RequestFormRepository requestFormRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        User user = new User();
        user.setName("Test User");
        user.setPassword("Password");
        userRepository.save(user);

        RequestForm requestForm1 = new RequestForm();
        requestForm1.setTermsAgreed(true);
        requestForm1.setUser(user);
        requestFormRepository.save(requestForm1);

        RequestForm requestForm2 = new RequestForm();
        requestForm2.setTermsAgreed(false);
        requestForm2.setUser(user);
        requestFormRepository.save(requestForm2);

        Iterable<RequestForm> requestForms = requestFormRepository.findAll();
        assertThat(requestForms).hasSize(2).contains(requestForm1, requestForm2);
    }
}