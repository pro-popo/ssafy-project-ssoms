// import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const Oracle = {
  namespaced: true,
  state: {
    // databaseCpuTimeRatio: 0,
    // databaseWaitTimeRatio: 0,
    // activeSerialSessions: 0,

    // dbBlockGetsPerUserCall: 0,
    // logicalReadsPerUserCall: 0,
    // redoGeneratedPerSec: 0,

    // executionsPerSec: 0,
    // totalParseCountPerSec: 0,
    // openCursorsPerSec: 0,
    // userCommitsPerSec: 0,

    // physicalReadsPerSec: 0,
    // physicalWritesPerSec: 0

    oracleList: []
  },
  getters: {
    showlist: (state) => state.oracleList
  },
  mutations: {
    SET_LIST(state, data) {
      state.oracleList.push(data);
      if (state.oracleList.length > 10) {
        state.oracleList = state.oracleList.slice(1, 11);
      }
      // console.log(state.LIST[state.LIST.length - 1].databaseCpuTimeRatio);
    }
  },
  actions: {},
  modules: {}
};

export default Oracle;
