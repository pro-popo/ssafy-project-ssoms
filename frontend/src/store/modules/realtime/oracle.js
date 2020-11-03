// import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const Oracle = {
  namespaced: true,
  state: {
    status: {
      databaseCpuTimeRatio: 0,
      databaseWaitTimeRatio: 0,
      activeSerialSessions: 0
    },
    memory: {
      dbBlockGetsPerUserCall: 0,
      logicalReadsPerUserCall: 0,
      redoGeneratedPerSec: 0
    },
    process: {
      executionsPerSec: 0,
      totalParseCountPerSec: 0,
      openCursorsPerSec: 0,
      userCommitsPerSec: 0
    },
    storage: {
      physicalReadsPerSec: 0,
      physicalWritesPerSec: 0
    }
  },
  getters: {},
  mutations: {},
  actions: {},
  modules: {}
};

export default Oracle;
