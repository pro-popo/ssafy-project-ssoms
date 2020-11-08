import axios from "axios";
import SERVER from "@/api/spring.js";
const Schema = {
  namespaced: true,
  state: {
    schemaList: [],
    realTimeSchemaList: {
      executions: [],
      cpuTimeTot: [],
      elapsedTimeTot: []
    },
    selectedSchema: "",
    schemaData: {
      sqlCnt: [861],
      executions: [214059],
      bufferGetsAvg: [153],
      diskReadsAvg: [0],
      rowsProcessedAvg: [2],
      cpuTimeAvg: [0],
      cpuTimeMax: [123],
      cpuTimeTot: [0],
      elapsedTimeAvg: [0],
      elapsedTimeMax: [123],
      elapsedTimeTot: [0]
    }
  },
  getters: {
    getSchemaList: (state) => state.schemaList,
    SelectedSchema: (state) => state.selectedSchema,
    getSchemaData: (state) => state.schemaData,
    getRealTimeSchemaList1: (state) => state.realTimeSchemaList.executions,
    getRealTimeSchemaList2: (state) => state.realTimeSchemaList.cpuTimeTot,
    getRealTimeSchemaList3: (state) => state.realTimeSchemaList.elapsedTimeTot,
    getSchemaLength: (state) => state.schemaList.length
  },
  mutations: {
    SET_SCHEMA_LIST(state, data) {
      state.schemaList = data;
    },
    SET_SELECTED_SCHEMA(state, data) {
      state.selectedSchema = data;
    },
    SET_REALTIME_SCHEMA_LIST(state, data) {
      if (state.realTimeSchemaList.executions.length === 0) {
        for (var i = 0; i < data.length; i++) {
          state.realTimeSchemaList.executions.push({
            name: data[i].parsingSchemaName,
            data: [data[i].executions]
          });
          state.realTimeSchemaList.cpuTimeTot.push({
            name: data[i].parsingSchemaName,
            data: [data[i].cpuTimeTot]
          });
          state.realTimeSchemaList.elapsedTimeTot.push({
            name: data[i].parsingSchemaName,
            data: [data[i].elapsedTimeTot]
          });
        }
      } else {
        for (var a = 0; a < data.length; a++) {
          for (var b = 0; b < state.realTimeSchemaList.executions.length; b++) {
            if (
              state.realTimeSchemaList.executions[b].name ===
              data[a].parsingSchemaName
            ) {
              state.realTimeSchemaList.executions[b].data.push(
                data[a].executions
              );
              state.realTimeSchemaList.cpuTimeTot[b].data.push(
                data[a].cpuTimeTot
              );
              state.realTimeSchemaList.elapsedTimeTot[b].data.push(
                data[a].elapsedTimeTot
              );
              if (state.realTimeSchemaList.executions[b].data.length > 12) {
                state.realTimeSchemaList.executions[b].data.shift();
                state.realTimeSchemaList.cpuTimeTot[b].data.shift();
                state.realTimeSchemaList.elapsedTimeTot[b].data.shift();
              }
            }
          }
        }
      }
    }
  },
  actions: {
    getSettingSchema({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.getSettingsSchema).then((res) => {
        commit("SET_SCHEMA_LIST", res.data.map.schema);
      });
    }
  }
};
export default Schema;
