import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8090/",
});

function getFixturesAPI(params) {
    return instance.get("/competitions/fixtures", params);
}

export {
    getFixturesAPI,
}