import { createRouter, createWebHistory } from "vue-router";
// import HomeView from "../views/HomeView.vue";
import Home from "@/views/Home.vue";

const routes = [
  {
    path: "/",
    name: "Main",
    redirect: "/home",
  },
  // {
  //   path: "*",
  //   name: "PageNotFound",
  //   component: () => import("@/views/error/404.vue"),
  // },
  {
    path: "/home",
    name: "home",
    component: Home,
  },
  {
    path: "/list",
    name: "BoardList",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import("@/views/board/ListView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
