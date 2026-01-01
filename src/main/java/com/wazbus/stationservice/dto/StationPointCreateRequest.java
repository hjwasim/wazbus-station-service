package com.wazbus.stationservice.dto;

import com.wazbus.stationservice.enums.PointType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
public class StationPointCreateRequest {

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @NotBlank
    @Size(min = 1, max = 20)
    private String stationCode;

    @NotBlank
    @Size(min = 1, max = 20)
    private String tripCode;

    @NotBlank
    @Size(min = 1, max = 20)
    private String landmark;

    private PointType type;
    private String address;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private Set<Long> mobile;
}
