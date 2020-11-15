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
      state.topQueryList.push(data);
      if (state.topQueryList.length > 12) {
        state.topQueryList.shift();
      }
    },
    SET_TOPQUERY_DETAIL(state, data) {
      const selectedRealTime = data[0];
      const index = data[1];
      state.topQueryDetail = state.topQueryList[selectedRealTime][index];
    }
  },
  actions: {},
  modules: {}
};

export default TopQuery;
