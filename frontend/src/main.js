import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import { loadFonts } from "./plugins/webfontloader";
import { createPinia } from "pinia";

// Code Mirror Editor
import { GlobalCmComponent } from "codemirror-editor-vue3";

// Vue 3 Easy Data Table
import Vue3EasyDataTable from "vue3-easy-data-table";
import "vue3-easy-data-table/dist/style.css";

// Plugins
import page from "@/plugins/page";
import routerPath from "@/plugins/routerPath";

import Button from "@/components/common/Button";

import { SetupCalendar } from "v-calendar";

const app = createApp(App);

loadFonts();

app
  .use(router)
  .use(store)
  .use(vuetify)
  .use(createPinia())
  .use(page)
  .use(routerPath)
  .use(GlobalCmComponent)
  .use(SetupCalendar, {})
  .component("Button", Button)
  .component("EasyDataTable", Vue3EasyDataTable)
  .mount("#app");

app.config.globalProperties.$router = router;
