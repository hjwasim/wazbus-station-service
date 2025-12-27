package com.wazbus.stationservice.enums;

import lombok.Getter;

@Getter
public enum EntityCodePrefix {

    STATION("STF"),
    STATION_POINT("STP");

    private final String prefix;

    EntityCodePrefix(String prefix) {
        this.prefix = prefix;
    }

}
