package com.wazbus.stationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StationPointCreatedResponse {
    private String code;
    private String name;
}
