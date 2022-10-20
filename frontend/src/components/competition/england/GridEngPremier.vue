<template>
  <v-container>
    <v-card elevation="10" outlined width="100%" class="mx-auto">
      <v-card-title>
        {{ leagueInfo[0] }}
        <v-img
          height="100"
          :src="leagueInfo[1]" />
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col>
            <EasyDataTable
              :headers="headers"
              :items="document"
              :rows-per-page="20"
              hide-rows-per-page
              hide-footer>

              <template #item-teamInfo="props">
                <div class="team-wrapper">
                  <v-img class="logo" :src="props.team.logo" />
                  <a class="name" target="_blank" :href="props.team.logo">
                    {{ props.team.name }}
                  </a>
                </div>
              </template>

            </EasyDataTable>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
// import axios from "axios";
import { getInfoEPL, getStandingEPL } from "@/api/competition";

export default {
  components: {
    // eslint-disable-next-line vue/no-unused-components
    EasyDataTable: window["vue3-easy-data-table"],
  },
  data: () => ({
    leagueInfo: [],
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
    ],
    document: [],
    // loading: false,
  }),
  mounted() {
    this.leagueInfo = getInfoEPL();
    this.getStandingList();
  },
  // watch: {
  //   options: {
  //     handler() {
  //       this.getStandingList();
  //     },
  //     deep: true,
  //   },
  // },
  methods: {
    // getStandingDataFromAPI() {
    //   return getStandingEPL().then(
    //     response => {
    //       console.log(response);
    //       return response.data;
    //     }
    //   )
    //     .catch(error => {
    //       console.log(error);
    //   })
    // },
    // getStandingList() {
    //   const me = this;
    //   this.loading = true;

    //   // eslint-disable-next-line no-unused-vars
    //   return new Promise((resolve, reject) => {
    //     let items = this.getStandingDataFromAPI().then(
    //       response => {
    //         items = response.data;

    //         setTimeout(() => {
    //           me.loading = false;
    //           this.document = items;
    //           console.log("docuemnt : ");
    //           console.log(this.document);
    //         }, 1000);
    //       }
    //     )
    //   })
    // },
    getStandingList() {
      this.document = getStandingEPL();
      console.log("this.document : ");
      console.log(this.document);;
      console.log(this.document[0].rank);
    },
    onClickRow() {
      console.log("GridEngPremier : onClickRow");
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
    /* cursor: pointer; */
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