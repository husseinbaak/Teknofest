package com.example.tradensbackendv2.services.mqttservices;

import com.example.tradensbackendv2.models.Qr;
import com.example.tradensbackendv2.services.QrService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class MQTTServiceForQrCode implements MqttCallback {
    private final QrService qrService;
    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public void connectionLost(Throwable cause) {
        LOG.info(" : " + cause.getMessage() + " :" + cause);
        LOG.error("Qr code connection is lost cause: " + cause.getMessage() + " :" + cause);
        start();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        saveQrCode(message.toString());
    }

    private void saveQrCode(String toString) {
        String[] slicedData = toString.split(";");
        Qr qr = new Qr();
        qr.setQrName(slicedData[0]);
        qr.setQrXCoordinate(slicedData[1]);
        qr.setQrYCoordinate(slicedData[2]);
        qr.setDataRecordingTime(new Timestamp(System.currentTimeMillis()));
        qrService.saveQrData(qr);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            LOG.info("deliveryCompleted for qr code ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @PostConstruct
    public void start() {
        String mqttPort = "1883";
        MqttClient client = new MqttClient("tcp://" + "localhost" + ":" + mqttPort, MqttClient.generateClientId(),
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

            LOG.info("Connection Status for qr code  :" + mqttConnectionToken.isComplete());
            client.subscribe("AGV_Qr");//#
            System.err.println("Connection is successful for qr topic");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}