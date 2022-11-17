import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8090/",
});

function getStandingsAPI(params) {
    return instance.get("/competitions/standings", params);
}

function getFixturesAPI(params) {
    return instance.get("/competitions/fixtures", params);
}

function getHistoryAPI(params) {
    return instance.get("/competitions/history", params);
}
function getSquadsAPI(params) {
    return instance.get("/competitions/squads", params);
}

export {
    getStandingsAPI,
    getFixturesAPI,
    getHistoryAPI,
    getSquadsAPI,
}