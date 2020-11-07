import Vue from "vue";
import Vuex from "vuex";
import Account from "@/store/modules/account.js";

import Database from "@/store/modules/database.js";
import TopQuery from "@/store/modules/topquery.js";

import Oracle from "@/store/modules/schema/oracle.js";
import Schema from "@/store/modules/schema/schema.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    time: 0
  },
  getters: {
    getRealTime: (state) => state.time
  },
  mutations: {
    SET_REALTIME(state, data) {
      state.time = data;
    }
  },
  actions: {},
  modules: {
    Account: Account,
    Database: Database,
    Oracle: Oracle,
    Schema: Schema,
    TopQuery: TopQuery
  }
});
