package com.example.tradensbackendv2.repository;

import com.example.tradensbackendv2.models.Sensors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorsRepository extends JpaRepository<Sensors,Integer> {
    @Query(value= "select * from sensors order by id desc limit 1",nativeQuery = true)
    Sensors getLastSensorsData();
}
