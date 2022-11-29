import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8090/",
});

function getStandingsAPI(params) {
    return instance.get("/teams/standings", params);
}

function getFixturesAPI(params) {
    return instance.get("/teams/fixtures", params);
}

function getHistoryAPI(params) {
    return instance.get("/teams/history", params);
}

function getHistoryChartLeagueLogoAPI(params) {
    return instance.get("/teams/history/chart/logo", params);
}

function getSquadsAPI(params) {
    return instance.get("/teams/squads", params);
}

export {
    getStandingsAPI,
    getFixturesAPI,
    getHistoryAPI,
    getSquadsAPI,
    getHistoryChartLeagueLogoAPI,
}