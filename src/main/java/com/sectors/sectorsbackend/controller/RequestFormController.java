package com.sectors.sectorsbackend.controller;

import com.sectors.sectorsbackend.dto.RequestFormDTO;
import com.sectors.sectorsbackend.dto.SectorDTO;
import com.sectors.sectorsbackend.exception.UserNotFoundException;
import com.sectors.sectorsbackend.service.RequestFormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/requestForms")
public class RequestFormController {
    private final RequestFormService requestFormService;

    public RequestFormController(RequestFormService requestFormService) {
        this.requestFormService = requestFormService;
    }

    @GetMapping("/{user}")
    public ResponseEntity<?> getLatestWithSectors(@PathVariable String user) {
        return requestFormService.getLatestForUserWithSectors(user)
                .map(form -> {
                    RequestFormDTO formDTO = RequestFormDTO.builder()
                            .user(form.getUser().getName())
                            .termsAgreed(form.getTermsAgreed())
                            .sectors(form.getSectors().stream()
                                    .map(sector -> new SectorDTO(sector.getName(), sector.getId()))
                                    .collect(Collectors.toSet()))
                            .build();
                    return ResponseEntity.ok(formDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRequestForm(@RequestBody RequestFormDTO requestFormDTO) {
        if (!requestFormDTO.getTermsAgreed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user has not agreed to the terms and conditions.");
        }
        try {
            RequestFormDTO createdRequestForm = requestFormService.createRequestForm(requestFormDTO);
            return new ResponseEntity<>(createdRequestForm, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (HttpMessageNotReadableException e) {
            return new ResponseEntity<>("Faulty request body", HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException.ServiceUnavailable e) {
            return new ResponseEntity<>("Service is currently unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
