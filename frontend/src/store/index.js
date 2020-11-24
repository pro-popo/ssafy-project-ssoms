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
    time: 0,
    timeList: [],
    selectedRealTime: 0, // 시점의 인덱스 값으로 저장
    selectedTooltip: "",
    outlierLog:[]
  },
  getters: {
    getRealTime: (state) => state.time,
    getRealTimeList: (state) => state.timeList,
    getIsSelected: (state) => state.isSelected,
    selectedRealTime: (state) => state.selectedRealTime,
    selectedTooltip: (state) => state.selectedTooltip,
    getOutlierLog: (state) => state.outlierLog,
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
        state.isSelected = true;
      }
    },
    SET_SETTING_SELECTED(state, data) {
      state.isSelected = data;
    },
    SET_SELECTED_TOOLTIP(state, data) {
      state.selectedTooltip = data;
    },
    SET_OUTLIER_LOG(state, data){
        state.outlierLog = data;
    }
  },
  actions: {
    getOutlierData({ commit }, { start, end }){
        axios.get(SERVER.URL + SERVER.ROUTES.getOutlierData + `${start}${end}`)
        .then((res) => {
            commit("SET_OUTLIER_LOG", res.map);
            console.log("이상");
            console.log(res.data);
        });
    },
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
        commit("SET_SELECTED_REALTIME", realTimeHistoricalDataList.length - 1);
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
