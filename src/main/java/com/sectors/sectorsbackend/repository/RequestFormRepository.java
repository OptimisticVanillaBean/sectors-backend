package com.sectors.sectorsbackend.repository;

import com.sectors.sectorsbackend.domain.RequestForm;
import com.sectors.sectorsbackend.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestFormRepository extends CrudRepository<RequestForm, Long> {
    @Override
    List<RequestForm> findAll();

    List<RequestForm> findByUserName(String userName);
}
