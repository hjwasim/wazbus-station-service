package com.wazbus.stationservice.controllers.admin;

import com.wazbus.stationservice.dto.StationCreateRequest;
import com.wazbus.stationservice.dto.StationCreatedResponse;
import com.wazbus.stationservice.services.StationAdminService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/admin/stations")
public class StationAdminController {

    private static final Logger logger = LoggerFactory.getLogger(StationAdminController.class);

    private final StationAdminService stationAdminService;

    @Autowired
    public StationAdminController(StationAdminService stationAdminService) {
        this.stationAdminService = stationAdminService;
    }

    @PostMapping
    public ResponseEntity<Mono<StationCreatedResponse>> createStation(
            @Valid @RequestBody StationCreateRequest stationCreateRequest) {
        Mono<StationCreatedResponse> response = stationAdminService.createStation(stationCreateRequest);
        return ResponseEntity.ok(response);
    }

}
