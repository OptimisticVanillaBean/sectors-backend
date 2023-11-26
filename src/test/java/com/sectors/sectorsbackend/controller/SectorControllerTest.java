package com.sectors.sectorsbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sectors.sectorsbackend.domain.Sector;
import com.sectors.sectorsbackend.service.SectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = SectorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SectorService sectorService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Sector> sectorList;

    @BeforeEach
    void setUp() {
        sectorList = new ArrayList<>();
        sectorList.add(new Sector(null, "Sector 1"));
        sectorList.add(new Sector(null, "Sector 2"));
        sectorList.add(new Sector(null, "Sector 3"));
    }

    @Test
    public void canGetAllSectors() throws Exception {
        given(sectorService.getAllSectors()).willReturn(sectorList);

        mockMvc.perform(get("/sectors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(sectorList)));
    }

    @Test
    public void cannotGetAllSectorsWhenDataAccessErrorOccurs() throws Exception {
        given(sectorService.getAllSectors()).willThrow(new DataAccessException("Error accessing data") {});

        mockMvc.perform(get("/sectors"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
