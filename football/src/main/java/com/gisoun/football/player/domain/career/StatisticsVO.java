package com.gisoun.football.player.domain.career;

public class StatisticsVO {

    private TeamVO team;
    private LeagueVO league;
    private GamesVO games;
    private GoalsVO goals;
    private CardsVO cards;

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

    public GamesVO getGames() {
        return games;
    }

    public void setGames(GamesVO games) {
        this.games = games;
    }

    public GoalsVO getGoals() {
        return goals;
    }

    public void setGoals(GoalsVO goals) {
        this.goals = goals;
    }

    public CardsVO getCards() {
        return cards;
    }

    public void setCards(CardsVO cards) {
        this.cards = cards;
    }

}
