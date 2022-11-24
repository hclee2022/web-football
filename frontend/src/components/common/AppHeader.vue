<template>
  <v-app-bar app color="light-green lighten-1" dark>
    <v-btn @click="movePage('/home')" text>
      <span class="mr-2">
        <b>Home</b>
      </span>
    </v-btn>
    <v-btn @click="movePage('/list')" text>
      <span class="mr-2">
        <b>게시판</b>
      </span>
    </v-btn>
    <!-- <DropdownMenu menuTitle="Competition" :listItems="menuListCompetition" /> -->
    <MenuCountry menuTitle="대회" />
    <DropdownMenu menuTitle="이적" :listItems="menuListTransfers" />
    <v-spacer></v-spacer>
    <Clock />
		<v-divider class="mx-4" inset vertical></v-divider>
		<Weather class="mr-3" />
  </v-app-bar>
</template>

<script>
import Clock from "@/components/common/Clock"
import Weather from "@/components/common/Weather"
import DropdownMenu from "@/components/common/DropdownMenu";
import MenuCountry from "@/components/common/MenuCountry";

// import { leagueStore } from "@/store/leagueStore";

export default {
  name: "AppHeader",
  components: {
    Clock,
    Weather,
    DropdownMenu,
    MenuCountry,
  },
  data: () => ({
    // // Lagacy
    // menuListCompetition: [
    //   {
    //     title: "Leagues Europe",
    //     to: "",
    //     isGroup: true,
    //     submenu: [
    //       {
    //         title: "England",
    //         to: "/competitions",
    //         params: {
    //           "id": leagueStore().$state.leagueId.EPL,
    //           "sesson": leagueStore().$state.sesson.S22,
    //         },
    //       },
    //       {
    //         title: "Spain",
    //         to: "/competitions",
    //         params: {
    //           "id": leagueStore().$state.leagueId.La_Liga,
    //           "sesson": leagueStore().$state.sesson.S22,
    //         },
    //       },
    //     ],
    //   },
    //   {
    //     title: "Leagues Asia",
    //     to: "/list",
    //     isGroup: true,
    //     submenu: [
    //       {
    //         title: "South Korea",
    //         to: "/competitions",
    //         params: {
    //           "id": leagueStore().$state.leagueId.K1,
    //           "sesson": leagueStore().$state.sesson.S22,
    //         },
    //       }
    //     ],
    //   },
    // ],
    menuListTransfers: [
      {
        title: "Lastest Tranfers",
        to: "/list",
        isGroup: false,
        submenu: [{}],
      },
      {
        title: "Top Transfers",
        to: "/list",
        isGroup: false,
        submenu: [[]],
      },
    ],
  }),
}
</script>

<style>

</style>