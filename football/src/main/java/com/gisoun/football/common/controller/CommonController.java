package com.gisoun.football.common.controller;

import java.io.File;
import java.util.Collections;
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
import com.gisoun.football.common.domain.countries.CountriesVO;
import com.gisoun.football.common.domain.countries.ResponseVO;
import com.gisoun.football.common.domain.leagues.CommonLeaguesVO;

@CrossOrigin
@RestController
@EnableAutoConfiguration
@RequestMapping("/common")
public class CommonController {
    
    @GetMapping("/countries")
    public List<ResponseVO> countriesMenu(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Json File Path & Name
        String jsonPath = "/json/common/";
        String jsonFile = "Countries" + ".json";

        // Read JSON File
        Resource cpr = new ClassPathResource(jsonPath + jsonFile);
        File file = cpr.getFile();

        // Create ObjectMapper
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Get Data
        CountriesVO countriesVO = om.readValue(file, CountriesVO.class);

        List<ResponseVO> result = countriesVO.getResponse();

        while (true) {
            // Find Index of Null (World)
            int index = IntStream.range(0, countriesVO.getResponse().size())
                .filter(i -> Objects.equals(countriesVO.getResponse().get(i).getCode(), null))
                .findFirst()
                .orElse(-1);
            
            if (index == -1) {
                break;
            } else {
                result.remove(index);
            }
        }

        return result;
    }
    
    @GetMapping("/leagues")
    public List<com.gisoun.football.common.domain.leagues.ResponseVO> leaguesMenu(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
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

        List<com.gisoun.football.common.domain.leagues.ResponseVO> result = commonLeaguesVO.getResponse();

        while (true) {
            // Find Index of Null (World)
            int index = IntStream.range(0, commonLeaguesVO.getResponse().size())
                    .filter(i -> Objects.equals(commonLeaguesVO.getResponse().get(i).getCountry().getCode(), null))
                    .findFirst()
                    .orElse(-1);

            if (index == -1) {
                break;
            } else {
                result.remove(index);
            }
        }
        
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getSeasons().size() <= 1) {
                continue;
            } else {
                Collections.sort(result.get(i).getSeasons(), (e1, e2) -> e1.getYear() - e2.getYear());
                result.get(i).getSeasons().subList(0, result.get(i).getSeasons().size() - 1).clear();;
            }
        }

        return result;
    }
}
