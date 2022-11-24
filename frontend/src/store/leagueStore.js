import { defineStore } from "pinia";

export const leagueStore = defineStore("leagueStore", {
  state: () => ({
    // Legacy
    // leagueId: {
    //   "EPL": 39,
    //   "La_Liga": 140,
    //   "K1": 292,
    // },
    // sesson: {
    //   "S22": 2022,
    // },
    curLeague: "",
    leagueIdList: {
        England: [39, 40, 41, 42, 43, 50, 51, 58, 59, 60, 931],
    },
  }),
  actions: {
    setCurLeague(name) {
      this.curLeague = name;
    },
  },
  getters: {
    getLeaguesOfCountry: (state) => {
      switch (state.curLeague) {
        case "England":
          return Object.values(state.leagueIdList.England);
        case "Spain":
          break;
      }
    }
  },
})
