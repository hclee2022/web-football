<template>
  <v-app-bar app color="light-green lighten-1" dark>
    <v-btn @click="movePage('/home')" text>
      <span class="mr-2">
        Home
      </span>
    </v-btn>
    <v-btn @click="movePage('/list')" text>
      <span class="mr-2">
        Board
      </span>
    </v-btn>
    <DropdownMenu menuTitle="Competition" :listItems="menuListCompetition" />
    <DropdownMenu menuTitle="Transfers" :listItems="menuListTransfers" />
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

export default {
  name: "AppHeader",
  components: {
    Clock,
    Weather,
    DropdownMenu,
  },
  data: () => ({
    menuListCompetition: [
      {
        title: "Leagues Europe",
        to: "",
        isGroup: true,
        submenu: [
          {
            title: "England",
            to: "/competition",
          }
        ],
      },
      {
        title: "Leagues Asia",
        to: "/list",
        isGroup: false,
        submenu: [[]],
      },
    ],
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