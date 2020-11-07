// import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const Oracle = {
  namespaced: true,
  state: {
    databaseCpuTimeRatio: [],
    databaseWaitTimeRatio: [],
    activeSerialSessions: [],
    timeList: [],

    // dbBlockGetsPerUserCall: 0,
    // logicalReadsPerUserCall: 0,
    // redoGeneratedPerSec: 0,

    // executionsPerSec: 0,
    // totalParseCountPerSec: 0,
    // openCursorsPerSec: 0,
    // userCommitsPerSec: 0,

    // physicalReadsPerSec: 0,
    // physicalWritesPerSec: 0

    oracleStatusList: []
  },
  getters: {
    getDatabaseCpuTimeRatioList: (state) => state.databaseCpuTimeRatio,
    getDatabaseWaitTimeRatio: (state) => state.databaseWaitTimeRatio,
    getActiveSerialSessions: (state) => state.activeSerialSessions,
    getTimeList: (state) => state.timeList
  },
  mutations: {
    SET_ORACLE_STATUS_LIST(state, data) {
      if (state.databaseCpuTimeRatio.length > 12) {
        state.databaseCpuTimeRatio.shift();
        state.databaseWaitTimeRatio.shift();
        state.activeSerialSessions.shift();
      }
      state.databaseCpuTimeRatio.push(data.databaseCpuTimeRatio);
      state.databaseWaitTimeRatio.push(data.databaseWaitTimeRatio);
      state.activeSerialSessions.push(data.activeSerialSessions);
    }
  },
  actions: {},
  modules: {}
};

export default Oracle;
