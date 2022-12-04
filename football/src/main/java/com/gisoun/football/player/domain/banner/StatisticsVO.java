package com.gisoun.football.player.domain.banner;

public class StatisticsVO {

    private TeamVO team;
    private LeagueVO league;

    public TeamVO getTeam() {
        return team;
    }

    public void setTeam(TeamVO team) {
        this.team = team;
    }

    public LeagueVO getLeague() {
        return league;
    }

    public void setLeague(LeagueVO league) {
        this.league = league;
    }

}
