import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8080/",
    // baseURL: "http://192.168.0.112:8080/",
});

/**
 * View Board List
 * @param {*} params
 */

function getBoardListAPI(params) {
    return instance.get("/board/list", params);
}

export {
    getBoardListAPI,
}