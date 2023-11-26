package com.sectors.sectorsbackend.controller;

import com.sectors.sectorsbackend.domain.Sector;
import com.sectors.sectorsbackend.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sectors")
public class SectorController {

    private final SectorService sectorService;

    @Autowired
    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSectors() {
        try {
            Iterable<Sector> sectors = sectorService.getAllSectors();
            return new ResponseEntity<>(sectors, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error accessing data", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
