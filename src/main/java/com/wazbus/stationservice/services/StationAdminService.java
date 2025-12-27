package com.wazbus.stationservice.services;

import com.wazbus.stationservice.dto.StationCreateRequest;
import com.wazbus.stationservice.dto.StationCreatedResponse;
import com.wazbus.stationservice.entities.Station;
import com.wazbus.stationservice.enums.EntityCodePrefix;
import com.wazbus.stationservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StationAdminService {

    private final StationRepository stationRepository;

    @Autowired
    public StationAdminService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Mono<StationCreatedResponse> createStation(StationCreateRequest stationCreateRequest) {
        String stationName = stationCreateRequest.getName();

        // String stationCode = codeGenerator.generate(EntityCodePrefix.STATION, stationName);

        Station station = new Station();
        station.setName(stationName);
        // station.setCode(stationCode);
        station.setCity(stationCreateRequest.getCity());
        station.setCountry(stationCreateRequest.getCountry());

        return stationRepository.save(station)
                .map(savedStation -> new StationCreatedResponse(savedStation.getCode(), savedStation.getName()));
    }
}
