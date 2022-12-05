<template>
  <div class="text-center">
    <v-container>
      <v-row justify="space-between">
        <v-col cols="3">
          <Button
            :disabled="prevBtnDisabled()"
            class="ma-2"
            @click="decreaseMonth"
            icon
            color="light-green lighten-1"
            iconName="mdi-chevron-double-left">
          </Button>
          <Button
            :disabled="prevBtnDisabled()"
            class="ma-2"
            @click="decreaseDay"
            icon
            color="light-green lighten-1"
            iconName="mdi-chevron-left">
          </Button>
        </v-col>
        <v-col cols="2">
          <DatePicker v-model="selectedDate" :available-dates="validFixtures">
            <template v-slot="{ togglePopover }">
              <div @click="togglePopover()" style="cursor: pointer">
                <h1>{{ fixturesYear }}</h1>
                <h4>{{ fixturesMonth }}월 {{fixturesDay}}일</h4>
              </div>
            </template>
          </DatePicker>
        </v-col>
        <v-col cols="3">
          <Button
            :disabled="nextBtnDisabled()"
            class="ma-2"
            @click="increaseDay"
            icon
            color="light-green lighten-1"
            iconName="mdi-chevron-right">
          </Button>
          <Button
            :disabled="nextBtnDisabled()"
            class="ma-2"
            @click="increaseMonth"
            icon
            color="light-green lighten-1"
            iconName="mdi-chevron-double-right">
          </Button>
        </v-col>
      </v-row>
    </v-container>
  </div>
  <v-divider />
  <v-card-text>
    <v-row justify="center">
      <v-col cols="8">
        <div
          class="pa-3 text-center"
          v-for="(data, key) in fixturesData.filter(fixturesFilter)"
          :key="key">
          
          <v-row justify="center" align="center">
            <v-col>
              <div class="home-wrapper" @click="pushTeamData(data.teams.home.id)">
                <v-img class="logo" :src="data.teams.home.logo" />
                <b class="name">{{ data.teams.home.name }}</b>
              </div>
            </v-col>
            <v-col cols="4">
              <div
                class="pa-3 text-center"
                v-if="data.fixture.status.short === 'FT'">

                <h1>{{ data.goals.home }} - {{ data.goals.away }}</h1>
                <div style="padding-top: 10px;">
                  <a>{{ timestampToTime(data.fixture.timestamp) }}</a>
                </div>
              </div>
              <div
                class="pa-3 text-center"
                v-else>
                
                <h2>{{ timestampToTime(data.fixture.timestamp) }}</h2>
              </div>
            </v-col>
            <v-col>
              <div class="away-wrapper" @click="pushTeamData(data.teams.home.id)">
                <v-img class="logo" :src="data.teams.away.logo" />
                <b class="name">{{ data.teams.away.name }}</b>
              </div>
            </v-col>
          </v-row>
          <br>
          <v-divider />
        </div>
      </v-col>
    </v-row>
  </v-card-text>
</template>

<script>
import { getFixturesAPI } from "@/api/competitions";

// dayjs Library
import dayjs from "dayjs";
import "dayjs/locale/ko";
dayjs.locale("ko");

// V-Calendar Component
import { DatePicker } from "v-calendar";
import "v-calendar/dist/style.css";

export default {
  components: {
    DatePicker,
  },
  data: () => ({
    selectedDate: dayjs().format("YYYY-MM-DD"),
    fixturesYear: "",
    fixturesMonth: "",
    fixturesDay: "",
    fixturesData: [],
    validFixtures: [],
    availableDates: [],
    defaultFixtureIndex: null,
  }),
  mounted() {
    this.getFixturesList();
  },
  watch: {
    selectedDate() {
      this.fixturesYear = dayjs(this.selectedDate).format("YYYY");
      this.fixturesMonth = dayjs(this.selectedDate).format("MM");
      this.fixturesDay = dayjs(this.selectedDate).format("DD");
    },
  },
  methods: {
    getFixturesList() {
      this.fixturesYear = dayjs(this.selectedDate).format("YYYY");
      this.fixturesMonth = dayjs(this.selectedDate).format("MM");
      this.fixturesDay = dayjs(this.selectedDate).format("DD");

      // eslint-disable-next-line no-unused-vars
      return new Promise((resolve, reject) => {
        let items = this.getFxituresListFromAPI().then(
          response => {
            items = response;

            setTimeout(() => {
              this.fixturesData = items.sort((a, b) => { // sort ASC
                return a.fixture.timestamp - b.fixture.timestamp;
              });

              this.validFixtures = [...new Set(this.fixturesData.map(
                v => dayjs.unix(v.fixture.timestamp).format("YYYY-MM-DD")
              ))];

              this.selectedDate = this.defaultDate();
              this.availableDates = this.pushAvailableDates();
            }, 1000) // 1000 (ms)
          }
        )
      })
    },
    async getFxituresListFromAPI() {
      return getFixturesAPI({
        params: this.$route.params,
      })
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    defaultDate() {
      for (var i = 0; i < this.validFixtures.length; i++) {
        if (dayjs(this.selectedDate).format("YYYY-MM-DD") === dayjs(this.validFixtures[i]).format("YYYY-MM-DD")) {
          this.defaultFixtureIndex = i;
          return this.validFixtures[i];
        } else if (dayjs(this.selectedDate).format("YYYY-MM-DD") < dayjs(this.validFixtures[i]).format("YYYY-MM-DD")) {
          this.defaultFixtureIndex = i;
          return this.validFixtures[i];
        }
      }
    },
    pushAvailableDates() {
      for (var i = 0; i < this.validFixtures.length - 1; i++) {
        this.availableDates.push({
          start: this.validFixtures[i],
          end: this.validFixtures[i + 1],
        });
      }
    },
    prevBtnDisabled() {
      if (this.defaultFixtureIndex === 0) {
        return true;
      } else {
        return false;
      }
    },
    nextBtnDisabled() {
      if (this.defaultFixtureIndex === this.validFixtures.length - 1) {
        return true;
      } else {
        return false;
      }
    },
    decreaseDay() {
      this.defaultFixtureIndex--;
      this.selectedDate = dayjs(this.validFixtures[this.defaultFixtureIndex]).format("YYYY-MM-DD");
    },
    increaseDay() {
      this.defaultFixtureIndex++;
      this.selectedDate = dayjs(this.validFixtures[this.defaultFixtureIndex]).format("YYYY-MM-DD");
    },
    decreaseMonth() {
      this.defaultFixtureIndex = 0;
      this.selectedDate = dayjs(this.validFixtures[this.defaultFixtureIndex]).format("YYYY-MM-DD");

    },
    increaseMonth() {
      this.defaultFixtureIndex = this.validFixtures.length - 1;
      this.selectedDate = dayjs(this.validFixtures[this.defaultFixtureIndex]).format("YYYY-MM-DD");
    },
    fixturesFilter(v) {
      return dayjs(this.selectedDate).format("YYYY-MM-DD") === dayjs.unix(v.fixture.timestamp).format("YYYY-MM-DD");
    },
    timestampToTime(timestamp) {
      return dayjs.unix(timestamp).format("H:mm");
    },
    pushTeamData(teamId) {
      this.$paramsTeam({
        country: this.$route.params.country,
        teamId: teamId,
        leagueId: this.$route.params.id,
      });
      this.movePage("/teams" + "/" + this.$route.params.country + "/" + this.$route.params.id + "/" + teamId);
    },
  },
}
</script>

<style>
.home-wrapper {
  /* padding: 5px; */
  /* display: flex; */
  align-items: center;
  justify-items: center;
  cursor: pointer;
}

.home-wrapper .logo {
  margin-right: 50px;
  display: inline-block;
  width: 40px;
  /* height: 40px; */
  /* border-radius: 50%; */
  object-fit: cover;
  vertical-align: middle;
  /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
}

.home-wrapper .name {
  font-size: 20px;
  text-decoration: none;
  color: green;
}

.away-wrapper {
  /* padding: 5px; */
  /* display: flex; */
  align-items: center;
  justify-items: center;
  cursor: pointer;
}

.away-wrapper .logo {
  margin-right: 50px;
  display: inline-block;
  width: 40px;
  /* height: 40px; */
  /* border-radius: 50%; */
  object-fit: cover;
  vertical-align: middle;
  /* box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%); */
}

.away-wrapper .name {
  font-size: 20px;
  text-decoration: none;
  color: green;
}
</style>