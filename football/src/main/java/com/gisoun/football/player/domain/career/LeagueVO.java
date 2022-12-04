package com.gisoun.football.player.domain.career;

public class LeagueVO {

    private Integer id;
    private String name;
    private String country;
    private String logo;
    private Integer customSeason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getCustomSeason() {
        return customSeason;
    }

    public void setCustomSeason(Integer customSeason) {
        this.customSeason = customSeason;
    }

}
