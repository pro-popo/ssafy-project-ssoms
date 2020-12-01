
const Oracle = {
  namespaced: true,
  state: {
    oracleStatue: {
      databaseCpuTimeRatio: [],
      databaseWaitTimeRatio: [],
      activeSerialSessions: [],

      dbBlockGetsPerSec: [],
      logicalReadsPerSec: [],
      redoGeneratedPerSec: [],

      executionsPerSec: [],
      totalParseCountPerSec: [],
      openCursorsPerSec: [],
      userCommitsPerSec: [],

      physicalReadsPerSec: [],
      physicalWritesPerSec: [],

      responseTimePerTxn: [] // 한 트랜잭션 당 응답을 받는 시간을 의미
    }
  },
  getters: {
    getDatabaseCpuTimeRatioList: (state) =>
      state.oracleStatue["databaseCpuTimeRatio"],
    getDatabaseWaitTimeRatio: (state) =>
      state.oracleStatue["databaseWaitTimeRatio"],
    getActiveSerialSessions: (state) =>
      state.oracleStatue["activeSerialSessions"],

    getDbBlockGetsPerSec: (state) => state.oracleStatue["dbBlockGetsPerSec"],
    getLogicalReadsPerSec: (state) => state.oracleStatue["logicalReadsPerSec"],
    getRedoGeneratedPerSec: (state) =>
      state.oracleStatue["redoGeneratedPerSec"],

    getExecutionsPerSec: (state) => state.oracleStatue["executionsPerSec"],
    getTotalParseCountPerSec: (state) =>
      state.oracleStatue["totalParseCountPerSec"],
    getOpenCursorsPerSec: (state) => state.oracleStatue["openCursorsPerSec"],
    getUserCommitsPerSec: (state) => state.oracleStatue["userCommitsPerSec"],

    getPhysicalReadsPerSec: (state) =>
      state.oracleStatue["physicalReadsPerSec"],
    getPhysicalWritesPerSec: (state) =>
      state.oracleStatue["physicalWritesPerSec"],

    getResponesTimePerTxn: (state) => state.oracleStatue["responseTimePerTxn"]
  },
  mutations: {
    SET_ORACLE_STATUS_LIST(state, data) {
      if (state.oracleStatue["databaseCpuTimeRatio"].length > 11) {
        for (const [key] of Object.entries(data)) {
          state.oracleStatue[`${key}`].shift();
        }
      }
      for (const [key, value] of Object.entries(data)) {
        state.oracleStatue[`${key}`].push(value);
      }
    }
  },
  actions: {},
  modules: {}
};

export default Oracle;
