package com.sectors.sectorsbackend.service;

import com.sectors.sectorsbackend.domain.RequestForm;
import com.sectors.sectorsbackend.domain.Sector;
import com.sectors.sectorsbackend.domain.User;
import com.sectors.sectorsbackend.dto.RequestFormDTO;
import com.sectors.sectorsbackend.dto.SectorDTO;
import com.sectors.sectorsbackend.exception.UserNotFoundException;
import com.sectors.sectorsbackend.repository.RequestFormRepository;
import com.sectors.sectorsbackend.repository.SectorRepository;
import com.sectors.sectorsbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequestFormService {
    private final RequestFormRepository requestFormRepository;
    private final UserRepository userRepository;
    private final SectorRepository sectorRepository;

    public RequestFormService(RequestFormRepository requestFormRepository, UserRepository userRepository, SectorRepository sectorRepository) {
        this.requestFormRepository = requestFormRepository;
        this.userRepository = userRepository;
        this.sectorRepository = sectorRepository;
    }

    public Optional<RequestForm> getLatestForUserWithSectors(String userName) {
        List<RequestForm> all = requestFormRepository.findByUserName(userName);
        return all.stream()
                .max(Comparator.comparing(RequestForm::getCreatedAt));
    }

    public RequestFormDTO createRequestForm(RequestFormDTO requestFormDTO) {
        RequestForm requestForm = new RequestForm();
        requestForm.setTermsAgreed(requestFormDTO.getTermsAgreed());
        requestForm.setCreatedAt(Instant.now());

        User user = userRepository.findByName(requestFormDTO.getUser())
                .orElseThrow(() -> new UserNotFoundException("User not found with name: " + requestFormDTO.getUser()));

        requestForm.setUser(user);

        Set<Sector> sectors = requestFormDTO.getSectors().stream()
                .map(sectorDTO -> sectorRepository.findByName(sectorDTO.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        requestForm.setSectors(sectors);

        userRepository.save(user);

        RequestForm savedRequestForm = requestFormRepository.save(requestForm);

        return RequestFormDTO.builder()
                .sectors(savedRequestForm.getSectors().stream()
                        .map(sector -> SectorDTO.builder()
                                .name(sector.getName())
                                .id(sector.getId())
                                .build())
                        .collect(Collectors.toSet()))
                .termsAgreed(savedRequestForm.getTermsAgreed())
                .user(savedRequestForm.getUser().getName())
                .build();
    }

}

