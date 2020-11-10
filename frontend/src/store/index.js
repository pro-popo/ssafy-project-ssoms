import Vue from "vue";
import Vuex from "vuex";
import Account from "@/store/modules/account.js";

import Oracle from "@/store/modules/oracle.js";
import Schema from "@/store/modules/schema.js";
import TopQuery from "@/store/modules/topquery.js";

import axios from "axios";
import SERVER from "@/api/spring.js";

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
  methods: {},
  actions: {
    initRealTimeData({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.getRealTimeData).then((res) => {
        const realTimeHistoricalDataList = res.data.map.realTimeMonitoringList;

        // 1. oracle  2. schema  3. topquery
        realTimeHistoricalDataList.forEach((realTimeHistoricalData) => {
          commit(
            "Oracle/SET_ORACLE_STATUS_LIST",
            realTimeHistoricalData.oracleStatus
          );
          commit(
            "Schema/SET_REALTIME_SCHEMA_LIST",
            realTimeHistoricalData.allSchemaStastics
          );
          commit(
            "TopQuery/SET_TOPQUERY_LIST",
            realTimeHistoricalData.allSchemaQueryInfo
          );
          commit("SET_REALTIME", realTimeHistoricalData.time);
        });
      });
    }
  },
  modules: {
    Account: Account,
    Oracle: Oracle,
    Schema: Schema,
    TopQuery: TopQuery
  }
});
