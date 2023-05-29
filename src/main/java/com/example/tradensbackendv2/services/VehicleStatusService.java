package com.example.tradensbackendv2.services;


import com.example.tradensbackendv2.models.VehicleStatus;

import java.util.List;

public interface VehicleStatusService {
    void saveVehicleData(VehicleStatus vehicleStatus);

    VehicleStatus getLastVehicleData();

    List<VehicleStatus> getAllRecordedVehicleData();
}
