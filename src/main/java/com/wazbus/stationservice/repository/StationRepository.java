package com.wazbus.stationservice.repository;

import com.wazbus.stationservice.entities.Station;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends ReactiveCrudRepository<Station, String> {
}
