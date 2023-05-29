package com.example.tradensbackendv2.services.mqttservices;

import com.example.tradensbackendv2.models.VehicleStatus;
import com.example.tradensbackendv2.services.VehicleStatusService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class MQTTServiceForVehicleStatus implements MqttCallback {
    private final VehicleStatusService vehicleStatusService;
    private MqttClient client = null;
    Logger LOG = LoggerFactory.getLogger(getClass());
    @Override
    public void connectionLost(Throwable cause) {
        LOG.info(" : " + cause.getMessage() + " :" + cause);
        LOG.error("Vehicle Status connection is lost cause: " + cause.getMessage() + " :" + cause);
        start();
    }
    @Override
    public void messageArrived(String topic, MqttMessage message){
        saveVehicleStatus(message.toString());
    }
    @SneakyThrows
    private void saveVehicleStatus(String toString) {
        JSONObject object=new JSONObject(toString);
        VehicleStatus vehicleStatus=new VehicleStatus();
        vehicleStatus.setVehicleIsCurrentlyDoing(object.getString("vehicleCurrentlyDoing"));
        vehicleStatus.setDataRecordingTime(new Timestamp(System.currentTimeMillis()));
        vehicleStatusService.saveVehicleData(vehicleStatus);
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            LOG.info("deliveryCompleted for vehicle status ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SneakyThrows
    @PostConstruct
    public void start() {
        try {
            String mqttIpAddress = "localhost";
            String mqttPort = "1883";
            client = new MqttClient("tcp://" + mqttIpAddress + ":" + mqttPort, MqttClient.generateClientId(),
                    new MemoryPersistence());
            // tcp://iot.eclipse.org:1883
        } catch (MqttException e1) {
            e1.printStackTrace();
        }
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setMaxInflight(3000);
        connectOptions.setAutomaticReconnect(true);
        // String mqttIpAddress = "iot.eclipse.org";
        client.setCallback(this);
        try {
            IMqttToken mqttConnectionToken = client.connectWithResult(connectOptions);

            LOG.info("Connection Status for vehicle status  :" + mqttConnectionToken.isComplete());
            client.subscribe("AGV_Status");//#
            System.err.println("Connection is successful for status topic");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}