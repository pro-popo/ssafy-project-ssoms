// import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const Schema = {
  namespaced: true,
  state: {
    // schemaData: {
    //   schemaName: "",
    //   sqlCnt: 0,
    //   executions: 0,
    //   bufferGetsAvg: 0,
    //   diskReadsAvg: 0,
    //   rowsProcessedAvg: 0,
    //   cpuTimeAvg: 0,
    //   cpuTimeMax: 0,
    //   cpuTimeTot: 0,
    //   elapsedTimeAvg: 0,
    //   elapsedTimeMax: 0,
    //   elapsedTimeTot: 0
    // },
    schemaData: []
  },
  getters: {
    showSchemaData: (state) => state.schemaData,
    showTest: (state) => state.testlist,
    showTTest: (state) => state.testnum
  },
  mutations: {
    SET_SCHEMA_DATA(state, data) {
      state.schemaData.push(data);
    },
    SET_TEST(state, data) {
      // state.testlist.push(data);
      // if (state.testlist.length > 12) {
      //   state.testlist = state.testlist.slice(1, 13);
      // }
      state.schemaData = data;
    },
    SET_TTEST(state, data) {
      state.testnum = state.testnum + data;
    }
  },
  actions: {},
  modules: {}
};

export default Schema;
