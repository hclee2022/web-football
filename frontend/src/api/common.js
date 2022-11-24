import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8090/",
});

function getCountriesAPI() {
    return instance.get("/common/countries");
}

function getLeaguesAPI() {
    return instance.get("/common/leagues");
}

function getCupsAPI() {
    return instance.get("/common/cups");
}

export {
    getCountriesAPI,
    getLeaguesAPI,
    getCupsAPI,
}