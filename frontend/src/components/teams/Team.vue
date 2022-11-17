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
            <TeamStandings
              @leagueSizeFromChild="setLeagueSize"
              @teamNameFromChild="setTeamName"
              @teamLogoFromChild="setTeamLogo" />
          </v-col>
          <v-col cols="6">
            <TeamFixtures />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <TeamHistory
              :leagueSize="leagueSize" />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <TeamSquads />
          </v-col>
        </v-row>
      </div>
    </v-card>
  </v-container>
</template>

<script>
import TeamFixtures from "@/components/teams/TeamFixtures";
import TeamStandings from "@/components/teams/TeamStandings";
import TeamHistory from "@/components/teams/TeamHistory";
import TeamSquads from "@/components/teams/TeamSquads";

export default {
  components: {
    TeamFixtures,
    TeamStandings,
    TeamHistory,
    TeamSquads,
  },
  data: () => ({
    leagueSize: null,
    teamName: "",
    teamLogo: "",
  }),
  mounted() {},
  methods: {
    setLeagueSize(size) {
      this.leagueSize = size;
    },
    setTeamName(name) {
      this.teamName = name;
    },
    setTeamLogo(logo) {
      this.teamLogo = logo;
    },
  },
}
</script>

<style>

</style>