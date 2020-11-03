import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const Query = {
  namespaced: true,
  state: {
    whole: {
      schemaName: "C##TESTDB",
      sqlCount: 212535,
      executions: 258322,
      bufferGetsAvg: 7.2,
      diskReadsAvg: 8.3,
      rowsProcessedAvg: 30.0,
      cpuTimeAvg: 1.5,
      cpuTimeMax: 7.3,
      cpuTimeTot: 50.09,
      elapsedTimeAvg: 2.5,
      elapsedTimeMax: 6.2,
      elapsedTimeTot: 53.73
    },
    cpuTopQuery: [
      {
        queryID: 1,
        querySQL: "delete from table3 where idx = 82055",
        lastActiveTime: "2020-11-01 17:11:36",
        executions: 73812,
        timePerSec: 2986.243,
        timeRatio: 26.3
      },
      {
        queryID: 2,
        querySQL:
          "insert into table2 values(TABLE2_SEQ.NEXTVAL, 81429, &apos;test&apos;)",
        queryData: 2
      },
      { queryID: 3, querySQL: "select * from table9", queryData: 3 },
      {
        queryID: 4,
        querySQL: "delete from table7 where idx = 85456",
        queryData: 4
      },
      {
        queryID: 5,
        querySQL:
          "insert into table2 values(TABLE2_SEQ.NEXTVAL, 81943, &apos;test&apos;)",
        queryData: 5
      }
    ],
    timeTopQuery: [
      { queryID: 1, querySQL: "TIME 일", queryData: 1 },
      { queryID: 2, querySQL: "TIME 이", queryData: 2 },
      { queryID: 3, querySQL: "TIME 삼", queryData: 3 },
      { queryID: 4, querySQL: "TIME 사", queryData: 4 },
      { queryID: 5, querySQL: "TIME 오", queryData: 5 }
    ],
    resourceTopQuery: [
      { queryID: 1, querySQL: "Resource 일", queryData: 1 },
      { queryID: 2, querySQL: "Resource 이", queryData: 2 },
      { queryID: 3, querySQL: "Resource 삼", queryData: 3 },
      { queryID: 4, querySQL: "Resource 사", queryData: 4 },
      { queryID: 5, querySQL: "Resource 오", queryData: 5 }
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
