package com.example.tradensbackendv2.repository;

import com.example.tradensbackendv2.models.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Integer>{
    @Query(value= "select * from vehicle_status order by id desc limit 1", nativeQuery = true)
    VehicleStatus getLastStatus();
}