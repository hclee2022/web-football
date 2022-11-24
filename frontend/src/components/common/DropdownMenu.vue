<template>
  <div class="text-center">
    <v-menu
      :close-on-content-click="false"
      open-on-hover>

      <template v-slot:activator="{ props }">
        <v-btn
          v-bind="props">

          <b>{{ menuTitle }}</b>
        </v-btn>
      </template>
      

      <v-list>
        <template
          v-for="(item, index) in listItems"
          :key="index">
          
          <v-list-item
            v-if="!item.isGroup"
            :title="item.title"
            :value="item"
            @click="movePage(item.to)">
          </v-list-item>

          <v-list-group
            v-else
            active-color="green"
            :value="item.title">

            <template v-slot:activator="{ props }">
              <v-list-item
                v-bind="props"
                :title="item.title">
              </v-list-item>
            </template>

            <v-list-item
              v-for="(submenu, i) in item.submenu"
              :key="i"
              :title="submenu.title"
              :value="submenu"
              @click="clickCompetition(submenu.to, submenu.params)">
            </v-list-item>
          </v-list-group>
          
        </template>
      </v-list>
    </v-menu>
  </div>
</template>

<script>
export default {
  props: {
    menuTitle: String,
    listItems: Array,
  },
  data: () => ({

  }),
  methods: {
    clickCompetition(to, params) {
      this.$router.push({
        name: "Competitions",
        params: params,
      });
      this.movePage(to + "/" + params.id  + "/" + params.sesson);
    },
  },
}
</script>

<style>
  .bg-active {
    background-color: white;
  }
</style>