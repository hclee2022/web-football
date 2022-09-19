<template>
  <v-app>
    <!-- <v-app-bar app> -->
      <AppHeader />
    <!-- </v-app-bar> -->
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import AppHeader from "@/components/common/AppHeader.vue";

export default {
  name: "App",
  components: {
    AppHeader,
  },
  data: () => ({
    //
  }),
};
</script>
