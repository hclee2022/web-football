package com.gisoun.football.team.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisoun.football.team.domain.history.TeamHistoryVO;
import com.gisoun.football.team.domain.squads.TeamSquadsVO;
import com.gisoun.football.team.domain.squads.PlayersVO;
import com.gisoun.football.common.domain.leagues.CommonLeaguesVO;
import com.gisoun.football.team.domain.fixtures.ResponseVO;
import com.gisoun.football.team.domain.fixtures.TeamFixturesVO;
import com.gisoun.football.team.domain.standings.LeagueVO;
import com.gisoun.football.team.domain.standings.TeamStandingVO;

@CrossOrigin
@RestController
@EnableAutoConfiguration
@RequestMapping("/teams")
public class TeamController {

    @GetMapping(value = "/standings")
    public HashMap<String, Object> competitionStandings(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // Get Params From Front-End
        int leagueIdVal = Integer.parseInt(request.getParameter("id"));
        int sessonVal = Integer.parseInt(request.getParameter("sesson"));

        // Json File Path & Name
        String jsonPath = "/json/competitions/standings/";
        String jsonFile;

        // // Read JSON File
        Resource cpr;
        File file;

        while (true) {
            jsonFile = "Standings" + "_" + leagueIdVal + "_" + sessonVal + ".json";
            // Read JSON File
            cpr = new ClassPathResource(jsonPath + jsonFile);

            if (cpr.exists() || sessonVal <= 2009) {
                file = cpr.getFile();
                break;
            } else {
                sessonVal--;
            }
        }

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        TeamStandingVO teamStandingVO = om.readValue(file, TeamStandingVO.class);

        // Response[0].League
        LeagueVO leagueVO = teamStandingVO.getResponse().get(0).getLeague();

        // Define HashMap
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", leagueVO.getId());
        map.put("sesson", leagueVO.getSeason());
        map.put("name", leagueVO.getName());
        map.put("country", leagueVO.getCountry());
        map.put("logo", leagueVO.getLogo());
        map.put("flag", leagueVO.getFlag());
        map.put("size", leagueVO.getStandings()[0].size());
        map.put("standings", leagueVO.getStandings()[0]);

        return map;
    }

    @GetMapping(value = "/fixtures")
    public List<ResponseVO> competitionFixtures(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // Get Params From Front-End
        int leagueIdVal = Integer.parseInt(request.getParameter("id"));
        int sessonVal = Integer.parseInt(request.getParameter("sesson"));

        // Json File Path & Name
        String jsonPath = "/json/competitions/fixtures/";
        String jsonFile;

        // Read JSON File
        Resource cpr;
        File file;

        while (true) {
            jsonFile = "Fixtures" + "_" + leagueIdVal + "_" + sessonVal + ".json";
            // Read JSON File
            cpr = new ClassPathResource(jsonPath + jsonFile);

            if (cpr.exists() || sessonVal <= 2009) {
                file = cpr.getFile();
                break;
            } else {
                sessonVal--;
            }
        }

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        TeamFixturesVO teamFixturesVO = om.readValue(file, TeamFixturesVO.class);

        List<ResponseVO> result = teamFixturesVO.getResponse();

        return result;
    }

    @GetMapping(value = "/history")
    public HashMap<String, Object> competitionHistory(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Get Params From Front-End
        int sessonVal = Integer.parseInt(request.getParameter("sesson"));
        String countryVal = request.getParameter("country");
        int teamIdVal = Integer.parseInt(request.getParameter("teamId"));

        String jsonPath = "/json/teams/standings/" + teamIdVal + "/";
        ;
        String jsonFile;

        File file;
        Resource cpr;

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Define HashMap
        HashMap<String, Object> map = new HashMap<String, Object>();

        // League Index
        Integer leagueIdx = null;

        while (true) {
            // Json File Path & Name
            jsonFile = "Standings" + "_" + teamIdVal + "_" + sessonVal + ".json";

            // Read JSON File
            cpr = new ClassPathResource(jsonPath + jsonFile);

            // Check File Exists
            if (cpr.exists()) {
                file = cpr.getFile();

                TeamHistoryVO teamHistoryVO = om.readValue(file, TeamHistoryVO.class);
                for (int i = 0; i < teamHistoryVO.getResponse().size(); i++) {
                    if (teamHistoryVO.getResponse().get(i).getLeague().getCountry().equals(countryVal)) {
                        leagueIdx = i;
                    }
                }

                if (leagueIdx == null) {
                    break;
                }

                com.gisoun.football.team.domain.history.LeagueVO leagueVO = teamHistoryVO.getResponse().get(leagueIdx)
                        .getLeague();

                // map.put(Integer.toString(sessonVal), leagueVO.getStandings()[0].get(0));
                map.put(Integer.toString(sessonVal), leagueVO);

                sessonVal--;
            } else {
                break;
            }
        }

        return map;
    }
    
    @GetMapping(value = "/history/chart/logo")
    public String historyChartLeagueLogo(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        int leagueIdVal = Integer.parseInt(request.getParameter("id"));

        // Json File Path & Name
        String jsonPath = "/json/common/";
        String jsonFile = "Leagues" + ".json";

        // Read JSON File
        Resource cpr = new ClassPathResource(jsonPath + jsonFile);
        File file = cpr.getFile();

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        CommonLeaguesVO commonLeaguesVO = om.readValue(file, CommonLeaguesVO.class);
        List<com.gisoun.football.common.domain.leagues.ResponseVO> list = commonLeaguesVO.getResponse();

        String result;
        while (true) {
            // Find Index of Null (World)
            int index = IntStream.range(0, commonLeaguesVO.getResponse().size())
                    .filter(i -> Objects.equals(commonLeaguesVO.getResponse().get(i).getLeague().getId(), leagueIdVal))
                    .findFirst()
                    .orElse(-1);

            if (index == -1) {
                result = null;
                break;
            } else {
                result = commonLeaguesVO.getResponse().get(index).getLeague().getLogo();
                break;
            }
        }
        System.out.println(result);

        return result;
    }
    
    @GetMapping(value = "/squads")
    public HashMap<String, Object> competitionSquads(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int teamIdVal = Integer.parseInt(request.getParameter("id"));

        // Json File Path & Name
        String jsonPath = "/json/teams/squads/";
        String jsonFile = "Squads" + "_" + teamIdVal + ".json";

        // Read JSON File
        Resource cpr = new ClassPathResource(jsonPath + jsonFile);
        File file = cpr.getFile();

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        TeamSquadsVO teamSquadsVO = om.readValue(file, TeamSquadsVO.class);

        // Response.get(0).getPlayers
        List<PlayersVO> playersVO = teamSquadsVO.getResponse().get(0).getPlayers();

        List<Object> gkList = new ArrayList<Object>();
        List<Object> dfList = new ArrayList<Object>();
        List<Object> mfList = new ArrayList<Object>();
        List<Object> atList = new ArrayList<Object>();

        for (int i = 0; i < playersVO.size(); i++) {
            switch (playersVO.get(i).getPosition()) {
                case "Goalkeeper":
                    gkList.add(playersVO.get(i));
                    break;
                case "Defender":
                    dfList.add(playersVO.get(i));
                    break;
                case "Midfielder":
                    mfList.add(playersVO.get(i));
                    break;
                case "Attacker":
                    atList.add(playersVO.get(i));
                    break;
            }
        }

        // Define HashMap
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("GK", gkList);
        map.put("DF", dfList);
        map.put("MF", mfList);
        map.put("AT", atList);

        return map;
    }

}
