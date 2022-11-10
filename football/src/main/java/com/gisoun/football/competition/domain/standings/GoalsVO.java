package com.gisoun.football.competition.domain.standings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoalsVO {

    @JsonProperty("for")
    private int scored;
    private int against;

    public int getScored() {
        return scored;
    }

    public void setScored(int scored) {
        this.scored = scored;
    }

    public int getAgainst() {
        return against;
    }

    public void setAgainst(int against) {
        this.against = against;
    }

}
