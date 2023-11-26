package com.sectors.sectorsbackend.service;

import com.sectors.sectorsbackend.domain.Sector;
import com.sectors.sectorsbackend.repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SectorServiceTest {

    @InjectMocks
    private SectorService sectorService;

    @Mock
    private SectorRepository sectorRepository;

    private List<Sector> sectorList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Sector sector1 = new Sector();
        sector1.setName("Sector1");

        Sector sector2 = new Sector();
        sector2.setName("Sector2");

        sectorList = Arrays.asList(sector1, sector2);
    }

    @Test
    public void getAllSectorsTest() {
        when(sectorRepository.findAll()).thenReturn(sectorList);

        List<Sector> sectors = sectorService.getAllSectors();

        assertEquals(2, sectors.size());
        assertEquals("Sector1", sectors.get(0).getName());
        assertEquals("Sector2", sectors.get(1).getName());
    }

}