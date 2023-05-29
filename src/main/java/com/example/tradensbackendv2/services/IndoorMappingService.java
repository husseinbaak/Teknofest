package com.example.tradensbackendv2.services;

import com.example.tradensbackendv2.models.IndoorMapping;


import java.util.List;

public interface IndoorMappingService {

    void saveIndoorData(IndoorMapping indoorMapping);

    IndoorMapping getLastIndoorData();

    List<IndoorMapping> getAllRecordedIndoorData();
}
