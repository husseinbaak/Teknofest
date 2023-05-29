package com.example.tradensbackendv2.services.mqttservices;

import com.example.tradensbackendv2.models.Rota;
import com.example.tradensbackendv2.models.Route;
import com.example.tradensbackendv2.services.RouteService;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MQTTService implements MqttCallback {
    private final RouteService routeService;
    private MqttClient client = null;
    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    @SneakyThrows
    public void connectionLost(Throwable throwable) {
        LOG.info("Bağlantı 1 Koptu : " + throwable.getMessage() + " :" + throwable);
        start();
    }

    @Override
    @SneakyThrows
    public void messageArrived(String s, MqttMessage mqttMessage) {
        System.out.println("Gelen rota: "+ mqttMessage.toString() );

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        try {
            LOG.info("deliveryCompleted ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SneakyThrows
    public void mesaj_gonder(String topicSuffix, Rota rota) {
        Route route=new Route();
        route.setRoute(route.getRoute());
        routeService.saveRoute(route);
        MqttMessage message = new MqttMessage();
            message.setPayload(rota.getMessage().getBytes());
        message.setQos(1);
        if (client.isConnected()) {
            LOG.info("Connection Status :" + client.isConnected());
        }
        client.publish(topicSuffix, message);

    }

    @SneakyThrows
    @PostConstruct
    public void start() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            String mqttIpAddress = "localhost";
            String mqttPort = "1883";
            client = new MqttClient("tcp://" + mqttIpAddress + ":" + mqttPort, MqttClient.generateClientId(),
                    persistence);
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

            LOG.info(" Connection Status :" + mqttConnectionToken.isComplete());
            client.subscribe("AGV_ROTA_RX");//#
            System.err.println("Subscribed to topic");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}