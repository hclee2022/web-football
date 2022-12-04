import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8090/",
});

function getPlayerBannerAPI(params) {
    return instance.get("/player/banner", params);
}

function getPlayerCareerAPI(params) {
    return instance.get("/player/statistics/career", params);
}

export {
    getPlayerBannerAPI,
    getPlayerCareerAPI,
}