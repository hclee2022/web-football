package com.gisoun.football.competition.domain.standings;

public class StandingsVO {

    private int rank;
    private TeamVO team;
    private int points;
    private int goalsDiff;
    private String form;
    private AllVO all;
    private HomeVO home;
    private AwayVO away;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public TeamVO getTeam() {
        return team;
    }

    public void setTeam(TeamVO team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsDiff() {
        return goalsDiff;
    }

    public void setGoalsDiff(int goalsDiff) {
        this.goalsDiff = goalsDiff;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public AllVO getAll() {
        return all;
    }

    public void setAll(AllVO all) {
        this.all = all;
    }

    public HomeVO getHome() {
        return home;
    }

    public void setHome(HomeVO home) {
        this.home = home;
    }

    public AwayVO getAway() {
        return away;
    }

    public void setAway(AwayVO away) {
        this.away = away;
    }

}
