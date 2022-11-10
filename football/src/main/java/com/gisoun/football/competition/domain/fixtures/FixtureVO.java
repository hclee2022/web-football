package com.gisoun.football.competition.domain.fixtures;

public class FixtureVO {

    private Long timestamp;
    private StatusVO status;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public StatusVO getStatus() {
        return status;
    }

    public void setStatus(StatusVO status) {
        this.status = status;
    }

}
