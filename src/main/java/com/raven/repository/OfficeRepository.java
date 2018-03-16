package com.raven.repository;

import com.raven.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    List<Office> findAll();

    Office findByName(String officeName);

}
