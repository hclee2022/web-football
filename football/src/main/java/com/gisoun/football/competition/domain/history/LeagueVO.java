package com.gisoun.football.competition.domain.history;

import java.util.List;

public class LeagueVO {

    private int sesson;
    private List<StandingsVO>[] standings;

    public int getSesson() {
        return sesson;
    }

    public void setSesson(int sesson) {
        this.sesson = sesson;
    }

    public List<StandingsVO>[] getStandings() {
        return standings;
    }

    public void setStandings(List<StandingsVO>[] standings) {
        this.standings = standings;
    }

}
