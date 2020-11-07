<template>
  <div class="home-container">
    <div class="home-sidebar">
      <AppSidebar :isAdmin="isAdmin" @user-profile="getMyProfile" />
    </div>
    <div class="home-main-container">
      <div class="home-navbar">
        <app-navbar />
      </div>

      <div class="home-main">
        <AppMain />
      </div>
    </div>

    <MyProfile
      v-if="requestProfile"
      @close-profile="closeProfile"
      @edit-profile="showEditUser"
    />
    <EditUser
      v-if="requestEditUser"
      :profile="profile"
      @close-edit-profile="closeEditUser"
      @open-profile="requestProfile = !requestProfile"
    />
  </div>
</template>

<script>
import AppNavbar from "@/components/main/AppNavbar.vue";
import AppSidebar from "@/components/main/AppSidebar.vue";

import AppMain from "@/components/main/AppMain.vue";

import MyProfile from "@/components/account/MyProfile.vue";
import EditUser from "@/components/account/EditUser.vue";

import SERVER from "@/api/spring.js";
import axios from "axios";

import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

import { mapMutations } from "vuex";

export default {
  name: "Home",
  components: {
    AppNavbar,
    AppMain,
    AppSidebar,

    MyProfile,
    EditUser
  },
  data() {
    return {
      isAdmin: false,
      requestProfile: false,
      requestEditUser: false,
      profile: ""
    };
  },
  methods: {
    checkIsAdmin() {
      axios
        .get(
          SERVER.URL +
            SERVER.ROUTES.idAdmin +
            sessionStorage.getItem("loginSession")
        )
        .then((res) => {
          if (res.data.result == "no") {
            this.isAdmin = false;
          } else {
            this.isAdmin = true;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getMyProfile() {
      this.requestProfile = true;
    },
    closeProfile() {
      this.requestProfile = false;
    },
    showEditUser(data) {
      this.requestEditUser = true;
      this.profile = data;
    },
    closeEditUser() {
      this.requestEditUser = false;
    },
    connect() {
      const serverURL = "http://localhost:8080/ssoms/stomp";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          this.stompClient.subscribe("/sendData/schedulerM", (res) => {
            this.SET_LIST(JSON.parse(res.body).OracleStastics);
            this.SET_TOPQUERY_LIST(JSON.parse(res.body).allSchemaQueryInfo);
            this.SET_REALTIME(JSON.parse(res.body).time);
            this.SET_REALTIME_SCHEMA_LIST(
              JSON.parse(res.body).allSchemaStastics
            );
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    ...mapMutations("Oracle", ["SET_LIST"]),
    ...mapMutations("TopQuery", ["SET_TOPQUERY_LIST"]),
    ...mapMutations(["SET_REALTIME"]),
    ...mapMutations("Schema", ["SET_REALTIME_SCHEMA_LIST"])
  },
  created() {
    this.checkIsAdmin();
    this.connect();
  }
};
</script>

<style>
.home-container {
  height: 100vh;
  display: flex;
}
.home-navbar {
  width: 100%;
}
.home-sidebar {
  height: 100%;
}

.home-main-container {
  display: flex;
  flex: 1 0 0;
  flex-direction: column;
  width: 100%;
  height: 100vh;
}
.home-main {
  width: 100%;
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 50px 80px;
}
</style>
