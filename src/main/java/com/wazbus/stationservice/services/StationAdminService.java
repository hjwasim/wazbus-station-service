package com.wazbus.stationservice.services;

import com.wazbus.stationservice.dto.StationCreateRequest;
import com.wazbus.stationservice.dto.StationCreatedResponse;
import com.wazbus.stationservice.entities.Station;
import com.wazbus.stationservice.enums.EntityCodePrefix;
import com.wazbus.stationservice.repository.StationRepository;
import com.wazbus.stationservice.utils.CodeSeqGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StationAdminService {

    private final StationRepository stationRepository;
    private final CodeSeqGenerator codeSeqGenerator;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public StationAdminService(StationRepository stationRepository,
                               CodeSeqGenerator codeSeqGenerator,
                               ReactiveMongoTemplate reactiveMongoTemplate) {
        this.stationRepository = stationRepository;
        this.codeSeqGenerator = codeSeqGenerator;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<StationCreatedResponse> createStation(StationCreateRequest stationCreateRequest) {
        String stationName = stationCreateRequest.getName();
        String city = stationCreateRequest.getCity();
        String country = stationCreateRequest.getCountry();

        Criteria nameCriteria = Criteria.where("name").is(stationName);
        Criteria cityCriteria = Criteria.where("city").is(city);
        Criteria combinedCriteria = new Criteria().andOperator(nameCriteria, cityCriteria);

        Query query = new Query().addCriteria(combinedCriteria);

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
                            .map(saved ->
                                    new StationCreatedResponse(
                                            saved.getCode(),
                                            saved.getName()
                                    )
                            );
                });
    }
}
