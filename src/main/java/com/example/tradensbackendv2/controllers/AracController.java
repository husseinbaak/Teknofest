package com.example.tradensbackendv2.controllers;

import com.example.tradensbackendv2.models.*;
import com.example.tradensbackendv2.services.mqttservices.MQTTService;
import com.example.tradensbackendv2.services.QrService;
import com.example.tradensbackendv2.services.VehicleStatusService;
import com.example.tradensbackendv2.services.impl.IndoorMappingServiceImp;
import com.example.tradensbackendv2.services.impl.SensorsServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arac")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequiredArgsConstructor
public class AracController {
    private final MQTTService mqttService;
    private final QrService qrService;
    private final IndoorMappingServiceImp indoorMappingServiceImp;
    private final SensorsServiceImp sensorsServiceImp;
    private final VehicleStatusService vehicleStatusService;

    /**
     * Rota
     * */

    @PostMapping("/rota-gir")
    public ResponseEntity<?> rotaGir(@RequestBody Rota rota){
        mqttService.mesaj_gonder("AGV_ROTA_RX",rota);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/son-qr-verisi")
    public ResponseEntity<Qr> lastQrData(){
        return new ResponseEntity<>(qrService.getLastQrData(),HttpStatus.OK);
    }

    @GetMapping("/tum-qr-verileri")
    public ResponseEntity<List<Qr>> listAllQrData(){
        return new ResponseEntity<>(qrService.getAllRecordedQrData(),HttpStatus.OK);
    }

    /**
     * Indoor controller
     * */
    @GetMapping("/son-haritalama-verisi")
    public ResponseEntity<IndoorMapping> lastIndoorData(){
        return new ResponseEntity<>(indoorMappingServiceImp.getLastIndoorData(),HttpStatus.OK);
    }
    @GetMapping("/tum-haritalama-verileri")
    public ResponseEntity<List<IndoorMapping>> listAllIndoorData(){
        return new ResponseEntity<>(indoorMappingServiceImp.getAllRecordedIndoorData(),HttpStatus.OK);
    }
    /**
     * Sensors controller
     * */
    @GetMapping("/son-sensor-verisi")
    public ResponseEntity<Sensors> lastSensorsData(){
        return new ResponseEntity<>(sensorsServiceImp.getLastSensorsData(),HttpStatus.OK);
    }
    @GetMapping("/tum-sensor-verileri")
    public ResponseEntity<List<Sensors>> listAllSensorsData(){
        return new ResponseEntity<>(sensorsServiceImp.getAllRecordedSensorsData(),HttpStatus.OK);
    }
    /**
     * VehicleStatus controller
     * */
    @GetMapping("/son-arac-durumu")
    public ResponseEntity<VehicleStatus> lastVehicleStatusData(){
        return new ResponseEntity<>(vehicleStatusService.getLastVehicleData(),HttpStatus.OK  );
    }
    @GetMapping("/tum-arac-durumlari")
    public ResponseEntity<List<VehicleStatus>> listAllVehicleStatusData(){
        return new ResponseEntity<>(vehicleStatusService.getAllRecordedVehicleData(),HttpStatus.OK);
    }
}