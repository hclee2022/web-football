<template>
  <div class="text-center">
    <v-menu
      location="bottom"
      :close-on-content-click="false"
      open-on-hover>

      <template v-slot:activator="{ props }">
        <v-btn v-bind="props">
          <b>{{ menuTitle }}</b>
        </v-btn>
      </template>

      <v-card class="listUI" max-height="400" outlined>
        <v-list v-for="(headItem, headIdx) in alphabetList" :key="headIdx">
          <v-menu
            location="end"
            :close-on-content-click="false"
            open-on-hover>
          
            <template v-slot:activator="{ props }">
              <div class="text-center country-wrapper" v-bind="props">
                <b class="name">{{ headItem }}</b>
                <v-icon class="name">
                  mdi-chevron-right
                </v-icon>
              </div>
            </template>

            <v-list class="listUI">
              <v-list-group
                v-for="(countryItem, index) in getSortedCountries(headItem)"
                :key="index"
                :value="countryItem.name">

                <template v-slot:activator="{ props }">
                  <v-list-item v-bind="props">
                    <div class="country-wrapper">
                      <v-img class="flag" :src="countryItem.flag" />
                      <b class="name">{{ countryItem.name }}</b>
                    </div>
                  </v-list-item>
                </template>

                <v-list-item
                  v-for="(leagueItem, leagueIdx) in getLeagueList(countryItem.code, countryItem.name)"
                  :key="leagueIdx"
                  @click="clickCompetition('/competitions', leagueItem.league.id, leagueItem.seasons[0].year, countryItem.name)">

                  <div class="league-wrapper">
                    <v-img class="flag" :src="leagueItem.league.logo" />
                    <b class="name">{{ leagueItem.league.name }}</b>
                  </div>
                </v-list-item>
              </v-list-group>
            </v-list>

          </v-menu>
        </v-list>
      </v-card>
    </v-menu>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import { getCountriesAPI, getLeaguesAPI, getCupsAPI } from "@/api/common";
// import { leagueStore } from "@/store/leagueStore";

export default {
  props: {
    menuTitle: String,
  },
  data: () => ({
    // Countries
    sortedCountries: [],
    alphabetList: [],
    // Leagues
    leagueList: [],
    // Cups
  }),
  mounted() {
    this.getCountries();
    this.getLeagues();
  },
  methods: {
    getCountries() {
      return new Promise(() => {
        let items = this.getCountriesFromAPI().then(
          response => {
            items = response;

            setTimeout(() => {
              this.sortedCountries = items.sort((a, b) => {
                if (a.name.toLowerCase() > b.name.toLowerCase()) return 1;
                else if (a.name.toLowerCase() < b.name.toLowerCase()) return -1;
                else return 0;
              });

              this.alphabetList = [...new Set(this.sortedCountries.map((v) => {
                return v.name[0]
              }))];
            }, 1000);
          }
        )
      })
    },
    async getCountriesFromAPI() {
      return getCountriesAPI()
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    getSortedCountries(key) {
      return this.sortedCountries.filter((v) => {
        return v.name[0] === key;
      });
    },
    getLeagues() {
      return new Promise(() => {
        let items = this.getLeaguesFromAPI().then(
          response => {
            items = response;

            setTimeout(() => {
              this.leagueList = items;
              // console.log("this.leagueList : ");
              // console.log(this.leagueList);
            }, 1000);
          }
        )
      })
    },
    async getLeaguesFromAPI() {
      return getLeaguesAPI()
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    getLeagueList(code, name) {
      return this.leagueList.filter((v) => {
        return v.country.code === code && v.country.name === name;
      });
    },
    clickCompetition(to, id, season, country) {
      this.$router.push({
        name: "Competitions",
        params: {
          country: country,
          id: id,
          season: season,
        },
      });
      this.movePage(to + "/" + country + "/" + id  + "/" + season);
    },
  },
}
</script>

<style>

  .listUI {
      -ms-overflow-style: none; /* IE and Edge */
      scrollbar-width: none; /* Firefox */
  }

  .listUI::-webkit-scrollbar {
      display: none; /* Chrome, Safari, Opera*/
  }

  .country-wrapper {
    padding: 5px;
    /* display: flex; */
    align-items: center;
    justify-items: center;
    cursor: pointer;
  }

  .country-wrapper .flag {
    margin-right: 10px;
    display: inline-block;
    width: 28px;
    height: 21px;
    object-fit: cover;
    vertical-align: middle;
    border: solid #eaeaea;
  }

  .country-wrapper .name {
    text-decoration: none;
    color: green;
  }

  .league-wrapper {
    padding: 5px;
    /* display: flex; */
    align-items: center;
    justify-items: center;
    cursor: pointer;
  }

  .league-wrapper .flag {
    margin-right: 10px;
    display: inline-block;
    width: 20px;
    height: 20px;
    object-fit: cover;
    vertical-align: middle;
  }

  .league-wrapper .name {
    text-decoration: none;
    color: green;
  }

</style>