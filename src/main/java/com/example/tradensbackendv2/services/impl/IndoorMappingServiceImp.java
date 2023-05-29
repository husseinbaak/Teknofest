package com.example.tradensbackendv2.services.impl;

import com.example.tradensbackendv2.models.IndoorMapping;
import com.example.tradensbackendv2.repository.IndoorMappingRepository;
import com.example.tradensbackendv2.services.IndoorMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndoorMappingServiceImp implements IndoorMappingService {
    private final IndoorMappingRepository indoorMappingRepository;

    @Override
    public void saveIndoorData(IndoorMapping indoorMapping) {
        indoorMappingRepository.save(indoorMapping);
    }

    @Override
    public IndoorMapping getLastIndoorData() {
        return indoorMappingRepository.getLastRecord();
    }

    @Override
    public List<IndoorMapping> getAllRecordedIndoorData() {
        return indoorMappingRepository.findAll();
    }
}
