import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import { loadFonts } from "./plugins/webfontloader";
import { createPinia } from "pinia";

import page from "@/plugins/page";

import Button from "@/components/common/Button";

const app = createApp(App);

loadFonts();

app
  .use(router)
  .use(store)
  .use(vuetify)
  .use(createPinia())
  .use(page)
  .component("Button", Button)
  .mount("#app");

app.config.globalProperties.$router = router;
