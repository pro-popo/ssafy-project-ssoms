import Vue from "vue";
import Vuex from "vuex";
import Account from "@/store/modules/account.js";

import Oracle from "@/store/modules/oracle.js";
import Schema from "@/store/modules/schema.js";
import TopQuery from "@/store/modules/topquery.js";
import Realtime from "@/store/modules/realtime.js";
import Socket from "@/store/modules/socket.js";


Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    graphColor: [
      "#f72585",
      "#7209b7",
      "#3a0ca3",
      "#4361ee",
      "#4cc9f0",
      "#6fffe9",
      "#f87060",
      "#ff7aa2"
    ],
  },
  getters: {

  },
  mutations: {

  },
  actions: {
  },
  modules: {
    Account: Account,
    Oracle: Oracle,
    Schema: Schema,
    TopQuery: TopQuery,
    Realtime: Realtime,
    Socket: Socket
  }
});
