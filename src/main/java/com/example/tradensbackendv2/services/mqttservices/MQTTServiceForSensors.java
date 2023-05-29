package com.example.tradensbackendv2.services.mqttservices;

import com.example.tradensbackendv2.models.Sensors;
import com.example.tradensbackendv2.services.SensorsService;
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

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class MQTTServiceForSensors implements MqttCallback {
    Logger LOG = LoggerFactory.getLogger(getClass());
    private final SensorsService sensorsService;
    @Override
    @SneakyThrows
    public void connectionLost(Throwable cause) {
        LOG.info(" : " + cause.getMessage() + " :" + cause);
        LOG.error("Sensors connection is lost cause: "+cause.getMessage()+ " :"+cause);
       start();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message)  {
        saveSensorsData(message.toString());
    }

    @SneakyThrows
    private void saveSensorsData(String toString) {
        Sensors sensors=new Sensors();
        JSONObject object=new JSONObject(toString);
        JSONArray array=object.getJSONArray("Distances");
        sensors.setBatteryLevel(object.getInt("batteryLevel"));//int
        sensors.setLoadCell((float) object.getDouble("loadCell"));
        sensors.setLeftMotorSpeed(object.getInt("leftMotorRpm"));
        sensors.setRightMotorSpeed(object.getInt("rightMotorRpm"));
        sensors.setVehicleSpeed(object.getInt("vehicleSpeed"));//float?
        sensors.setBatteryCurrentLevel((float) object.getDouble("batteryCurrent"));
        sensors.setLeftMotorCurrentLevel((float) object.getDouble("leftMotorCurrent"));
        sensors.setRightMotorCurrentLevel((float) object.getDouble("rightMotorCurrent"));
        sensors.setForwardSensorDistance((float) array.getDouble(0));
        sensors.setLeftFrontCrossSensorDistance((float) array.getDouble(1));
        sensors.setLeftSensorDistance((float) array.getDouble(2));
        sensors.setLeftRearCrossSensorDistance((float) array.getDouble(3));
        sensors.setEcuTemperature((float) object.getDouble("ecuTemperature"));
        sensors.setFanState(object.getBoolean("fanState"));
        sensors.setCarLights(object.getBoolean("carLights"));
        sensors.setDataRecordingTime(new Timestamp(System.currentTimeMillis()));
        sensorsService.saveSensorsData(sensors);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            LOG.info("deliveryCompleted for sensors ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    @PostConstruct
    public void start() {
            String mqttIpAddress = "localhost";
            String mqttPort = "1883";
        MqttClient client = new MqttClient("tcp://" + mqttIpAddress + ":" + mqttPort, MqttClient.generateClientId(),
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

            LOG.info("Connection Status for Sensors  :" + mqttConnectionToken.isComplete());
            client.subscribe("Deneme_Json");//#
            System.err.println("Connection is successful for sensors topic");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}