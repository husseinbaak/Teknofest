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
@Table(name = "indoor_mapping")
public class IndoorMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "rssi1")
    private float rssi1;
    @Column(name = "rssi2")
    private float rssi2;
    @Column(name = "rssi3")
    private  float rssi3;
    @Column(name = "rssi4")
    private float rssi4;
    @Column(name = "calculated_x_coordinate")
    private int calculatedXCoordinate;
    @Column(name = "calculated_y_coordinate")
    private int calculatedYCoordinate;
    @Column(name = "data_recording_time")
    private Timestamp dataRecordingTime;
}