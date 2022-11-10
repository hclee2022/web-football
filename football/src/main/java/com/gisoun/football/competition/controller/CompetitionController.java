package com.gisoun.football.competition.controller;

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
import com.gisoun.football.competition.domain.fixtures.CompetitionFixturesVO;
import com.gisoun.football.competition.domain.fixtures.ResponseVO;
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

        CompetitionFixturesVO competitionFixturesVO = om.readValue(file, CompetitionFixturesVO.class);

        List<ResponseVO> result = competitionFixturesVO.getResponse();

        return result;
    }
}
