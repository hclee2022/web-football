<template>
  <EasyDataTable
    :headers="headers"
    :items="standingsList"
    :rows-per-page="20"
    :loading="loading"
    hide-rows-per-page
    hide-footer>

    <template #item-rank="props">
      <h3>
        {{ props.rank }}
      </h3>
    </template>

    <template #item-teamInfo="props">
      <div class="team-wrapper" @click="pushTeamData(props)">
        <v-img class="logo" :src="props.team.logo" />
        <a class="name" target="_blank">
          {{ props.team.name }}
        </a>
      </div>
    </template>
  
  </EasyDataTable>
</template>

<script>
import { getStandingsAPI } from "@/api/competitions";

export default {
  data: () => ({
    loading: false,
    headers: [
      { text: "순위", value: "rank", sortable: true },
      { text: "팀명", value: "teamInfo", sortable: true },
      { text: "경기", value: "all.played", sortable: true },
      { text: "승", value: "all.win", sortable: true },
      { text: "무", value: "all.draw", sortable: true },
      { text: "패", value: "all.lose", sortable: true },
      { text: "득점", value: "all.goals.for", sortable: true },
      { text: "실점", value: "all.goals.against", sortable: true },
      { text: "득실차", value: "goalsDiff", sortable: true },
      { text: "승점", value: "points", sortable: true },
      { text: "최근 5경기", value: "form" },
    ],
    standingsList: [],
  }),
  mounted() {
    this.getStandings();
  },
  methods: {
    getStandings() {
      this.loading = true;

      // eslint-disable-next-line no-unused-vars
      return new Promise((resolve, reject) => {
        let items = this.getStandingsFromAPI().then(
          response => {
            items = response.standings;
            this.$emit("logoFromStandings", response.logo);
            this.$emit("nameFromStandings", response.name);

            setTimeout(() => {
              this.loading = false;
              this.standingsList = items;
            }, 1000);
          }
        )
      })
    },
    async getStandingsFromAPI() {
      console.log(this.$route.params);
      return getStandingsAPI({
        params: this.$route.params,
      })
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    pushTeamData(data) {
      this.$router.push({
        name: "Team",
        params: {
          country: this.$route.params.country,
          teamId: data.team.id,
          leagueId: this.$route.params.id,
        },
      });
      this.movePage("/teams" + "/" + this.$route.params.country + "/" + this.$route.params.id + "/" + data.team.id);
    },
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
</style>