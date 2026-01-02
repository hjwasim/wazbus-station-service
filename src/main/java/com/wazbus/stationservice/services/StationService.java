package com.wazbus.stationservice.services;

import com.wazbus.stationservice.dto.StationResponse;
import com.wazbus.stationservice.entities.Station;
import com.wazbus.stationservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StationService {

    private final StationRepository stationRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public StationService(StationRepository stationRepository,
                          ReactiveMongoTemplate reactiveMongoTemplate) {
        this.stationRepository = stationRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Flux<StationResponse> fetchAllStations() {
        return stationRepository.findAll().map(station -> {
            return new StationResponse(station.getCode(),
                    station.getName(),
                    station.getCity(),
                    station.getCountry()
            );
        });
    }

    public Mono<StationResponse> fetchStationByCode(String stationCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(stationCode));

        return reactiveMongoTemplate.findOne(query, Station.class)
                .map(station ->
                        new StationResponse(station.getCode(),
                                station.getName(),
                                station.getCity(),
                                station.getCountry()
                        )
                )
                .switchIfEmpty(Mono.error(new RuntimeException("Station not found")));

    }
}
