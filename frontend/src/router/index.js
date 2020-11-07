import Vue from "vue";
import store from "@/store";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import Login from "@/views/MainLogin.vue";
import SignUpAdmin from "@/components/non-account/SignUpAdmin.vue";

// Admin
import Member from "@/views/admin/Member.vue";
import OracleDB from "@/views/admin/OracleDB.vue";
import Schema from "@/views/admin/Schema.vue";

// User
import MainHome from "@/views/user/MainHome.vue";
import PCResource from "@/views/user/PCResource.vue";
import SchemaMonitoring from "@/views/user/SchemaMonitoring.vue";
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
        name: "SchemaMonitoring",
        component: SchemaMonitoring
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
  },
  {
    path: "/admin/signup",
    name: "SignUpAdmin",
    component: SignUpAdmin
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
  if (to.name == "SignUpAdmin") {
    if (!store.getters["Account/isExistedAdmin"]) next();
    else next({ name: "Home" });
  } else {
    if (
      to.name != "Login" &&
      to.name != "SignUpAdmin" &&
      sessionStorage.getItem("loginSession") == null
    ) {
      next({ name: "Login" });
    } else {
      next();
    }
  }
  // if (to.name == "SignUpAdmin") {
  //   console.log(store.getters["Account/isExistedAdmin"]);
  //   if (!store.getters["Account/isExistedAdmin"]) {
  //     next({ name: "SignUpAdmin" });
  //   } else {
  //     next("/");
  //   }
  // }
  // } else next();
});

export default router;
