<template>
  <v-app>
    <!-- <v-app-bar app> -->
      <AppHeader />
    <!-- </v-app-bar> -->
    <v-main>
      <router-view />
    </v-main>
    <Snackbar />
  </v-app>
</template>

<script>
import AppHeader from "@/components/common/AppHeader.vue";
import Snackbar from "./components/common/Snackbar";

export default {
  name: "App",
  components: {
    AppHeader,
    Snackbar,
  },
  data: () => ({
    //
  }),
};
</script>
