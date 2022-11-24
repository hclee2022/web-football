package com.gisoun.football.team.domain.fixtures;

public class ResponseVO {

    private FixtureVO fixture;
    private LeagueVO league;
    private TeamsVO teams;
    private GoalsVO goals;

    public FixtureVO getFixture() {
        return fixture;
    }

    public void setFixture(FixtureVO fixture) {
        this.fixture = fixture;
    }

    public LeagueVO getLeague() {
        return league;
    }

    public void setLeague(LeagueVO league) {
        this.league = league;
    }

    public TeamsVO getTeams() {
        return teams;
    }

    public void setTeams(TeamsVO teams) {
        this.teams = teams;
    }

    public GoalsVO getGoals() {
        return goals;
    }

    public void setGoals(GoalsVO goals) {
        this.goals = goals;
    }

}
