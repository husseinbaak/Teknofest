package com.example.tradensbackendv2.services;

import com.example.tradensbackendv2.models.Qr;

import java.util.List;

public interface QrService {
    void saveQrData(Qr qr);

    Qr getLastQrData();

    List<Qr> getAllRecordedQrData();
}
