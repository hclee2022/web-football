import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8090/",
});

/**
 * View Board List
 * @param {*} params
 */
function getBoardListAPI(params) {
    return instance.get("/board/list", params);
}

/**
 * View Board Detail
 * @param {*} params
 */
function getBoardDetailAPI(params) {
    return instance.get("/board/detail", params);
}

/**
 * Insert Board
 * @param {*} params
 */
function insertBoardAPI(params) {
    return instance.post("/board/insert", null, params)
}

/**
 * Update Board
 * @param {*} params
 */
function updateBoardAPI(params) {
    return instance.post("/board/update", null, params)
}

/**
 * Delete Board
 * @param {*} params
 */
function deleteBoardAPI(params) {
    return instance.post("/board/delete", null, params)
}

/**
 * Insert Reply
 * @param {*} params
 */
function insertReplyAPI(params) {
    return instance.post("/board/reply/insert", null, params)
}

/**
 * View Reply List
 * @param {*} params
 */
function getReplyListAPI(params) {
    return instance.get("/board/reply/list", params);
}

/**
 * Update Reply
 * @param {*} params
 */
function updateReplyAPI(params) {
    return instance.post("/board/reply/update", null, params)
}

/**
 * Delete Reply
 * @param {*} params
 */
function deleteReplyAPI(params) {
    return instance.post("/board/reply/delete", null, params)
}

export {
    getBoardListAPI,
    getBoardDetailAPI,
    insertBoardAPI,
    updateBoardAPI,
    deleteBoardAPI,
    insertReplyAPI,
    getReplyListAPI,
    updateReplyAPI,
    deleteReplyAPI,
}