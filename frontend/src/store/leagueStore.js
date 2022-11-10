import { defineStore } from "pinia";

export const leagueStore = defineStore("leagueStore", {
  state: () => ({
    leagueId: {
      "EPL": 39,
      "La_Liga": 140,
      "K1": 292,
    },
    sesson: {
      "S22": 2022,
    },
    teamsObj: {
      teamInfo: {},
    },
  })
})
