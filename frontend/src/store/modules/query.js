import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const Query = {
  namespaced: true,
  state: {
    whole: {
      schemaName: "C##TESTDB",
      sqlCount: 21,
      executions: 258322,
      bufferGetsAvg: 7.0,
      diskReadsAvg: 0.0,
      rowsProcessedAvg: 30.0,
      cpuTimeAvg: 0.0,
      cpuTimeMax: 0.0,
      cpuTimeTot: 50.09,
      elapsedTimeAvg: 0.0,
      elapsedTimeMax: 0.0,
      elapsedTimeTot: 53.73
    },
    cpuTopQuery: [
      { queryID: 1, queryContent: "CPU 일", queryData: 1 },
      { queryID: 2, queryContent: "CPU 이", queryData: 2 },
      { queryID: 3, queryContent: "CPU 삼", queryData: 3 },
      { queryID: 4, queryContent: "CPU 사", queryData: 4 },
      { queryID: 5, queryContent: "CPU 오", queryData: 5 }
    ],
    timeTopQuery: [
      { queryID: 1, queryContent: "TIME 일", queryData: 1 },
      { queryID: 2, queryContent: "TIME 이", queryData: 2 },
      { queryID: 3, queryContent: "TIME 삼", queryData: 3 },
      { queryID: 4, queryContent: "TIME 사", queryData: 4 },
      { queryID: 5, queryContent: "TIME 오", queryData: 5 }
    ],
    resourceTopQuery: [
      { queryID: 1, queryContent: "Resource 일", queryData: 1 },
      { queryID: 2, queryContent: "Resource 이", queryData: 2 },
      { queryID: 3, queryContent: "Resource 삼", queryData: 3 },
      { queryID: 4, queryContent: "Resource 사", queryData: 4 },
      { queryID: 5, queryContent: "Resource 오", queryData: 5 }
    ]
  },
  getters: {
    schemaName: (state) => state.whole.schemaName,
    wholeQuery: (state) => state.whole,
    cpuTopQuery: (state) => state.cpuTopQuery,
    timeTopQuery: (state) => state.timeTopQuery,
    resourceTopQuery: (state) => state.resourceTopQuery
  },
  // commit
  mutations: {
    SET_WHOLE_QUERY(state, result) {
      state.whole.schemaName = result;
    }
  },
  // dispatch
  actions: {
    getWholeQuery() {
      axios
        .get()
        .then(() => {})
        .catch(() => {});
    }
  },
  modules: {}
};

export default Query;
