package com.wazbus.stationservice.services;

import com.wazbus.stationservice.dto.StationCreateRequest;
import com.wazbus.stationservice.dto.StationCreatedResponse;
import com.wazbus.stationservice.entities.Station;
import com.wazbus.stationservice.enums.EntityCodePrefix;
import com.wazbus.stationservice.repository.StationRepository;
import com.wazbus.stationservice.utils.CodeSeqGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StationAdminService {

    private final StationRepository stationRepository;
    private final CodeSeqGenerator codeSeqGenerator;

    @Autowired
    public StationAdminService(StationRepository stationRepository, CodeSeqGenerator codeSeqGenerator) {
        this.stationRepository = stationRepository;
        this.codeSeqGenerator = codeSeqGenerator;
    }

    public Mono<StationCreatedResponse> createStation(StationCreateRequest stationCreateRequest) {
        String stationName = stationCreateRequest.getName();
        String stationCode = codeSeqGenerator.generate(EntityCodePrefix.STATION, stationName);

        Station station = new Station();
        station.setName(stationName);
        station.setCode(stationCode);
        station.setCity(stationCreateRequest.getCity());
        station.setCountry(stationCreateRequest.getCountry());

        return stationRepository.save(station)
                .map(savedStation -> new StationCreatedResponse(savedStation.getCode(), savedStation.getName()));
    }
}
