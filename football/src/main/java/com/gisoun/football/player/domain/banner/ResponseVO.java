package com.gisoun.football.player.domain.banner;

import java.util.List;

public class ResponseVO {

    private PlayerVO player;
    private List<StatisticsVO> statistics;

    public PlayerVO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerVO player) {
        this.player = player;
    }

    public List<StatisticsVO> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<StatisticsVO> statistics) {
        this.statistics = statistics;
    }

}
