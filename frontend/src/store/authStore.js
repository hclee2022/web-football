// Pinia Example
import { defineStore } from "pinia";

export const useAuthStore = defineStore("AuthStore", {
  state: () => {
    return {
      authId: null,
      authUserUnsubscribe: null,
      authObserverUnsubscribe: null,
    };
  },
  getters: {},
  actions: {},
});
