package com.example.tradensbackendv2.repository;

import com.example.tradensbackendv2.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Query(value = "select * from route order by id desc limit 1",nativeQuery = true)
    Route getLastRoute();
}