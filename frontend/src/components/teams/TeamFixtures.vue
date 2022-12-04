<template>
  <v-card min-height="45vh" class="pa-3" outlined>
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
        <div v-if="setDateBorder(props) === 0" class="text-center" style="border-right: 2px solid gold;">
          <h4>
            {{ timestampToDate(props.fixture.timestamp) }}
          </h4>
        </div>

        <div v-else-if="setDateBorder(props) === 1" class="text-center" style="border-right: 2px solid blue;">
          <h4>
            {{ timestampToDate(props.fixture.timestamp) }}
          </h4>
        </div>

        <div v-else-if="setDateBorder(props) === 2" class="text-center" style="border-right: 2px solid lightgreen;">
          <h4>
            {{ timestampToDate(props.fixture.timestamp) }}
          </h4>
        </div>

        <div v-else-if="setDateBorder(props) === 3" class="text-center" style="border-right: 2px solid red;">
          <h4>
            {{ timestampToDate(props.fixture.timestamp) }}
          </h4>
        </div>
      </template>

      <template #item-homeInfo="props">
        <div class="home-wrapper text-left" @click="pushTeamData(props.teams.home.id)">
          <v-img class="logo" :src="props.teams.home.logo" />
          <a class="name" target="_blank">
            {{ props.teams.home.name }}
          </a>
        </div>
      </template>

      <template #item-scoreInfo="props">
        <div class="text-center" v-if="props.fixture.status.short === 'FT'">

          <h3>{{ props.goals.home }} - {{ props.goals.away }}</h3>
          <div>
            <a>{{ timestampToTime(props.fixture.timestamp) }}</a>
          </div>
        </div>
        <div class="text-center" v-else>

          <h3>{{ timestampToTime(props.fixture.timestamp) }}</h3>
        </div>
      </template>

      <template #item-awayInfo="props">
        <div class="away-wrapper text-right" @click="pushTeamData(props.teams.away.id)">
          <a class="name" target="_blank">
            {{ props.teams.away.name }}
          </a>
          <v-img class="logo" :src="props.teams.away.logo" />
        </div>
      </template>
    </EasyDataTable>
  </v-card>
</template>

<script>
// dayjs Library
import dayjs from "dayjs";
import "dayjs/locale/ko";
dayjs.locale("ko");

// import { leagueStore } from "@/store/leagueStore";
import { getFixturesAPI } from "@/api/teams";

export default {
  data: () => ({
    // Fixtures
    fixtureHeaders: [
      { text: "일정", value: "dateInfo" },
      { text: "홈", value: "homeInfo" },
      { text: "결과", value: "scoreInfo" },
      { text: "원정", value: "awayInfo" },
    ],
    overviewFixtures: [],
    defaultDate: dayjs().format("YYYY-MM-DD"),
    loading: false,
  }),
  mounted() {
    this.getFixtures();
  },
  methods: {
    getFixtures() {
      this.loading = true;

      return new Promise(() => {
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
              this.loading = false;
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
          season: dayjs().format("YYYY"),
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
    },
    pushTeamData(teamId) {
      this.$paramsTeam({
        country: this.$route.params.country,
        teamId: teamId,
        leagueId: this.$route.params.leagueId,
      });
      this.movePage("/teams" + "/" + this.$route.params.country + "/" + this.$route.params.leagueId + "/" + teamId);
    },
  }
}
</script>

<style>
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
</style>