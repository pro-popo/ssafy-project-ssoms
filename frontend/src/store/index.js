import Vue from "vue";
import Vuex from "vuex";
import Account from "@/store/modules/account.js";

import Oracle from "@/store/modules/oracle.js";
import Schema from "@/store/modules/schema.js";
import TopQuery from "@/store/modules/topquery.js";

import axios from "axios";
import SERVER from "@/api/spring.js";
// import router from "@/router";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isSelected: true,
    time: 0,
    timeList: [],
    selectedRealTime: 0 // 시점의 인덱스 값으로 저장
  },
  getters: {
    getRealTime: (state) => state.time,
    getRealTimeList: (state) => state.timeList,
    getIsSelected: (state) => state.isSelected,
    selectedRealTime: (state) => state.selectedRealTime
  },
  mutations: {
    SET_REALTIME(state, data) {
      state.time = data;
      state.timeList.push(data.slice(11));
      if (state.timeList.length > 12) {
        state.timeList.shift();
      }
    },
    SET_SELECTED_REALTIME(state, selectedTime) {
      if (selectedTime == -1 || selectedTime == 11) {
        state.selectedRealTime = state.timeList.length - 1;
        state.isSelected = false;
      } else {
        state.selectedRealTime = selectedTime;
        // console.log("체크", state.selectedRealTime);
      }
    },
    SET_SETTING_SELECTED(state, data) {
      state.isSelected = data;
      // console.log("선택자", state.isSelected);
    }
  },
  actions: {
    initRealTimeData({ commit }) {
      console.log("들어오나??????");
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
