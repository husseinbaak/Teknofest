package com.example.tradensbackendv2.repository;

import com.example.tradensbackendv2.models.Qr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QrRepository extends JpaRepository<Qr,Integer> {
    @Query(value= "select * from qr order by id desc limit 1",nativeQuery = true)
    Qr getLastRecordedData();
}