<template>
  <v-card class="pa-3" outlined>
    <v-card-title class="text-center">
      <b>
        스쿼드
      </b>
    </v-card-title>
    <br>
    <div v-for="(posItem, posIdx) in positionList" :key="posIdx">
      <v-card class="pa-3">
        <v-row>
          <v-col cols="12">
            <div>
              <h3>{{ Object.values(posItem)[0] }}</h3>
            </div>
          </v-col>
          <v-col cols="4" v-for="(item, index) in getSquadsList(Object.keys(posItem)[0])" :key="index">
            <v-card class="pa-3" @click="pushPlayerData(item.id)">
              <v-row>
                <v-col cols="6">
                  <div>
                    <span>
                      No. {{ item.number }}
                      <h4>
                        {{ item.name }}
                      </h4>
                    </span>
                  </div>
                </v-col>
                <v-col cols="6">
                  <span>
                    <v-img class="logo" :src="item.photo" />
                  </span>
                </v-col>
              </v-row>
            </v-card>
          </v-col>
        </v-row>
      </v-card>
      <br>
    </div>
  </v-card>
</template>

<script>
import { getSquadsAPI } from "@/api/teams";

export default {
  data: () => ({
    squadsList: [],
    positionList: [
      { GK: "골키퍼" },
      { DF: "수비수" },
      { MF: "미드필더" },
      { AT: "공격수" },
    ],
  }),
  mounted() {
    this.getSquads();
  },
  methods: {
    getSquads() {
      return new Promise(() => {
        let items = this.getSquadsFromAPI().then(
          response => {
            items = response;
            this.squadsList = items;
          }
        )
      })
    },
    async getSquadsFromAPI() {
      return getSquadsAPI({
        params: {
          id: this.$route.params.teamId,
        },
      })
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    getSquadsList(key) {
      return this.squadsList[key];
    },
    pushPlayerData(data) {
      this.$router.push({
        name: "Player",
        params: {
          id: data,
        },
      });
      this.movePage("/players" + "/" + data);
    },
  },
}
</script>

<style>
.logo {
  margin-left: 10px;
  display: inline-block;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  vertical-align: middle;
  /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
}
</style>