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
@Table(name = "qr")
public class Qr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "qr_name")
    private String qrName;
    @Column(name = "qr_x_coordinate")
    private String qrXCoordinate;
    @Column(name = "qr_y_coordinate")
    private String qrYCoordinate;
    @Column(name = "data_recording_time")
    private Timestamp dataRecordingTime;
}