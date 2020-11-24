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
    timeAndCpuList: {
      time: [],
      cpu: [],
      check: false,
      ani_flag: false,
      isEmpty: false
    },
    pastTimeData: {
      allSchemaStastics: [],
      oracleStatus: {},
      schemas: [],
      schemaList: [],
      radarchart: [],
      check: false
    }
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
    getPastTimeData: (state) => state.pastTimeData,
    getPastTimeDataCheck: (state) => state.pastTimeData.check
  },
  mutations: {
    SET_TIME_AND_CPU_LIST_CHECK(state, data) {
      state.timeAndCpuList.check = data;
    },
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
    SET_TIME_AND_CPU_LIST(state, data) {
      let times = [];
      let cpus = [];
      if (data != null) {
        state.timeAndCpuList.isEmpty = false;
        data.forEach((element) => {
          times.push(element.time);
          cpus.push(element.databaseCpuTimeRatio);
        });
      } else {
        state.timeAndCpuList.isEmpty = true;
      }
      state.timeAndCpuList.time = times;
      state.timeAndCpuList.cpu = cpus;
      state.timeAndCpuList.check = true;
    },
    SET_PAST_TIME_DATA(state, data) {
      state.pastTimeData.oracleStatus = data.oracleStatus;
      let tempstat = {};
      let tempsch = {};
      let list = [];
      let radar = [];
      data.allSchemaStastics.forEach((element) => {
        tempstat[element.parsingSchemaName] = element;

        radar.push({
          value: [
            element.bufferGetsAvg,
            element.cpuTimeAvg,
            element.cpuTimeMax,
            element.cpuTimeTot,
            element.sqlCnt
          ],
          name: element.parsingSchemaName
        });
        list.push(element.parsingSchemaName);
      });
      data.schemas.forEach((element) => {
        tempsch[element.bufferGets[0].parsingSchemaName] = element;
      });
      state.pastTimeData.allSchemaStastics = tempstat;
      state.pastTimeData.schemas = tempsch;
      state.pastTimeData.radarchart = radar;
      state.pastTimeData.schemaList = list;
    },
    SET_PAST_TIME_DATA_CHECK(state) {
      state.pastTimeData.check = !state.pastTimeData.check;
    },
    SET_IS_EMPTY_CPU_DATA(state, data) {
      state.timeAndCpuList.isEmpty = data;
    }
  },
  actions: {
    getSettingSchema({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.getSettingsSchema).then((res) => {
        commit("SET_SCHEMA_LIST", res.data.map.schema);
      });
    },

    setTimeAndCpuList({ commit, dispatch }, { start, end }) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getPastData + `${start}${end}`)
        .then((res) => {
          if (res.data.result === "empty") {
            commit("SET_TIME_AND_CPU_LIST", null);
          } else if (res.data.result === "success") {
            commit("SET_TIME_AND_CPU_LIST", res.data.map.timeAndCpuList);
            dispatch(
              "setPastTimeData",
              res.data.map.timeAndCpuList[
                res.data.map.timeAndCpuList.length - 1
              ].time
            );
          }
        })
        .catch((err) => console.log(err));
    },
    setPastTimeData({ commit }, date) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getPastData + "/" + date)
        .then((res) => {
          if (res.data.result === "notExist") {
            alert("data not exist");
          }
          if (res.data.result === "success") {
            commit("SET_PAST_TIME_DATA", res.data.map.realTimeMonitoring);
          }
        })
        .catch((err) => console.log(err));
    }
  }
};
export default Schema;
