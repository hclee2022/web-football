<template>
  <v-card class="pa-3" outlined>
    <Line
      :chart-data="historyChartData()"
      :chart-options="historyChartOptions()"
      :height="100" />
  </v-card>
</template>

<script>
import { leagueStore } from "@/store/leagueStore";
import { getHistoryAPI } from "@/api/teams";

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

            setTimeout(() => {
              this.historyLabels = Object.keys(items).map((v) => {
                return v.substring(2) + "/" + (parseInt(v.substring(2)) + 1).toString()
              });

              this.historyData = Object.values(items).map((v) => {
                return v.rank;
              });

              this.historyDataLabels = Object.values(items).map((v) => {
                return {
                  "win": v.all.win,
                  "draw": v.all.draw,
                  "lose": v.all.lose,
                  "points": v.points,
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
          leagueId: this.$route.params.leagueId,
          sesson: leagueStore().$state.sesson.S22,
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
                return "승 : " + data.win + "\n" +
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