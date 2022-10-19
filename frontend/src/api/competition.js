import standingJSON from "@/assets/json/standing_2022_epl.json";

const response = standingJSON.response[0];

function getInfoEPL() {
    return [response.league["name"], response.league["logo"]];
}

function getStandingEPL() {
    console.log(standingJSON.response[0].league.standings[0]);
    console.log(standingJSON.response[0].league.standings[0][0].all["goals"]);
    return response.league.standings[0];
}

export {
    getInfoEPL,
    getStandingEPL,
}