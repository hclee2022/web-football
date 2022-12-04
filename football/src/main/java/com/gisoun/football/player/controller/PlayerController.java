package com.gisoun.football.player.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisoun.football.common.domain.countries.CountriesVO;
import com.gisoun.football.common.domain.leagues.CommonLeaguesVO;
import com.gisoun.football.player.domain.banner.PlayerBannerVO;
import com.gisoun.football.player.domain.banner.ResponseVO;
import com.gisoun.football.player.domain.career.PlayerCareerVO;
import com.gisoun.football.team.domain.squads.PlayersVO;
import com.gisoun.football.team.domain.squads.TeamSquadsVO;

@CrossOrigin
@RestController
@EnableAutoConfiguration
@RequestMapping("/player")
public class PlayerController {
    
    @GetMapping("/banner")
    public HashMap<String, Object> playerBanner(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int playerIdVal = Integer.parseInt(request.getParameter("id"));
        int seasonVal = Integer.parseInt(request.getParameter("season"));

        // Json File Path & Name
        String jsonPath = "/json/players/" + playerIdVal + "/";
        String jsonFile = "Player" + "_" + playerIdVal + "_" + seasonVal + ".json";

        // Read JSON File
        Resource cpr = new ClassPathResource(jsonPath + jsonFile);
        File file = cpr.getFile();

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        ResponseVO playerResponseVO = om.readValue(file, PlayerBannerVO.class).getResponse().get(0);

        // Define HashMap
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("nationality", playerResponseVO.getPlayer().getNationality());
        map.put("teamId", playerResponseVO.getStatistics().get(0).getTeam().getId());
        map.put("teamLogo", playerResponseVO.getStatistics().get(0).getTeam().getLogo());
        map.put("teamName", playerResponseVO.getStatistics().get(0).getTeam().getName());
        map.put("leagueId", playerResponseVO.getStatistics().get(0).getLeague().getId());
        map.put("leagueCountry", playerResponseVO.getStatistics().get(0).getLeague().getCountry());
        map.put("playerName", playerResponseVO.getPlayer().getName());
        map.put("age", playerResponseVO.getPlayer().getAge());
        map.put("birthDate", playerResponseVO.getPlayer().getBirth().getDate());
        map.put("height", playerResponseVO.getPlayer().getHeight());
        map.put("weight", playerResponseVO.getPlayer().getWeight());
        map.put("photo", playerResponseVO.getPlayer().getPhoto());

        // Squads
        jsonPath = "/json/teams/squads/";
        jsonFile = "Squads" + "_" + playerResponseVO.getStatistics().get(0).getTeam().getId() + ".json";

        cpr = new ClassPathResource(jsonPath + jsonFile);
        file = cpr.getFile();

        List<PlayersVO> teamSquadsPlayersVO = om.readValue(file, TeamSquadsVO.class).getResponse().get(0).getPlayers();

        for (int i = 0; i < teamSquadsPlayersVO.size(); i++) {
            if (teamSquadsPlayersVO.get(i).getId() == playerResponseVO.getPlayer().getId()) {
                map.put("number", teamSquadsPlayersVO.get(i).getNumber());

                switch (teamSquadsPlayersVO.get(i).getPosition()) {
                    case "Goalkeeper":
                        map.put("position", "GK");
                        break;
                    case "Defender":
                        map.put("position", "DF");
                        break;
                    case "Midfielder":
                        map.put("position", "MF");
                        break;
                    case "Attacker":
                        map.put("position", "AT");
                        break;
                }

                break;
            }

            if (i >= teamSquadsPlayersVO.size() - 1) {
                map.put("number", null);
                map.put("position", null);

                break;
            }
        }

        // Country
        jsonPath = "/json/common/";
        jsonFile = "Countries" + ".json";

        cpr = new ClassPathResource(jsonPath + jsonFile);
        file = cpr.getFile();

        List<com.gisoun.football.common.domain.countries.ResponseVO> countriesResponseVO = om
                .readValue(file, CountriesVO.class).getResponse();

        for (int i = 0; i < countriesResponseVO.size(); i++) {
            if (countriesResponseVO.get(i).getName().equals(playerResponseVO.getPlayer().getNationality())) {
                map.put("flag", countriesResponseVO.get(i).getFlag());

                break;
            }

            if (i >= countriesResponseVO.size() - 1) {
                map.put("flag", null);

                break;
            }
        }

        return map;
    }

    @GetMapping("/statistics/career")
    public HashMap<String, Object> playerStatisticsHeaders(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int playerIdVal = Integer.parseInt(request.getParameter("id"));
        int seasonVal = Integer.parseInt(request.getParameter("season"));
        
        // Json File Path & Name
        String jsonPath = "/json/players/" + playerIdVal + "/";
        String jsonFile;
        
        // Read JSON File
        Resource cpr;
        File file;
        
        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<com.gisoun.football.player.domain.career.StatisticsVO> playerStatisticsHeadersResponseVO;

        String leagueJsonPath = "/json/common/";
        String leagueJsonFile = "Leagues" + ".json";

        Resource leagueCpr = new ClassPathResource(leagueJsonPath + leagueJsonFile);
        File leagueFile = leagueCpr.getFile();

        List<com.gisoun.football.common.domain.leagues.ResponseVO> commonLeaguesResponseVO = 
            om.readValue(leagueFile, CommonLeaguesVO.class).getResponse();

        HashMap<String, Object> map = new HashMap<String, Object>();

        int hashMapIdx = 0;
        while (true) {
            jsonFile = "Player" + "_" + playerIdVal + "_" + seasonVal + ".json";
            cpr = new ClassPathResource(jsonPath + jsonFile);

            if (cpr.exists()) {
                file = cpr.getFile();

                playerStatisticsHeadersResponseVO = 
                    om.readValue(file, PlayerCareerVO.class).getResponse().get(0).getStatistics();
                    
                for (int i = 0; i < playerStatisticsHeadersResponseVO.size(); i++) {
                    if (playerStatisticsHeadersResponseVO.get(i).getLeague().getId() == null) {
                        continue;
                    }

                    for (int j = 0; j < commonLeaguesResponseVO.size(); j++) {
                        if (commonLeaguesResponseVO.get(j).getLeague().getId() == playerStatisticsHeadersResponseVO
                                .get(i).getLeague().getId()) {

                            playerStatisticsHeadersResponseVO.get(i).getLeague().setCustomSeason(seasonVal);
                            map.put(Integer.toString(hashMapIdx), playerStatisticsHeadersResponseVO.get(i));
                            hashMapIdx++;
                        }
                    }
                }
                seasonVal--;
            } else {
                break;
            }
        }

        return map;
    }

}
