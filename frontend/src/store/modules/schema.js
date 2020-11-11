import axios from "axios";
import SERVER from "@/api/spring.js";
const Schema = {
  namespaced: true,
  state: {
    schemaList: [],
    realTimeSchemaList: {
      executions: [],
      cpuTimeTot: [],
      elapsedTimeTot: [],
      bufferGetsAvg: []
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
    },
    timeAndCpuList:{
        time : [],
        cpu: []
    },
  },
  getters: {
    getSchemaList: (state) => state.schemaList,
    SelectedSchema: (state) => state.selectedSchema,
    getSchemaData: (state) => state.schemaData,
    getRealTimeSchemaList1: (state) => state.realTimeSchemaList.executions,
    getRealTimeSchemaList2: (state) => state.realTimeSchemaList.cpuTimeTot,
    getRealTimeSchemaList3: (state) => state.realTimeSchemaList.elapsedTimeTot,
    getRealTimeSchemaList4: (state) => state.realTimeSchemaList.bufferGetsAvg,
    getSchemaLength: (state) => state.schemaList.length,
    getTimeAndCpuList: (state) => state.timeAndCpuList,
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
          state.realTimeSchemaList.bufferGetsAvg.push({
            name: data[i].parsingSchemaName,
            data: [data[i].bufferGetsAvg]
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
              state.realTimeSchemaList.bufferGetsAvg[b].data.push(
                data[a].bufferGetsAvg
              );
              if (state.realTimeSchemaList.executions[b].data.length > 12) {
                state.realTimeSchemaList.executions[b].data.shift();
                state.realTimeSchemaList.cpuTimeTot[b].data.shift();
                state.realTimeSchemaList.elapsedTimeTot[b].data.shift();
                state.realTimeSchemaList.bufferGetsAvg[b].data.shift();
              }
            }
          }
        }
      }
    },
    SET_TIME_AND_CPU_LIST(state, data){
        let times = [];
        let cpus = [];
        data.forEach(element => {
            let temp = element.time.split(' ');
            let f = temp[0].split('-');
            let s = temp[1].split(':');
            times.push(f[0]+'/'+f[1]+'/'+f[2]+'/'+s[0]+'/'+s[1]);
            cpus.push(element.databaseCpuTimeRatio);
        });
        state.timeAndCpuList.time = times;
        state.timeAndCpuList.cpu = cpus;
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
