package com.example.tradensbackendv2.services.impl;

import com.example.tradensbackendv2.models.Route;
import com.example.tradensbackendv2.repository.RouteRepository;
import com.example.tradensbackendv2.services.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;

    @Override
    public void saveRoute(Route route) {
        route.setDataRecordingTime(new Timestamp(System.currentTimeMillis()));
        routeRepository.save(route);
    }
}