// import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const Oracle = {
  namespaced: true,
  state: {
    oracleStatue: {
      databaseCpuTimeRatio: [],
      databaseWaitTimeRatio: [],
      activeSerialSessions: [],

      dbBlockGetsPerUserCall: [],
      logicalReadsPerUserCall: [],
      redoGeneratedPerSec: [],

      executionsPerSec: [],
      totalParseCountPerSec: [],
      openCursorsPerSec: [],
      userCommitsPerSec: [],

      physicalReadsPerSec: [],
      physicalWritesPerSec: [],

      responseTimePerTxn: []
    }
  },
  getters: {
    getDatabaseCpuTimeRatioList: (state) =>
      state.oracleStatue["databaseCpuTimeRatio"],
    getDatabaseWaitTimeRatio: (state) =>
      state.oracleStatue["databaseWaitTimeRatio"],
    getActiveSerialSessions: (state) =>
      state.oracleStatue["activeSerialSessions"],

    getDbBlockGetsPerUserCall: (state) =>
      state.oracleStatue["dbBlockGetsPerUserCall"],
    getLogicalReadsPerUserCall: (state) =>
      state.oracleStatue["logicalReadsPerUserCall"],
    getRedoGeneratedPerSec: (state) =>
      state.oracleStatue["redoGeneratedPerSec"],

    getExecutionsPerSec: (state) =>
      state.oracleStatue["activexecutionsPerSeceSerialSessions"],
    getTotalParseCountPerSec: (state) =>
      state.oracleStatue["totalParseCountPerSec"],
    getOpenCursorsPerSec: (state) => state.oracleStatue["openCursorsPerSec"],
    getUserCommitsPerSec: (state) => state.oracleStatue["userCommitsPerSec"],

    getPhysicalReadsPerSec: (state) =>
      state.oracleStatue["physicalReadsPerSec"],
    getPhysicalWritesPerSec: (state) =>
      state.oracleStatue["physicalWritesPerSec"],

    getResponesTimePerTxn: (state) => state.oracleStatue["responesTimePerTxn"]
  },
  mutations: {
    SET_ORACLE_STATUS_LIST(state, data) {
      if (state.oracleStatue["databaseCpuTimeRatio"].length > 12) {
        for (const [key] of Object.entries(data)) {
          state.oracleStatue[`${key}`].shift();
        }
      }
      for (const [key, value] of Object.entries(data)) {
        console.log(key);
        state.oracleStatue[`${key}`].push(value);
        console.log(key + ":" + state.oracleStatue[`${key}`]);
      }
    }
  },
  actions: {},
  modules: {}
};

export default Oracle;
