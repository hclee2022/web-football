<template>
  <v-card class="pa-3" outlined>
    <Line
      :chart-data="historyChartData()"
      :chart-options="historyChartOptions()"
      :height="100" />
  </v-card>
</template>

<script>
import { getHistoryAPI } from "@/api/teams";

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

export default {
  components: {
    Line,
  },
  props: {
    leagueSize: Number,
  },
  data: () => ({
    // History
    historyLabels: [],
    historyData: [],
    historyDataLabels: [],
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
            console.log("history Items");
            console.log(items);

            setTimeout(() => {
              this.historyLabels = Object.keys(items).map((v) => {
                return v.substring(2) + "/" + (parseInt(v.substring(2)) + 1).toString()
              });

              this.historyData = Object.values(items).map((v) => {
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
            }, 1000);
          }
        )
      })
    },
    async getHistoryFromAPI() {
      return getHistoryAPI({
        params: {
          sesson: dayjs().format("YYYY"),
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
    historyChartData() {
      return {
        labels: this.historyLabels,
        datasets: [
          {
            label: "순위",
            data: this.historyData,
            fill: false,
            tension: 0.1,
            borderColor: "rgb(0, 128, 0)",
            backgroundColor: "white",
            pointStyle: "rectRounded",
            pointRadius: 20,
            hoverRadius: 20,
            pointBorderColor: "rgb(156, 204, 101)",
          },
        ],
      }
    },
    historyChartOptions() {
      return {
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
            max: this.leagueSize + 3,
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
              size: "20",
              weight: "bold",
            },
          },
          tooltip: {
            callbacks: {
              footer: (v) => {
                let data = this.historyDataLabels[v[0].dataIndex];
                console.log("data : ");
                console.log(data);
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

</style>