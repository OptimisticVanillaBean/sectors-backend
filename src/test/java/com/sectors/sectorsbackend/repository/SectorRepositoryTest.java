package com.sectors.sectorsbackend.repository;

import com.sectors.sectorsbackend.domain.Sector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SectorRepositoryTest {

    @Autowired
    private SectorRepository sectorRepository;

    @Test
    public void testFindByName() {
        String sectorName = "testSector";
        Sector sector = new Sector();
        sector.setName(sectorName);
        sectorRepository.save(sector);

        Sector foundSector = sectorRepository.findByName(sectorName).orElse(null);
        assertThat(foundSector).isNotNull();
        assertThat(foundSector.getName()).isEqualTo(sectorName);
    }

    @Test
    public void testFindAll() {
        Sector sector1 = new Sector();
        sector1.setName("testSector1");
        sectorRepository.save(sector1);

        Sector sector2 = new Sector();
        sector2.setName("testSector2");
        sectorRepository.save(sector2);

        Iterable<Sector> sectors = sectorRepository.findAll();
        assertThat(sectors).hasSize(2).contains(sector1, sector2);
    }

}
