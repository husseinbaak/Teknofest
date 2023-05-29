package com.example.tradensbackendv2.services.impl;

import com.example.tradensbackendv2.models.VehicleStatus;
import com.example.tradensbackendv2.repository.VehicleStatusRepository;
import com.example.tradensbackendv2.services.VehicleStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleStatusServiceImp implements VehicleStatusService {
   private final VehicleStatusRepository vehicleStatusRepository;
    @Override
    public void saveVehicleData(VehicleStatus vehicleStatus) {
        vehicleStatusRepository.save(vehicleStatus);
    }

    @Override
    public VehicleStatus getLastVehicleData() {
        return vehicleStatusRepository.getLastStatus();
    }

    @Override
    public List<VehicleStatus> getAllRecordedVehicleData() {
        return vehicleStatusRepository.findAll();
    }
}
