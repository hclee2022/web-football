<template>
  <v-card min-height="45vh" class="pa-3" outlined>
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
</template>

<script>
import { leagueStore } from "@/store/leagueStore";
import { getStandingsAPI } from "@/api/teams";

export default {
  data: () => ({
    // Standings
    overviewStandings: [],
    teamRank: null,
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
  }),
  mounted() {
    this.getStandings();
  },
  methods: {
    getStandings() {
      this.loading = true;

      return new Promise(() => {
        let items = this.getStandingsFromAPI().then(
          response => {
            // Get Standings
            items = response.standings;
            this.$emit("leagueSizeFromChild", items.length); // Used In Team -> TeamHistory

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
      this.$emit("teamNameFromChild", selectedTeam.team.name);
      this.$emit("teamLogoFromChild", selectedTeam.team.logo);
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
    rowOfSelectedTeam(item) {
      if (item.rank == this.teamRank) {
        return "selected-row";
      }
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

.selected-row {
  --easy-table-body-row-background-color: rgb(205, 232, 243);
}
</style>