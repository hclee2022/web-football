<template>
  <v-container>
    <v-card elevation="10" outlined width="100%" class="mx-auto">
      <v-card-title>
        Board
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col align-self="end" cols="12" md="2">
            <v-select
              v-model="schType"
              label="검색 조건"
              :items="conditions">
            </v-select>
          </v-col>
          <v-col cols="12" md="8">
            <v-text-field
              v-model="schVal"
              label="검색어"
              single-line
              @keypress.enter.prevent="getBoardList">
            </v-text-field>
          </v-col>
          <v-col align-self="center">
            <Button
              @click="getBoardList"
              color="light-green lighten-1"
              rounded
              block
              iconName="mdi-magnify"
              btnName="Search">
            </Button>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <EasyDataTable
              style="cursor: pointer;"
              v-model:options="options"
              @click-row="onClickRow"
              :headers="headers"
              :items="document"
              :server-items-length="totalCount"
              :loading="loading"
              :rows-per-page="5"
              hide-rows-per-page
              buttons-pagination>

              <template v-slot:items="props" >
                <td>{{ props.item.docNo }}</td>
                <td>{{ props.item.title }}</td>
                <td>{{ props.item.writer }}</td>
                <td>{{ props.item.regDttm }}</td>
                <td>{{ props.item.view }}</td>
                <td>{{ props.item.reply }}</td>
              </template>
            </EasyDataTable>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
    <v-container>
      <v-fab-transition>
        <v-row>
          <v-col>
            <Button
              @click="movePage('/write')"
              color="light-green lighten-1"
              icon
              position="fixed"
              iconName="mdi-pencil">
            </Button>
          </v-col>
        </v-row>
      </v-fab-transition>
    </v-container>
  </v-container>
</template>

<script>
import { getBoardListAPI } from "@/api/index";

export default {
  components: {
    // eslint-disable-next-line vue/no-unused-components
    EasyDataTable: window["vue3-easy-data-table"],
  },
  data() {
    return {
      headers: [
        { text: "글 번호", value: "docNo" },
        { text: "제목", value: "title" },
        { text: "작성자", value: "writer" },
        { text: "작성 일시", value: "regDttm" },
        { text: "조회 수", value: "view" },
        { text: "댓글 수", value: "reply" },
      ],
      document: [],
      options: {
        multiSort: true,
        sortBy: [],
        sortDesc: [],
      },
      totalCount: 0,
      loading: false,
      conditions: [
        "글 번호",
        "제목",
        "작성자",
      ],
      schType: "",
      schVal: "",
    }
  },
  mounted() {
    this.getBoardList();
  },
  watch: {
    options: {
      handler() {
        this.getBoardList();
      },
      deep: true,
    },
  },
  methods: {
    getBoardDataFromAPI(sort) {
      var paramSchType;
      switch (this.schType) {
        case this.conditions[0]:
          paramSchType = "docNo";
          break;
        case this.conditions[1]:
          paramSchType = "title";
          break;
        case this.conditions[2]:
          paramSchType = "writer";
          break;
      }
      
      return getBoardListAPI({
        params: {
          schType: paramSchType,
          schVal: this.schVal,
          sort: encodeURIComponent(sort),
        },
      })
        .then(response => {
          return response.data;
        })
        .catch(error => {
          console.log(error);
        })
    },
    getBoardList() {
      const me = this;
      this.loading = true;

      // eslint-disable-next-line no-unused-vars
      return new Promise((resolve, reject) => {
        const { sortBy, sortDesc } = this.options;
        let sort = [];

        if (sortBy.length > 0) {
          sortBy.forEach((value, index) => {
            sort.push(
              value
                .replace(/[A-Z]/g, function (str) {
                  return "_" + str[0]
                })
                .toUpperCase() + " " + (sortDesc[index] ? "desc" : "asc"),
            )
          })
        } else {
          sort.push("DOC_NO desc");
        }

        let items = this.getBoardDataFromAPI(sort).then(
          response => {
            items = response.data;
            const total = response.total;

            setTimeout(() => {
              me.loading = false;
              this.document = items;
              this.totalCount = total;
            }, 1000);
          },
        )
      })
    },
    onClickRow(data) {
      this.movePage("/detail?docNo=" + data.docNo);
    },
  },
}
</script>

<style>

</style>