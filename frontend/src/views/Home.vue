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
    }
  },
  created() {
    this.checkIsAdmin();
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
  height: 90%;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 50px 80px;
}
</style>
