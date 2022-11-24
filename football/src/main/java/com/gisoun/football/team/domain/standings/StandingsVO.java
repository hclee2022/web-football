package com.gisoun.football.team.domain.standings;

public class StandingsVO {

    private int rank;
    private TeamVO team;
    private int points;
    private AllVO all;

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

    public AllVO getAll() {
        return all;
    }

    public void setAll(AllVO all) {
        this.all = all;
    }

}
