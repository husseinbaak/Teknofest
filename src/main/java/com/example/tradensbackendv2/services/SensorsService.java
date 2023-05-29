package com.example.tradensbackendv2.services;


import com.example.tradensbackendv2.models.Sensors;

import java.util.List;

public interface SensorsService {
    void saveSensorsData(Sensors sensors);

    Sensors getLastSensorsData();

    List<Sensors> getAllRecordedSensorsData();
}
