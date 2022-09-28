// Vuex
// import { createStore } from "vuex";
// 
// export default createStore({
//   state: {},
//   getters: {},
//   mutations: {},
//   actions: {},
//   modules: {},
// });


// Pinia
import { defineStore } from "pinia";
// import { ref, computed } from "vue";

export const SET_SNACKBAR = "SET_SNACKBAR"

export const store = defineStore("store", {
  state: () => ({
    sb: {
      show: false,
      msg: "",
      color: "",
    },
  }),
  actions: {
    [SET_SNACKBAR](state, sb) {
      state.sb = sb;
    },
  },
})