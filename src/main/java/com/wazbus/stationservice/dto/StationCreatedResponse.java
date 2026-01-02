package com.wazbus.stationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationCreatedResponse extends StationResponse {

    private boolean isSuccess;

    public StationCreatedResponse(String code, String name, String city, String country) {
        super(code, name, city, country);
    }
}
