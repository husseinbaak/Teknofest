package com.example.tradensbackendv2.services.impl;

import com.example.tradensbackendv2.models.Qr;
import com.example.tradensbackendv2.repository.QrRepository;
import com.example.tradensbackendv2.services.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QrServiceImpl implements QrService {
    private final QrRepository qrRepository;

    @Override
    public void saveQrData(Qr qr){
        qrRepository.save(qr);
    }
    @Override
    public Qr getLastQrData(){
        return qrRepository.getLastRecordedData();
    }
    @Override
    public List<Qr> getAllRecordedQrData(){
        return qrRepository.findAll();
    }
}