package com.raven.service;

import com.raven.model.Office;
import com.raven.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    public List<Office> getOffices() {
        return officeRepository.findAll();
    }

}
