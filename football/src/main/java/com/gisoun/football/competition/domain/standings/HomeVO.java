package com.gisoun.football.competition.domain.standings;

public class HomeVO {

    private int played;
    private int win;
    private int draw;
    private int lose;
    private GoalsVO goals;

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public GoalsVO getGoals() {
        return goals;
    }

    public void setGoals(GoalsVO goals) {
        this.goals = goals;
    }

}