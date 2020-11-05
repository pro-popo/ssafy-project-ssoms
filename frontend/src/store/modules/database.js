import axios from "axios";
import SERVER from "@/api/spring.js";
const Database = {
  namespaced: true,
  state: {
    schemaList: [],
    selectedSchema: ""
  },
  getters: {
    getSchemaList: (state) => state.schemaList,
    SelectedSchema: (state) => state.selectedSchema
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
