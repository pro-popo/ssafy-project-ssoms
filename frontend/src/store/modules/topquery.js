// import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const TopQuery = {
  namespaced: true,
  state: {
    topQueryList: []
  },
  getters: {
    getTopQueryList: (state) => state.topQueryList
  },
  mutations: {
    SET_TOPQUERY_LIST(state, data) {
      state.topQueryList = data;
    }
  },
  actions: {},
  modules: {}
};

export default TopQuery;
