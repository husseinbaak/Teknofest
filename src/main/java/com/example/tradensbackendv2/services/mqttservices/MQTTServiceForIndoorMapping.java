package com.example.tradensbackendv2.services.mqttservices;

import com.example.tradensbackendv2.models.IndoorMapping;
import com.example.tradensbackendv2.services.IndoorMappingService;
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
public class MQTTServiceForIndoorMapping implements MqttCallback {
    private final IndoorMappingService indoorMappingService;
    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public void connectionLost(Throwable cause) {
        LOG.info(" : " + cause.getMessage() + " :" + cause);
        LOG.error("Indoor connection is lost cause: " + cause.getMessage() + " :" + cause);
        start();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        saveIndoorMappingData(message.toString());

    }

    @SneakyThrows
    private void saveIndoorMappingData(String toString) {
        JSONObject object = new JSONObject(toString);
        IndoorMapping indoorMapping = new IndoorMapping();
        indoorMapping.setRssi1((float) object.getDouble("rssi1"));
        indoorMapping.setRssi2((float) object.getDouble("rssi2"));
        indoorMapping.setRssi3((float) object.getDouble("rssi3"));
        indoorMapping.setRssi4((float) object.getDouble("rssi4"));
        indoorMapping.setCalculatedXCoordinate(object.getInt("calculatedXCoordinate"));
        indoorMapping.setCalculatedYCoordinate(object.getInt("calculatedYCoordinate"));
        indoorMapping.setDataRecordingTime(new Timestamp(System.currentTimeMillis()));
        indoorMappingService.saveIndoorData(indoorMapping);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            LOG.info("deliveryCompleted for indoor ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @PostConstruct
    public void start() {
        MqttClient client = new MqttClient("tcp://" + "localhost" + ":" + "1883", MqttClient.generateClientId(),
                new MemoryPersistence());
        // tcp://iot.eclipse.org:1883
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setMaxInflight(3000);
        connectOptions.setAutomaticReconnect(true);
        // String mqttIpAddress = "iot.eclipse.org";
        client.setCallback(this);
        try {
            IMqttToken mqttConnectionToken = client.connectWithResult(connectOptions);

            LOG.info("Connection Status for indoor  :" + mqttConnectionToken.isComplete());
            client.subscribe("AGV_Indoor");//#
            System.err.println("Connection is successful for indoor topic");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}