import Vue from "vue";
import Vuex from "vuex";
import Account from "@/store/modules/account.js";

import Oracle from "@/store/modules/oracle.js";
import Schema from "@/store/modules/schema.js";
import TopQuery from "@/store/modules/topquery.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    time: 0,
    timeList: []
  },
  getters: {
    getRealTime: (state) => state.time,
    getRealTimeList: (state) => state.timeList
  },
  mutations: {
    SET_REALTIME(state, data) {
      state.time = data;
      state.timeList.push(data.slice(11));
      if (state.timeList.length > 12) {
        state.timeList.shift();
      }
    }
  },
  actions: {},
  modules: {
    Account: Account,
    Oracle: Oracle,
    Schema: Schema,
    TopQuery: TopQuery
  }
});
