import Vue from "vue";
import Vuex from "vuex";
import Account from "@/store/modules/account.js";
import Query from "@/store/modules/query.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    Account: Account,
    Query: Query
  }
});
