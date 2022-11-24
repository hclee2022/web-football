package com.gisoun.football.common.domain.leagues;

import java.util.List;

public class ResponseVO {

    private LeagueVO league;
    private CountryVO country;
    private List<SeasonsVO> seasons;

    public LeagueVO getLeague() {
        return league;
    }

    public void setLeague(LeagueVO league) {
        this.league = league;
    }

    public CountryVO getCountry() {
        return country;
    }

    public void setCountry(CountryVO country) {
        this.country = country;
    }

    public List<SeasonsVO> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SeasonsVO> seasons) {
        this.seasons = seasons;
    }

}
