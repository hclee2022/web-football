<template>
  <v-container>
    <!-- <v-card> -->
    <v-card elevation="10" outlined width="100%" class="mx-auto overflow-y-auto">
      <v-banner>
        <v-row align="center">
          <v-col cols="1">
            <Button
              @click="prevPage"
              color="light-green lighten-1"
              icon
              iconName="mdi-arrow-left-thick">
            </Button>
          </v-col>
          <v-col cols="1">
            <div class="pa-1" outlined>
              <v-img :src="teamLogo" />
            </div>
          </v-col>
          <v-col cols="4">
            <div class="pa-1" outlined>
              <h1>{{ teamName }}</h1>
            </div>
          </v-col>
        </v-row>
      </v-banner>
      <div class="pa-5">
        <v-row>
          <v-col cols="6">
            <v-card
              min-height="45vh"
              class="pa-3"
              outlined>
              <EasyDataTable
                :headers="standingHeaders"
                :items="overviewStandings"
                :rows-per-page="5"
                :body-row-class-name="rowOfSelectedTeam"
                :loading="loading"
                hide-rows-per-page
                hide-footer>

                <template #item-rank="props">
                  <h3>
                    {{ props.rank }}
                  </h3>
                </template>

                <template #item-teamInfo="props">
                  <div class="team-wrapper">
                    <v-img class="logo" :src="props.team.logo" />
                    <a class="name" target="_blank">
                      {{ props.team.name }}
                    </a>
                  </div>
                </template>
              </EasyDataTable>
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card
              min-height="45vh"
              class="pa-3"
              outlined>
              <EasyDataTable
                :headers="fixtureHeaders"
                :items="overviewFixtures"
                :rows-per-page="5"
                :loading="loading"
                hide-rows-per-page
                hide-footer
                header-text-direction="center"
                body-text-direction="center">

                <template #item-dateInfo="props">
                  <div
                    v-if="setDateBorder(props) === 0"
                    class="text-center"
                    style="border-right: 2px solid gold;">
                    <h4>
                      {{ timestampToDate(props.fixture.timestamp) }}
                    </h4>
                  </div>

                  <div
                    v-else-if="setDateBorder(props) === 1"
                    class="text-center"
                    style="border-right: 2px solid blue;">
                    <h4>
                      {{ timestampToDate(props.fixture.timestamp) }}
                    </h4>
                  </div>

                  <div
                    v-else-if="setDateBorder(props) === 2"
                    class="text-center"
                    style="border-right: 2px solid lightgreen;">
                    <h4>
                      {{ timestampToDate(props.fixture.timestamp) }}
                    </h4>
                  </div>

                  <div
                    v-else-if="setDateBorder(props) === 3"
                    class="text-center"
                    style="border-right: 2px solid red;">
                    <h4>
                      {{ timestampToDate(props.fixture.timestamp) }}
                    </h4>
                  </div>
                </template>

                <template #item-homeInfo="props">
                  <div class="home-wrapper text-left">
                    <v-img class="logo" :src="props.teams.home.logo" />
                    <a class="name" target="_blank">
                      {{ props.teams.home.name }}
                    </a>
                  </div>
                </template>

                <template #item-scoreInfo="props">
                  <div
                    class="text-center"
                    v-if="props.fixture.status.short === 'FT'">

                    <h3>{{ props.goals.home }} - {{ props.goals.away }}</h3>
                    <div>
                      <a>{{ timestampToTime(props.fixture.timestamp) }}</a>
                    </div>
                  </div>
                  <div
                    class="text-center"
                    v-else>

                    <h3>{{ timestampToTime(props.fixture.timestamp) }}</h3>
                  </div>
                </template>

                <template #item-awayInfo="props">
                  <div class="away-wrapper text-right">
                    <a class="name" target="_blank">
                      {{ props.teams.away.name }}
                    </a>
                    <v-img class="logo" :src="props.teams.away.logo" />
                  </div>
                </template>
              </EasyDataTable>
            </v-card>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-card class="pa-3" outlined>
              <!-- History -->
              <Line
                :chart-data="historyChartData"
                :chart-options="historyChartOptions"
                :height="100" />
            </v-card>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-card class="pa-3" outlined>
              Squard
            </v-card>
          </v-col>
        </v-row>
      </div>
    </v-card>
  </v-container>
</template>

<script>
import { leagueStore } from "@/store/leagueStore";
import { getStandingsAPI, getFixturesAPI } from "@/api/teams";

// dayjs Library
import dayjs from "dayjs";
import "dayjs/locale/ko";
dayjs.locale("ko");

// chart.js + vue-chartjs
import { Line } from "vue-chartjs";
// import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, PointElement, CategoryScale, LinearScale } from "chart.js";
// ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, CategoryScale, LinearScale);
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

export default {
  components: {
    // eslint-disable-next-line vue/no-unused-components
    EasyDataTable: window["vue3-easy-data-table"],
    Line,
  },
  data: () => ({
    // Standings
    overviewStandings: [],
    teamRank: null,
    teamName: "",
    teamLogo: "",
    standingHeaders: [
      { text: "순위", value: "rank", sortable: true },
      { text: "팀명", value: "teamInfo", sortable: true },
      { text: "경기", value: "all.played", sortable: true },
      { text: "승", value: "all.win", sortable: true },
      { text: "무", value: "all.draw", sortable: true },
      { text: "패", value: "all.lose", sortable: true },
      { text: "승점", value: "points", sortable: true },
    ],
    loading: false,

    // Fixtures
    fixtureHeaders: [
      { text: "일정", value: "dateInfo" },
      { text: "홈", value: "homeInfo" },
      { text: "결과", value: "scoreInfo" },
      { text: "원정", value: "awayInfo" },
    ],
    overviewFixtures: [],
    defaultDate: dayjs().format("YYYY-MM-DD"),
    historyChartData: {
      labels: ["10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-19", "19-20", "20-21", "21-22", "22-23"],
      datasets: [
        {
          type: "line",
          label: "History",
          borderColor: "#f87979",
          data: [3, 1, 2, 1, 2, 4, 3, 1, 1, 2, 1, 1, 2],
          fill: false,
          tension: 0.1,
        },
      ],
    },
    historyChartOptions: {
      responsive: true,
      scales: {
        y: {
          axis: "y",
          afterDataLimits: (scale) => {
            console.log(scale);
            scale.max = scale.max * 1.2;
          },
        },
      }
    },
  }),
  mounted() {
    this.getStandings();
    this.getFixtures();
  },
  methods: {
    getStandings() {
      this.loading = true;

      // eslint-disable-next-line no-unused-vars
      return new Promise((resolve, reject) => {
        let items = this.getStandingsFromAPI().then(
          response => {
            // Get Standings
            items = response.standings;

            setTimeout(() => {
              this.loading = false;

              this.setBanner(
                items.filter((v) => {
                  return v.team.id === parseInt(this.$route.params.teamId);
                })[0]
              );

              this.overviewStandings = this.setStandings(items);
            }, 1000);
          }
        )
      })
    },
    async getStandingsFromAPI() {
      return getStandingsAPI({
        params: {
          id: this.$route.params.leagueId,
          sesson: leagueStore().$state.sesson.S22,
        },
      })
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    setBanner(selectedTeam) {
      this.teamRank = selectedTeam.rank;
      this.teamName = selectedTeam.team.name;
      this.teamLogo = selectedTeam.team.logo;
    },
    setStandings(items) {
      if (this.teamRank <= 3) {
        return items.slice(0, 5);
      } else if (this.teamRank >= (items.length - 2)) {
        return items.slice(-5);
      } else {
        return items.slice(this.teamRank - 3, this.teamRank + 2);
      }
    },
    getFixtures() {
      // eslint-disable-next-line no-unused-vars
      return new Promise((resolve, reject) => {
        let items = this.getFxituresListFromAPI().then(
          response => {
            // Get Fixtures of Selected Team
            items = response.filter((v) => {
              return (parseInt(this.$route.params.teamId) === v.teams.home.id)
                || (parseInt(this.$route.params.teamId) === v.teams.away.id)
            })
              .sort((a, b) => { // Sort ASC
                return a.fixture.timestamp - b.fixture.timestamp;
              });

            setTimeout(() => {
              let fixtureIndex;

              this.defaultDate, fixtureIndex = this.setDefaultDate(
                [...new Set(items.map(
                  v => dayjs.unix(v.fixture.timestamp).format("YYYY-MM-DD")
                ))]
              );

              this.overviewFixtures = this.sliceByFixtures(items, fixtureIndex);
            }, 1000) // 1000 (ms)
          }
        )
      })
    },
    async getFxituresListFromAPI() {
      return getFixturesAPI({
        params: {
          id: this.$route.params.leagueId,
          sesson: leagueStore().$state.sesson.S22,
        },
      })
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    setDefaultDate(formatedDate) {
      for (var i = 0; i < formatedDate.length; i++) {
        if (dayjs(this.defaultDate).format("YYYY-MM-DD") === dayjs(formatedDate[i]).format("YYYY-MM-DD")) {
          return formatedDate[i], i;
        } else if (dayjs(this.defaultDate).format("YYYY-MM-DD") < dayjs(formatedDate[i]).format("YYYY-MM-DD")) {
          return formatedDate[i], i;
        }
      }
    },
    sliceByFixtures(items, fixtureIndex) {
      if (fixtureIndex <= 3) {
        return items.slice(0, 5);
      } else if (fixtureIndex >= (items.length - 2)) {
        return items.slice(-5);
      } else {
        return items.slice(fixtureIndex - 3, fixtureIndex + 2);
      }
    },
    timestampToTime(timestamp) {
      return dayjs.unix(timestamp).format("H:mm");
    },
    timestampToDate(timestamp) {
      return dayjs.unix(timestamp).format("YYYY년 MM월 DD일");
    },
    rowOfSelectedTeam(item) {
      if (item.rank == this.teamRank) {
        return "selected-row";
      }
    },
    setDateBorder(props) {
      if (props.fixture.status.short !== "FT") {
        return 0; // gold
      }

      if (!props.teams.home.winner && !props.teams.away.winner) {
        return 1; // blue
      } else if (((props.teams.home.id === parseInt(this.$route.params.teamId)) && props.teams.home.winner)
       || ((props.teams.away.id === parseInt(this.$route.params.teamId)) && props.teams.away.winner)) {
        return 2; // green
      } else if (((props.teams.home.id === parseInt(this.$route.params.teamId)) && !props.teams.home.winner)
       || ((props.teams.away.id === parseInt(this.$route.params.teamId)) && !props.teams.away.winner)) {
        return 3; // red
      }
    }
  },
}
</script>

<style>
  .team-wrapper {
    padding: 5px;
    /* display: flex; */
    align-items: center;
    justify-items: center;
    cursor: pointer;
  }

  .team-wrapper .logo {
    margin-right: 10px;
    display: inline-block;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    object-fit: cover;
    vertical-align: middle;
    /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
  }

  .team-wrapper .name {
    text-decoration: none;
    color: green;
  }

  .home-wrapper {
    padding: 5px;
    /* display: flex; */
    align-items: center;
    justify-items: center;
    cursor: pointer;
  }

  .home-wrapper .logo {
    margin-right: 10px;
    display: inline-block;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    object-fit: cover;
    vertical-align: middle;
    /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
  }

  .home-wrapper .name {
    text-decoration: none;
    color: green;
  }

  .away-wrapper {
    padding: 5px;
    /* display: flex; */
    align-items: center;
    justify-items: center;
    cursor: pointer;
  }

  .away-wrapper .logo {
    margin-left: 10px;
    display: inline-block;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    object-fit: cover;
    vertical-align: middle;
    /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
  }

  .away-wrapper .name {
    text-decoration: none;
    color: green;
  }

  .selected-row {
    --easy-table-body-row-background-color: rgb(205, 232, 243);
  }
</style>