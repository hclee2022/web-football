<template>
  <v-container>
    <v-card elevation="10" outlined width="60%" class="mx-auto">
      <v-banner>
        <v-row align="center">
          <v-col class="text-center" cols="3">
            <div class="player pa-0 ma-0" outlined>
              <v-img class="photo" :src="bannerList.photo" />
              <div class="flag">
                <v-img :src="bannerList.flag" />
              </div>
              <div class="position">
                <b>{{ bannerList.position }}</b>
              </div>
            </div>
            <br />
            <div>
              <h2>No. {{ bannerList.number }}</h2>
            </div>
          </v-col>
          <v-divider vertical inset />
          <v-col class="ma-0">
            <v-row>
              <v-col>
                <div class="banner-team-wrapper pa-0 ma-0" @click="pushTeamData(bannerList.leagueCountry, bannerList.teamId, bannerList.leagueId)">
                  <v-img class="logo" :src="bannerList.teamLogo" />
                  <a class="name" target="_blank">
                    <b>{{ bannerList.teamName }}</b>
                  </a>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <div class="pa-0 ma-0" outlined>
                  <h2>{{ bannerList.playerName }}</h2>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <div class="pa-0 ma-0" outlined>
                  <b>{{ bannerList.birthDate }} ({{ bannerList.age }}세)</b>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <div class="pa-0 ma-0" outlined>
                  <b>{{ bannerList.height }}</b>
                  <br />
                  <b>{{ bannerList.weight }}</b>
                </div>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-banner>
      <v-row>
        <v-col>
          <EasyDataTable
            :headers="headers"
            :items="careerItems"
            :rows-per-page="100"
            :loading="loading"
            hide-rows-per-page
            hide-footer
            header-text-direction="center"
            body-text-direction="center">

            <template #header-cardYellow="header">
              <div>
                <v-icon color="yellow">
                  {{ header.text }}
                </v-icon>
              </div>
            </template>

            <template #header-cardRed="header">
              <div>
                <v-icon color="red">
                  {{ header.text }}
                </v-icon>
              </div>
            </template>

            <template #item-season="props">
              <b>
                {{ (props.league.customSeason - 2000) + "/" + (props.league.customSeason - 1999) }}
              </b>
            </template>

            <template #item-league="props">
              <div class="career-league-wrapper text-left" @click="clickCompetition('/competitions', props.league.id, props.league.country)">
                <v-img class="logo" :src="props.league.logo" />
                <a class="name" target="_blank">
                  {{ props.league.name }}
                </a>
              </div>
            </template>

            <template #item-team="props">
              <div class="career-team-wrapper text-left" @click="pushTeamData(props.league.country, props.team.id, props.league.id)">
                <v-img class="logo" :src="props.team.logo" />
                <a class="name" target="_blank">
                  {{ props.team.name }}
                </a>
              </div>
            </template>

            <template #item-cardYellow="props">
              {{ props.cards.yellow }}
            </template>

            <template #item-cardRed="props">
              {{ props.cards.red }}
            </template>

          </EasyDataTable>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
import { getPlayerBannerAPI, getPlayerCareerAPI } from "@/api/player";

// dayjs Library
import dayjs from "dayjs";
import "dayjs/locale/ko";
dayjs.locale("ko");

export default {
  data: () => ({
    bannerList: [],
    headers: [
      { text: "시즌", value: "season" },
      { text: "리그", value: "league" },
      { text: "팀", value: "team" },
      { text: "출장", value: "games.appearences" },
      { text: "득점", value: "goals.total" },
      { text: "도움", value: "goals.assists" },
      { text: "mdi-card mdi-rotate-90", value: "cardYellow" },
      { text: "mdi-card mdi-rotate-90", value: "cardRed" },
    ],
    careerItems: [],
    loading: false,
  }),
  mounted() {
    this.getPlayerBanner();
    this.getPlayerCareer();
  },
  methods: {
    getPlayerBanner() {
      return new Promise(() => {
        let bannerItems = this.getPlayerBannerFromAPI().then(
          response => {
            bannerItems = response;
            this.bannerList = bannerItems;
          }
        )
      })
    },
    async getPlayerBannerFromAPI() {
      return getPlayerBannerAPI({
        params: {
          id: this.$route.params.id,
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
    getPlayerCareer() {
      return new Promise(() => {
        this.loading = true;

        let items = this.getPlayerCareerFromAPI().then(
          response => {
            items = response;
            console.log(items);

            setTimeout(() => {
              this.loading = false;

              this.careerItems = Object.values(items).map((v) => v);
            }, 1000);
          }
        )
      })
    },
    async getPlayerCareerFromAPI() {
      return getPlayerCareerAPI({
        params: {
          id: this.$route.params.id,
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
    pushTeamData(country, teamId, leagueId) {
      this.$paramsTeam({
        country: country,
        teamId: teamId,
        leagueId: leagueId,
      });
      this.movePage("/teams" + "/" + country + "/" + leagueId + "/" + teamId);
    },
    clickCompetition(to, id, country) {
      this.$paramsCompetitions({
        country: country,
        id: id,
        season: dayjs().format("YYYY"),
      });
      this.movePage(to + "/" + country + "/" + id  + "/" + dayjs().format("YYYY"));
    },
  },
}
</script>

<style>
.player {
  position: relative;
}

.player .photo {
  border-radius: 50%;
}

.player .flag {
  width: 25px;
  position: absolute;
  left: 0px;
  top: 0px;
  border: solid #eaeaea;
}

.player .position {
  margin: 0 auto;
  width: 30px;
  height: 30px;
  position: absolute;
  right: 0px;
  top: 0px;
  border: 3px solid green;
  border-radius: 50%;
}

.banner-team-wrapper {
  padding: 5px;
  /* display: flex; */
  align-items: center;
  justify-items: center;
  cursor: pointer;
}

.banner-team-wrapper .logo {
  margin-right: 5px;
  display: inline-block;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  object-fit: cover;
  vertical-align: middle;
  /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
}

.banner-team-wrapper .name {
  font-size: 20px;
  text-decoration: none;
  color: green;
  vertical-align: middle;
}

.table-data {
  padding: 5px;
  /* display: flex; */
  align-items: center;
  justify-items: center;
  cursor: pointer;
}

.table-data .logo {
  margin-right: 5px;
  display: inline-block;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  object-fit: cover;
  vertical-align: middle;
  /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
}

.career-league-wrapper {
  padding: 5px;
  /* display: flex; */
  align-items: center;
  justify-items: center;
  cursor: pointer;
}

.career-league-wrapper .logo {
  margin-right: 5px;
  display: inline-block;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  object-fit: cover;
  vertical-align: middle;
  /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
}

.career-league-wrapper .name {
  text-decoration: none;
  vertical-align: middle;
  color: green;
}

.career-team-wrapper {
  padding: 5px;
  /* display: flex; */
  align-items: center;
  justify-items: center;
  cursor: pointer;
}

.career-team-wrapper .logo {
  margin-right: 10px;
  display: inline-block;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  object-fit: cover;
  vertical-align: middle;
  /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
}

.career-team-wrapper .name {
  text-decoration: none;
  color: green;
}
</style>