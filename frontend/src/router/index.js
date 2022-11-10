import { createRouter, createWebHistory } from "vue-router";
// import HomeView from "../views/HomeView.vue";
import Home from "@/views/Home.vue";

const routes = [
  {
    path: "/",
    name: "Main",
    redirect: "/home",
  },
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () =>
      import("@/views/error/PageNotFound.vue"),
  },
  {
    path: "/home",
    name: "home",
    component: Home,
  },
  {
    path: "/list",
    name: "BoardList",
    component: () =>
      import("@/views/board/ListView.vue"),
  },
  {
    path: "/write",
    name: "BoardWrite",
    component: () =>
      import("@/views/board/WriteView.vue"),
  },
  {
    path: "/detail",
    name: "BoardDetail",
    component: () =>
      import("@/views/board/DetailView.vue"),
  },
  {
    path: "/edit",
    name: "BoardEdit",
    component: () =>
      import("@/views/board/EditView.vue"),
  },
  {
    path: "/competitions/:id/:sesson",
    name: "Competitions",
    component: () =>
      import("@/views/competitions/CompetitionsView.vue"),
    props: true,
  },
  {
    path: "/teams/:leagueId/:teamId",
    name: "Team",
    component: () =>
      import("@/views/teams/TeamView.vue"),
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
