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
    leagueIdList: 
      {
        GB: {
          England:
          {
            0: [39],
            1: [40],
            2: [41],
            3: [42],
            4: [43],
            5: [50, 51],
            6: [58, 59, 60, 931],
          }
        },
      }
    
  })
})
