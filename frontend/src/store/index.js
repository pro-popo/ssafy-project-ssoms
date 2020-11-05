import Vue from "vue";
import Vuex from "vuex";
import Account from "@/store/modules/account.js";
import Database from "@/store/modules/database.js";
import Query from "@/store/modules/query.js";

// realtime
import Oracle from "@/store/modules/realtime/oracle.js";
import Schema from "@/store/modules/realtime/schema.js";
import TopQuery from "@/store/modules/realtime/topquery.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    Account: Account,
    Database: Database,
    Query: Query,
    Oracle: Oracle,
    Schema: Schema,
    TopQuery: TopQuery
  }
});
