package com.gisoun.football.player.domain.career;

import java.util.List;

public class ResponseVO {

    private List<StatisticsVO> statistics;

    public List<StatisticsVO> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<StatisticsVO> statistics) {
        this.statistics = statistics;
    }

}
