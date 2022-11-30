<template>
  <v-card class="pa-3" outlined>
    <v-row align="center">
      <v-col class="pa-0 ma-0 text-center" cols="2">
        <v-row v-for="(item, idx) in divisionLogoList" :key="idx">
          <v-col class="text-right" cols="6">
            <v-icon :color="backgroundColorList[idx]" size="x-large">
              mdi-square
            </v-icon>
          </v-col>
          <v-col class="text-left" cols="6">
            <v-img :src="item" width="30" />
          </v-col>
        </v-row>
      </v-col>
      <v-col class="pa-0 ma-0">
        <Line
          :chart-data="historyChartData()"
          :chart-options="historyChartOptions()"
          :height="100" />
      </v-col>
    </v-row>
    <!-- <Line
      :chart-data="historyChartData()"
      :chart-options="historyChartOptions()"
      :height="100" /> -->
  </v-card>
</template>

<script>
import { leagueStore } from "@/store/leagueStore";

// eslint-disable-next-line no-unused-vars
import { getHistoryAPI, getHistoryChartLeagueLogoAPI } from "@/api/teams";

// dayjs Library
import dayjs from "dayjs";
import "dayjs/locale/ko";
dayjs.locale("ko");

// chart.js + vue-chartjs
import { Line } from "vue-chartjs";
import { Chart, registerables, Tooltip } from "chart.js";
import ChartDataLabels from 'chartjs-plugin-datalabels';
Chart.register(...registerables);
Chart.register(ChartDataLabels);

Tooltip.positioners.centerPositioner = function (items, eventPosition) {
  // const tooltip = this;
  const xPos = Tooltip.positioners.average(items);

  if (xPos === false) {
    return false;
  }

  return {
    x: xPos.x,
    y: eventPosition.y,
    xAlign: "center", // left, center, right
    yAlign: "top", // top, center, bottom
  };
};

const arbitraryLine = {
  id: "arbitraryLine",
  beforeDraw: chart => {

    if (chart.tooltip._active && chart.tooltip._active.length) {
      const ctx = chart.ctx;
      ctx.save();

      const activePoint = chart.tooltip._active[0];

      ctx.beginPath();
      ctx.setLineDash([5, 7]);
      ctx.moveTo(activePoint.element.x, chart.chartArea.top);
      ctx.lineTo(activePoint.element.x, activePoint.element.y);
      ctx.lineWidth = 2;
      ctx.strokeStyle = "gray";
      ctx.stroke();
      ctx.restore();

      ctx.beginPath();
      ctx.moveTo(activePoint.element.x, activePoint.element.y);
      ctx.lineTo(activePoint.element.x, chart.chartArea.bottom);
      ctx.lineWidth = 2;
      ctx.strokeStyle = "blue";
      ctx.stroke();
      ctx.restore();
    }
  }
}
Chart.register(arbitraryLine);

const canvasBackgroundColor = {
  id: "canvasBackgroundColor",
  // eslint-disable-next-line no-unused-vars
  beforeDraw(chart, args, pluginOptions) {
    // console.log(chart);
    // console.log(args);
    // console.log(pluginOptions);
    // eslint-disable-next-line no-unused-vars
    const { ctx, chartArea: { top, bottom, left, right, width }, scales: { x, y }, } = chart;

    ctx.save();

    // bgColors(0, 9.5, "rgba(255, 26, 104, 0.2)");
    // bgColors(9.5, 20, "rgba(75, 192, 192, 0.2)");
    // bgColors(0, 4, "rgba(255, 206, 86, 0.2)");
    for (var i = 0; i < pluginOptions.bgColorList.length; i++) {
      bgColors(
        Object.values(pluginOptions.colorObj[i])[0].min,
        Object.values(pluginOptions.colorObj[i])[0].max,
        pluginOptions.bgColorList[i]
      );
    }

    function bgColors(bracketLow, bracketHigh, color) {
      ctx.fillStyle = color;
      ctx.fillRect(
        left,
        y.getPixelForValue(bracketHigh),
        width,
        y.getPixelForValue(bracketLow) - y.getPixelForValue(bracketHigh)
      );
      ctx.restore();
    }
  }
}
Chart.register(canvasBackgroundColor);

export default {
  components: {
    Line,
  },
  data: () => ({
    historyLabels: [],
    historyRankList: [],
    historyDataLabels: [],
    historyDivision: [],
    chartRankList: [],
    chartBGColorObj: {},
    backgroundColorList: [
      "rgba(102, 204, 145, 0.2)", // Light Green
      "rgba(102, 153, 204, 0.2)", // Light Blue
      "rgba(247, 247, 110, 0.2)", // Light Yellow
      "rgba(219, 148, 148, 0.2)", // Light Red
      "rgba(128, 128, 128, 0.2)", // Gray
      "rgba(154, 103, 249, 0.2)", // Light Purple
      "rgba(35, 3, 94, 0.2)", // Navy
      "rgba(7, 154, 207, 0.2)", // Skyblue
      "rgba(240, 156, 0, 0.2)", // Orange
      "rgba(243, 98, 195, 0.2)", // Pink
    ],
    divisionLogoList: [],
    leagueSizeList: [],
    chartSize: null,
  }),
  mounted() {
    this.getHistory();
  },
  methods: {
    getHistory() {
      return new Promise(() => {
        let items = this.getHistoryFromAPI().then(
          response => {
            items = response;
            leagueStore().setCurLeague(this.$route.params.country);
            console.log("items : ");
            console.log(items);

            setTimeout(() => {
              this.historyLabels = Object.keys(items).map((v) => {
                return v.substring(2) + "/" + (parseInt(v.substring(2)) + 1).toString()
              });

              this.historyRankList = Object.values(items).map((v) => {
                return v.standings[0][0].rank;
              });

              this.historyDataLabels = Object.values(items).map((v) => {
                return {
                  "leagueName": v.name,
                  "win": v.standings[0][0].all.win,
                  "draw": v.standings[0][0].all.draw,
                  "lose": v.standings[0][0].all.lose,
                  "points": v.standings[0][0].points,
                };
              });

              // 역대 소속되었던 리그ID 리스트
              this.historyDivision = Object.values(items).map((v) => {
                return v.id
              });

              // 역대 소속되었던 리그ID 리스트 (중복제거)
              let divisionIdList = [...new Set(this.historyDivision)];

              this.leagueSizeList = Object.values(items).map((v) => {
                return v.size
              });
              this.chartSize = [...new Set(this.leagueSizeList)].reduce((acc, cur) => acc + cur) + [...new Set(this.leagueSizeList)].length;
              console.log("reduce : ");
              console.log([...new Set(this.leagueSizeList)].reduce((acc, cur) => acc + cur) + [...new Set(this.leagueSizeList)].length);

              let rankUpdate = []; // 상위/하부 리그에 따라 차트 데이터 실제값 변화
              let divisionList = []; // 역대 소속되었던 리그 인덱스(leagueStore)
              for (var i = 0; i < this.historyDivision.length; i++) {
                for (var j = 0; j < leagueStore().getLeaguesOfCountry.length; j++) {
                  for (var k = 0; k < leagueStore().getLeaguesOfCountry[j].length; k++) {
                    if (this.historyDivision[i] == leagueStore().getLeaguesOfCountry[j][k]) {
                      // rankUpdate.push((this.historyRankList[i] + (j * 20)) / 2);
                      rankUpdate.push((this.historyRankList[i] + (j * this.leagueSizeList[i])) / 2);
                      divisionList.push(j);
                    }
                  }
                }
              }
              console.log("rankUpdate : ");
              console.log(rankUpdate);
              this.chartRankList = rankUpdate;
              rankUpdate = [];

              if ([...new Set(divisionList)].length > 1) {
                var sortedDivisionList = [...new Set(divisionList)].sort((a, b) => {
                  if(a > b) return 1;
                  if(a === b) return 0;
                  if(a < b) return -1;
                });
                
                for (i = 0; i < sortedDivisionList.length; i++) {
                  this.chartBGColorObj[i] = {
                    [i]: {
                      min: 0,
                      max: 0,
                    }
                  }
                  
                  let divisionIdx;
                  for (k = 0; k < divisionIdList.length; k++) {
                    if (leagueStore().getLeaguesOfCountry[sortedDivisionList[i]].includes(divisionIdList[k])) {
                      divisionIdx = leagueStore().getLeaguesOfCountry[sortedDivisionList[i]].indexOf(divisionIdList[k]);
                    }
                  }
                  this.getHistoryChartLeagueLogo(sortedDivisionList[i], divisionIdx);

                  for (j = 0; j < divisionList.length; j++) {
                    if (sortedDivisionList[i] == divisionList[j]) {
                      if (Object.values(this.chartBGColorObj[i])[0].min == 0) {
                        Object.values(this.chartBGColorObj[i])[0].min = this.chartRankList[j];
                        Object.values(this.chartBGColorObj[i])[0].max = this.chartRankList[j];
                      } else {
                        if (Object.values(this.chartBGColorObj[i])[0].min > this.chartRankList[j]) {
                          Object.values(this.chartBGColorObj[i])[0].min = this.chartRankList[j];
                        }

                        if (Object.values(this.chartBGColorObj[i])[0].max < this.chartRankList[j]) {
                          Object.values(this.chartBGColorObj[i])[0].max = this.chartRankList[j];
                        }
                      }
                    }
                  }

                  if (Object.values(this.chartBGColorObj[i])[0].min == Object.values(this.chartBGColorObj[i])[0].max) {
                    Object.values(this.chartBGColorObj[i])[0].max += 1;
                  }
                }

                if (Object.values(this.chartBGColorObj[0])[0].min != 0) {
                  Object.values(this.chartBGColorObj[0])[0].min = 0;
                }
              } else {
                this.chartBGColorObj[0] = {
                  0: {
                    min: 0,
                    max: 0,
                  }
                }

                let divisionIdx;
                if (leagueStore().getLeaguesOfCountry[divisionList[0]].includes(divisionIdList[0])) {
                  divisionIdx = leagueStore().getLeaguesOfCountry[divisionList[0]].indexOf(divisionIdList[0]);
                }
                this.getHistoryChartLeagueLogo(divisionList[0], divisionIdx);
                  
                for (i = 0; i < divisionList.length; i++) {
                  if (Object.values(this.chartBGColorObj[0])[0].min == 0) {
                    Object.values(this.chartBGColorObj[0])[0].min = this.chartRankList[i];
                    Object.values(this.chartBGColorObj[0])[0].max = this.chartRankList[i];
                  } else {
                    if (Object.values(this.chartBGColorObj[0])[0].min > this.chartRankList[i]) {
                      Object.values(this.chartBGColorObj[0])[0].min = this.chartRankList[i];
                    }
                    
                    if (Object.values(this.chartBGColorObj[0])[0].max < this.chartRankList[i]) {
                      Object.values(this.chartBGColorObj[0])[0].max = this.chartRankList[i];
                    }
                  }
                }
                Object.values(this.chartBGColorObj[0])[0].min = 0;
              }
            }, 1000);
          }
        )
      })
    },
    async getHistoryFromAPI() {
      return getHistoryAPI({
        params: {
          season: dayjs().format("YYYY"),
          country: this.$route.params.country,
          teamId: this.$route.params.teamId,
        },
      })
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    getHistoryChartLeagueLogo(idx1, idx2) {
      return new Promise(() => {
        this.getHistoryChartLeagueLogoFromAPI(idx1, idx2).then(
          response => {
            this.divisionLogoList.push(response);
          }
        )
      })
    },
    async getHistoryChartLeagueLogoFromAPI(idx1, idx2) {
      return getHistoryChartLeagueLogoAPI({
        params: {
          id: leagueStore().getLeaguesOfCountry[idx1][idx2],
        },
      })
        .then(res => {
          return res.data;
        })
        .catch(e => {
          console.log(e);
        })
    },
    historyChartData() {
      return {
        labels: this.historyLabels,
        datasets: [
          {
            label: "순위",
            // data: this.historyRankList,
            data: this.chartRankList,
            fill: false,
            tension: 0.1,
            borderColor: "rgb(0, 128, 0)",
            backgroundColor: "white",
            pointStyle: "rectRounded",
            pointRadius: 12,
            hoverRadius: 12,
            pointBorderColor: "rgb(156, 204, 101)",
          },
        ],
      }
    },
    historyChartOptions() {
      return {
        animation: false,
        responsive: true,
        scales: {
          x: {
            title: {
              display: true,
              text: "시즌",
            },
            grid: {
              display: false,
            },
          },
          y: {
            title: {
              display: true,
              text: "순위",
            },
            min: -2,
            // max: this.leagueSize + 3,
            // max: [...new Set(this.historyDivision)].length * 10 + 3,
            max: this.chartSize,
            ticks: {
              stepSize: 1,
            },
            reverse: true,
            display: false,
          },
        },
        plugins: {
          title: {
            display: true,
            text: "구단 역대 순위",
          },
          legend: {
            display: false,
          },
          datalabels: {
            color: "green",
            font: {
              size: "12",
              weight: "bold",
            },
            formatter: (value, context) => {
              return this.historyRankList[context.dataIndex];
            }
          },
          tooltip: {
            displayColors: false,
            callbacks: {
              label: (context) => {
                return this.historyRankList[context.dataIndex] + "위";
              },
              footer: (v) => {
                let data = this.historyDataLabels[v[0].dataIndex];
                return data.leagueName + "\n" +
                  "승 : " + data.win + "\n" +
                  "무 : " + data.draw + "\n" +
                  "패 : " + data.lose + "\n" +
                  "승점 : " + data.points
              },
            },
            position: "centerPositioner",
          },
          arbitraryLine: true,
          canvasBackgroundColor: {
            colorObj: this.chartBGColorObj,
            bgColorList: this.backgroundColorList.slice(0, Object.keys(this.chartBGColorObj).length),
          },
        },
        // onHover: (evt) => {
        //   console.log(evt);
        // },
        interaction: {
          mode: "index",
          intersect: false,
        },
      }
    },
  },
}
</script>

<style>
.logo {
  margin-right: 10px;
  width: 20px;
  height: 20px;
}
</style>