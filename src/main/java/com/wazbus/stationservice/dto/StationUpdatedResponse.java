package com.wazbus.stationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationUpdatedResponse extends StationResponse {
    private boolean isSuccess;

    public StationUpdatedResponse(String code, String name, String city, String country) {
        super(code, name, city, country);
    }
}
