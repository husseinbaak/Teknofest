package com.example.tradensbackendv2.services.impl;

import com.example.tradensbackendv2.models.Sensors;
import com.example.tradensbackendv2.repository.SensorsRepository;
import com.example.tradensbackendv2.services.SensorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorsServiceImp implements SensorsService {
    private final SensorsRepository sensorsRepository;
    @Override
    public void saveSensorsData(Sensors sensors) {
        sensorsRepository.save(sensors);
    }

    @Override
    public Sensors getLastSensorsData() {
        return sensorsRepository.getLastSensorsData();
    }

    @Override
    public List<Sensors> getAllRecordedSensorsData() {
        return sensorsRepository.findAll();
    }
}
