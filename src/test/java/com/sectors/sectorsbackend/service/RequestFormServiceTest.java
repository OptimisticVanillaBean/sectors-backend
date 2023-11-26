package com.sectors.sectorsbackend.service;

import com.sectors.sectorsbackend.domain.RequestForm;
import com.sectors.sectorsbackend.domain.Sector;
import com.sectors.sectorsbackend.domain.User;
import com.sectors.sectorsbackend.dto.RequestFormDTO;
import com.sectors.sectorsbackend.dto.SectorDTO;
import com.sectors.sectorsbackend.repository.RequestFormRepository;
import com.sectors.sectorsbackend.repository.SectorRepository;
import com.sectors.sectorsbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


public class RequestFormServiceTest {

    @InjectMocks
    private RequestFormService requestFormService;

    @Mock
    private RequestFormRepository requestFormRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SectorRepository sectorRepository;

    private List<RequestForm> requestFormList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user = new User();
        user.setName("User1");

        RequestForm requestForm1 = new RequestForm();
        requestForm1.setCreatedAt(Instant.now());
        requestForm1.setUser(user);
        requestForm1.setId(1L);

        RequestForm requestForm2 = new RequestForm();
        requestForm2.setCreatedAt(Instant.now().plusSeconds(1));
        requestForm2.setUser(user);
        requestForm2.setId(2L);

        requestFormList = Arrays.asList(requestForm1, requestForm2);
    }

    @Test
    public void getLatestForUserWithSectorsTest() {
        when(requestFormRepository.findByUserName("User1")).thenReturn(requestFormList);

        Optional<RequestForm> latestRequestForm = requestFormService.getLatestForUserWithSectors("User1");

        assertEquals(requestFormList.get(1), latestRequestForm.get());
        assertEquals(requestFormList.get(1).getId(), 2L);
    }

    @Test
    public void createRequestFormTest() {
        RequestFormDTO requestFormDTO = RequestFormDTO.builder()
                .user("User1")
                .termsAgreed(true)
                .sectors(Set.of(SectorDTO.builder().name("Sector1").build()))
                .build();

        User user = new User();
        user.setName("User1");

        Sector sector = new Sector();
        sector.setName("Sector1");

        when(userRepository.findByName("User1")).thenReturn(Optional.of(user));
        when(sectorRepository.findByName("Sector1")).thenReturn(Optional.of(sector));
        when(requestFormRepository.save(any(RequestForm.class))).thenAnswer(i -> i.getArguments()[0]);

        RequestFormDTO savedRequestFormDTO = requestFormService.createRequestForm(requestFormDTO);

        assertEquals(requestFormDTO.getUser(), savedRequestFormDTO.getUser());
        assertEquals(requestFormDTO.getTermsAgreed(), savedRequestFormDTO.getTermsAgreed());
        assertEquals(requestFormDTO.getSectors().iterator().next().getName(), savedRequestFormDTO.getSectors().iterator().next().getName());
    }
}