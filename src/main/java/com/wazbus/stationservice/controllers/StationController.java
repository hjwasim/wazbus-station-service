package com.wazbus.stationservice.controllers;

import com.wazbus.stationservice.dto.StationResponse;
import com.wazbus.stationservice.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/stations")
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public ResponseEntity<Flux<StationResponse>> getStations() {
        return ResponseEntity.ok(stationService.fetchAllStations());
    }

    @GetMapping("/{stationCode}")
    public ResponseEntity<Mono<StationResponse>> getStationByStationCode(
            @PathVariable("stationCode") String stationCode) {
        return ResponseEntity.ok(stationService.fetchStationByCode(stationCode));
    }
}
