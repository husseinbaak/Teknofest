package com.example.tradensbackendv2.repository;

import com.example.tradensbackendv2.models.IndoorMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IndoorMappingRepository extends JpaRepository<IndoorMapping,Integer> {

    @Query(value = "select  * from indoor_mapping  order by id desc limit 1",nativeQuery = true)
    IndoorMapping getLastRecord();




}