<template>
  <v-container>
    <v-card elevation="10" outlined width="100%" class="mx-auto">
      <v-card-title>
        {{ leagueName }}
        <v-img
          height="100"
          :src="leagueLogo" />
      </v-card-title>
      <br>
      <v-tabs
        v-model="defaultTab"
        color="green"
        grow>

        <v-tab
          v-for="tab in tabs"
          :key="tab"
          :value="tab">

          <h3>
            {{ tab }}
          </h3>
        </v-tab>
      </v-tabs>
      <v-window
        v-model="defaultTab">

        <v-window-item value="순위표" eager>
          <v-card-text>
            <v-row>
              <v-col>
                <CompetitionStandings
                  @nameFromStandings="setLeagueName"
                  @logoFromStandings="setLeagueLogo" />
              </v-col>
            </v-row>
          </v-card-text>
        </v-window-item>
        <v-window-item value="일정" eager style="min-height: 100vh;">
          <CompetitionFixtures />
        </v-window-item>
      </v-window>
    </v-card>
  </v-container>
</template>

<script>
// import { leagueStore } from "@/store/leagueStore";

import CompetitionStandings from "@/components/competitions/CompetitionStandings";
import CompetitionFixtures from "@/components/competitions/CompetitionFixtures";

export default {
  components: {
    CompetitionStandings,
    CompetitionFixtures,
  },
  data: () => ({
    defaultTab: "순위표",
    defaultPage: 1,
    tabs: ["순위표", "일정"],
    leagueName: String,
    leagueLogo: String,
  }),
  mounted() {
    
  },
  methods: {
    setLeagueName(name) {
      this.leagueName = name;
    },
    setLeagueLogo(logo) {
      this.leagueLogo = logo;
    },
  },
}
</script>

<style>
  .bg-tabs {
    background-color: white !important;
  }
</style>