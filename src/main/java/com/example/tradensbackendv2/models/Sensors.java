package com.example.tradensbackendv2.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "sensors")
public class Sensors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "battery_level")
    private int batteryLevel;

    @Column(name = "load_cell")
    private float loadCell;

    @Column(name = "left_motor_speed")
    private int leftMotorSpeed;

    @Column(name = "right_motor_speed")
    private int rightMotorSpeed;

    @Column(name = "vehicle_speed")
    private float vehicleSpeed;

    @Column(name = "battery_current_level")
    private float batteryCurrentLevel;

    @Column(name = "left_motor_current_level")
    private float leftMotorCurrentLevel;

    @Column(name = "right_motor_current_level")
    private float rightMotorCurrentLevel;

    @Column(name = "forward_sensor_distance")
    private float forwardSensorDistance;

    @Column(name = "left_front_cross_sensor_distance")
    private float leftFrontCrossSensorDistance;

    @Column(name = "left_sensor_distance")
    private float leftSensorDistance;

    @Column(name = "left_rear_cross_sensor_distance")
    private float leftRearCrossSensorDistance;

    @Column(name = "ecu_temperature")
    private float ecuTemperature;

    @Column(name = "fan_state")
    private Boolean fanState;

    @Column(name = "car_lights")
    private Boolean carLights;

    @Column(name = "data_recording_time")
    private Timestamp dataRecordingTime;


}

