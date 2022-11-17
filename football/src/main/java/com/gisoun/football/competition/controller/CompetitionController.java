package com.gisoun.football.competition.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisoun.football.competition.domain.fixtures.CompetitionFixturesVO;
import com.gisoun.football.competition.domain.fixtures.ResponseVO;
import com.gisoun.football.competition.domain.history.TeamHistoryVO;
import com.gisoun.football.competition.domain.squads.PlayersVO;
import com.gisoun.football.competition.domain.squads.SquadsVO;
import com.gisoun.football.competition.domain.standings.CompetitionStandingsVO;
import com.gisoun.football.competition.domain.standings.LeagueVO;

@CrossOrigin
@RestController
@EnableAutoConfiguration
@RequestMapping("/competitions")
public class CompetitionController {
    
    @GetMapping(value = "/standings")
    public HashMap<String, Object> competitionStandings(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // Get Params From Front-End
        int leagueIdVal = Integer.parseInt(request.getParameter("id"));
        int sessonVal = Integer.parseInt(request.getParameter("sesson"));

        // Json File Path & Name
        String jsonPath = "/json/competitions/standings/";
        String jsonFile = "Standings" + "_" + leagueIdVal + "_" + sessonVal + ".json";

        // Read JSON File
        Resource cpr = new ClassPathResource(jsonPath + jsonFile);
        File file = cpr.getFile();

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        CompetitionStandingsVO competitionStandingsVO = om.readValue(file, CompetitionStandingsVO.class);

        // Response[0].League
        LeagueVO leagueVO = competitionStandingsVO.getResponse().get(0).getLeague();

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
        String jsonFile = "Fixtures" + "_" + leagueIdVal + "_" + sessonVal + ".json";

        // Read JSON File
        Resource cpr = new ClassPathResource(jsonPath + jsonFile);
        File file = cpr.getFile();

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        CompetitionFixturesVO competitionFixturesVO = om.readValue(file, CompetitionFixturesVO.class);

        List<ResponseVO> result = competitionFixturesVO.getResponse();

        return result;
    }

    @GetMapping(value = "/history")
    public HashMap<String, Object> competitionHistory(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Get Params From Front-End
        int leagueIdVal = Integer.parseInt(request.getParameter("leagueId"));
        int sessonVal = Integer.parseInt(request.getParameter("sesson"));
        int teamIdVal = Integer.parseInt(request.getParameter("teamId"));

        String jsonPath;
        String jsonFile;

        File file;
        Resource cpr;

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Define HashMap
        HashMap<String, Object> map = new HashMap<String, Object>();

        while (true) {
            // Json File Path & Name
            jsonPath = "/json/competitions/standings/";
            jsonFile = "Standings" + "_" + leagueIdVal + "_" + sessonVal + ".json";

            // Read JSON File
            cpr = new ClassPathResource(jsonPath + jsonFile);

            // Check File Exists
            if (cpr.exists()) {
                file = cpr.getFile();

                TeamHistoryVO teamHistoryVO = om.readValue(file, TeamHistoryVO.class);
                com.gisoun.football.competition.domain.history.LeagueVO leagueVO = teamHistoryVO.getResponse().get(0)
                        .getLeague();

                // Find Index of Team In Standings
                int index = IntStream.range(0, leagueVO.getStandings()[0].size())
                        .filter(i -> Objects.equals(leagueVO.getStandings()[0].get(i).getTeam().getId(), teamIdVal))
                        .findFirst()
                        .orElse(-1);

                if (index == -1) {
                    break;
                }

                map.put(Integer.toString(sessonVal), leagueVO.getStandings()[0].get(index));

                sessonVal--;
            } else {
                break;
            }
        }

        return map;
    }
    
    @GetMapping(value = "/squads")
    public HashMap<String, Object> competitionSquads(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int teamIdVal = Integer.parseInt(request.getParameter("id"));

        // Json File Path & Name
        String jsonPath = "/json/competitions/squads/";
        String jsonFile = "Squads" + "_" + teamIdVal + ".json";

        // Read JSON File
        Resource cpr = new ClassPathResource(jsonPath + jsonFile);
        File file = cpr.getFile();

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        SquadsVO squadsVO = om.readValue(file, SquadsVO.class);

        // Response.get(0).getPlayers
        List<PlayersVO> playersVO = squadsVO.getResponse().get(0).getPlayers();

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
