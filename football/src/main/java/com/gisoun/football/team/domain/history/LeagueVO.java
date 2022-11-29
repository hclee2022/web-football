package com.gisoun.football.team.domain.history;

import java.util.List;

public class LeagueVO {

    private int id;
    private String name;
    private String country;
    private int season;
    private List<StandingsVO>[] standings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public List<StandingsVO>[] getStandings() {
        return standings;
    }

    public void setStandings(List<StandingsVO>[] standings) {
        this.standings = standings;
    }

}
