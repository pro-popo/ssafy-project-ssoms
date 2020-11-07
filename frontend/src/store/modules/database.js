import axios from "axios";
import SERVER from "@/api/spring.js";
const Database = {
  namespaced: true,
  state: {
    schemaList: [],
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
    getSchemaData: (state) => state.schemaData
  },
  mutations: {
    SET_SCHEMA_LIST(state, data) {
      state.schemaList = data;
    },
    SET_SELECTED_SCHEMA(state, data) {
      state.selectedSchema = data;
    }
  },
  actions: {
    getSettingSchema({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.getSettingsSchema).then((res) => {
        console.log(res.data);
        commit("SET_SCHEMA_LIST", res.data.map.schema);
      });
    }
  }
};
export default Database;
