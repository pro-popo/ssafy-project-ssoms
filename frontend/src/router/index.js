import Vue from "vue";
import store from "@/store";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import Login from "@/views/MainLogin.vue";
import SignUpAdmin from "@/components/non-account/SignUpAdmin.vue";

// Admin
import Setting from "@/views/admin/Setting.vue";
import Member from "@/views/admin/Member.vue";
import OracleDB from "@/views/admin/OracleDB.vue";
import Schema from "@/views/admin/Schema.vue";

// User
import SchemaMonitoring from "@/views/user/SchemaMonitoring.vue";
import RealTimeMonitoring from "@/views/user/RealTimeMonitoring.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    children: [
      {
        path: "/admin",
        name: "Setting",
        component: Setting
      },
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

      // user
      {
        path: "/user/realtime/:index",
        name: "RealTimeMonitoring",
        component: RealTimeMonitoring
      },
      {
        path: "/user/schema",
        name: "SchemaMonitoring",
        component: SchemaMonitoring
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
  // if (from.name == null) next(false);
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
});

export default router;
