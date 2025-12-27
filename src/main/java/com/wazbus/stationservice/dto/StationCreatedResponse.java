package com.wazbus.stationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StationCreatedResponse {
    private String code;
    private String name;
}
