import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import Login from "@/views/MainLogin.vue";
// Admin
import Member from "@/views/admin/Member.vue";
import OracleDB from "@/views/admin/OracleDB.vue";
import Schema from "@/views/admin/Schema.vue";

// User
import MainHome from "@/views/user/MainHome.vue";
import PCResource from "@/views/user/PCResource.vue";
import QueryMonitoring from "@/views/user/QueryMonitoring.vue";
import RealMonitoring from "@/views/user/RealMonitoring.vue";
import Traffic from "@/views/user/Traffic.vue";
import Socket from "@/views/Socket";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    children: [
      {
        path: "/admin/member",
        name: "Member",
        component: Member
      },
      {
        path: "/admin/oracle",
        name: "OracleDB",
        component: OracleDB
      },
      {
        path: "/admin/schema",
        name: "Schema",
        component: Schema
      },
      {
        path: "/user/home",
        name: "MainHome",
        component: MainHome
      },
      {
        path: "/user/resource",
        name: "PCResource",
        component: PCResource
      },
      {
        path: "/user/query",
        name: "QueryMonitoring",
        component: QueryMonitoring
      },
      {
        path: "/user/real",
        name: "RealMonitoring",
        component: RealMonitoring
      },
      {
        path: "/user/traffic",
        name: "Traffic",
        component: Traffic
      },
      {
        path: "/Socket",
        name: "Socket",
        component: Socket
      }
    ]
  },

  {
    path: "/login",
    name: "Login",
    component: Login
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    return { x: 0, y: 0 };
  }
});

router.beforeEach((to, from, next) => {
  if (to.name != "Login" && sessionStorage.getItem("loginSession") == null) {
    next({ name: "Login" });
  } else {
    next();
  }
});

export default router;
