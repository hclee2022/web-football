package com.gisoun.football.team.domain.fixtures;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusVO {

    @JsonProperty("short")
    private String shortStatus;

    public String getShortStatus() {
        return shortStatus;
    }

    public void setShortStatus(String shortStatus) {
        this.shortStatus = shortStatus;
    }

}
