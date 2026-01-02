package com.wazbus.stationservice.services;

import com.wazbus.stationservice.dto.StationCreateRequest;
import com.wazbus.stationservice.dto.StationCreatedResponse;
import com.wazbus.stationservice.dto.StationUpdateRequest;
import com.wazbus.stationservice.dto.StationUpdatedResponse;
import com.wazbus.stationservice.entities.Station;
import com.wazbus.stationservice.enums.EntityCodePrefix;
import com.wazbus.stationservice.repository.StationRepository;
import com.wazbus.stationservice.utils.CodeSeqGenerator;
import com.wazbus.stationservice.utils.StationQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StationAdminService {

    private static final Logger logger = LoggerFactory.getLogger(StationAdminService.class);

    private final StationQueryHelper stationQueryHelper;
    private final StationRepository stationRepository;
    private final CodeSeqGenerator codeSeqGenerator;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public StationAdminService(StationQueryHelper stationQueryHelper, StationRepository stationRepository,
                               CodeSeqGenerator codeSeqGenerator,
                               ReactiveMongoTemplate reactiveMongoTemplate) {
        this.stationQueryHelper = stationQueryHelper;
        this.stationRepository = stationRepository;
        this.codeSeqGenerator = codeSeqGenerator;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<StationCreatedResponse> createStation(StationCreateRequest stationCreateRequest) {
        String stationName = stationCreateRequest.getName();
        String city = stationCreateRequest.getCity();
        String country = stationCreateRequest.getCountry();

        Query query = stationQueryHelper.isStationExistByNameAndCityQuery(stationName, city);

        return reactiveMongoTemplate.exists(query, Station.class)
                .flatMap(found -> {
                    if (found) {
                        return Mono.error(new RuntimeException("Station already exists"));
                    }

                    String stationCode =
                            codeSeqGenerator.generate(EntityCodePrefix.STATION, stationName);

                    Station station = new Station();
                    station.setName(stationName);
                    station.setCity(city);
                    station.setCountry(country);
                    station.setCode(stationCode);

                    return stationRepository.save(station)
                            .map(saved -> {
                                        StationCreatedResponse response = new StationCreatedResponse(
                                                saved.getCode(),
                                                saved.getName(),
                                                saved.getCity(),
                                                saved.getCountry());
                                        response.setSuccess(true);
                                        return response;
                                    }
                            );
                });
    }

    public Mono<Boolean> deleteStation(String stationCode) {
        Query query = stationQueryHelper.isStationExistByCodeQuery(stationCode);
        return reactiveMongoTemplate
                .remove(query, Station.class)
                .flatMap(result -> {
                    if (result.getDeletedCount() == 1) {
                        return Mono.just(true);
                    }
                    return Mono.just(false);
                });
    }

    public Mono<StationUpdatedResponse> updateStation(StationUpdateRequest stationUpdateRequest) {
        String stationCode = stationUpdateRequest.getCode();
        Query query = stationQueryHelper.isStationExistByCodeQuery(stationCode);
        return reactiveMongoTemplate.findOne(query, Station.class)
                .flatMap(station -> {
                    if (station == null) {
                        return Mono.error(new RuntimeException("Station does not exist"));
                    }
                    String stationName = stationUpdateRequest.getName();
                    String city = stationUpdateRequest.getCity();
                    String country = stationUpdateRequest.getCountry();

                    Query existQuery = stationQueryHelper.isStationExistByNameAndCityQuery(stationName, city);
                    return reactiveMongoTemplate.exists(existQuery, Station.class)
                            .flatMap(isFound -> {
                                if (isFound) {
                                    return Mono.error(new RuntimeException("Station already exists"));
                                }
                                station.setName(stationName);
                                station.setCity(city);
                                station.setCountry(country);
                                return stationRepository.save(station)
                                        .map(saved -> {
                                                    StationUpdatedResponse response = new StationUpdatedResponse(
                                                            saved.getCode(),
                                                            saved.getName(),
                                                            saved.getCity(),
                                                            saved.getCountry());
                                                    response.setSuccess(true);
                                                    return response;
                                                }
                                        );
                            });
                });
    }
}
