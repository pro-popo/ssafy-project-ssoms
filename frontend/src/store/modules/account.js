import axios from "axios";
import SERVER from "@/api/spring.js";
import router from "@/router";

const Account = {
  namespaced: true,
  state: {
    account: {
      email: "",
      name: "",
      employeeId: "",
      phoneNumber: "",
      admin: false,
      visited_history: []
    },
    isExistedAdmin: true,
    isSuccessSignUp: false
  },
  getters: {
    account: (state) => state.account,
    isExistedAdmin: (state) => state.isExistedAdmin,
    isSuccessSignUp: (state) => state.isSuccessSignUp
  },
  mutations: {
    SET_EXISTED_ADMIN(state, result) {
      state.isExistedAdmin = result;
    },
    SET_SUCCESS_SIGNUP(state, result) {
      state.isSuccessSignUp = result;
    }
  },
  actions: {
    checkExistedAdmin({ commit }) {
      axios
        .get(SERVER.URL + SERVER.ROUTES.isExistedAdmin)
        .then((res) => {
          if (res.data.result == "yes") {
            commit("SET_EXISTED_ADMIN", true);
            // router.push({ name: "Login" });
          } else if (res.data.result == "no") {
            commit("SET_EXISTED_ADMIN", false);
            router.push({ name: "SignUpAdmin" });
          }
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    logout() {
      axios
        .get(
          SERVER.URL +
            SERVER.ROUTES.logout +
            sessionStorage.getItem("loginSession")
        )
        .then((res) => {
          if (res.data.result == "success") {
            sessionStorage.removeItem("loginSession");
            router.push({ name: "Login" });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
  modules: {}
};

export default Account;
