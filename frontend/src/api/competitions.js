import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8090/",
});

function getCompetitionAPI(params) {
    return instance.get("/competitions/standings", params);
}

export {
    getCompetitionAPI,
}