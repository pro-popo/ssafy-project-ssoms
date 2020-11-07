// import axios from "axios";
// import SERVER from "@/api/spring.js";
// import router from "@/router";

const TopQuery = {
  namespaced: true,
  state: {
    topQueryList: [],
    topQueryDetail: ""
  },
  getters: {
    getTopQueryList: (state) => state.topQueryList,
    getTopQueryDetail: (state) => state.topQueryDetail
  },
  mutations: {
    SET_TOPQUERY_LIST(state, data) {
      state.topQueryList = data;
    },
    SET_TOPQUERY_DETAIL(state, data) {
      state.topQueryDetail = state.topQueryList[data];
    }
  },
  actions: {},
  modules: {}
};

export default TopQuery;
