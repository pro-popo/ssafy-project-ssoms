<template>
  <v-navigation-drawer
    :expand-on-hover="fixedMini"
    :width="230"
    v-model="drawer"
    :mini-variant.sync="mini"
    permanent
    dark
    style="height: 100%"
    class="sidebar"
  >
    <div style="height: 100%" class="sidebar-container">
      <div>
        <v-list dense style="margin-top: -3px">
          <v-list-item style="margin-left: -6px">
            <v-btn
              class="animate__animated"
              :class="{
                animate__fadeOutRight: !fixedMini,
                animate__fadeInRight: fixedMini,
              }"
              icon
              :disabled="!fixedMini"
              @click.stop="
                mini = false;
                fixedMini = false;
              "
              style="margin-right: auto"
            >
              <v-icon>mdi-menu</v-icon>
            </v-btn>
            <v-btn
              icon
              class="animate__animated"
              :class="{
                animate__fadeOutLeft: fixedMini,
                animate__fadeInLeft: !fixedMini,
              }"
              :disabled="fixedMini"
              @click.stop="
                mini = true;
                fixedMini = true;
              "
              style="margin-left: auto"
            >
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
          </v-list-item>
          <v-list-item
            link
            @click="getMyProfile"
            style="margin: 14px 0px 7px 0px"
          >
            <v-list-item-icon>
              <v-icon>mdi-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title style="margin-right: 10px">
              <span>{{ email }}</span>
            </v-list-item-title>
          </v-list-item>
        </v-list>
        <v-divider></v-divider>
        <v-list dense>
          <AppSidebarAdmin v-if="isAdmin" :mini="mini" />
          <AppSidebarUser v-if="!isAdmin" />
        </v-list>
      </div>
      <div class="logout" title="로그아웃">
        <v-btn icon style="padding-left: 3px" @click="userLogout"
          ><v-icon>mdi-logout</v-icon></v-btn
        >
      </div>
    </div>
    <MyProfile
      v-if="requestProfile"
      @close-profile="requestProfile = false"
      @edit-profile="requestEditUser = true"
    />
  </v-navigation-drawer>
</template>

<script>
import AppSidebarAdmin from "@/components/main/AppSidebarAdmin.vue";
import AppSidebarUser from "@/components/main/AppSidebarUser.vue";

import { mapActions } from "vuex";
import "animate.css";

import MyProfile from "@/components/account/MyProfile.vue";

export default {
  name: "AppSidebar",
  data() {
    return {
      email: sessionStorage.getItem("loginSession"),
      drawer: true,
      model: 0,
      mini: true,
      fixedMini: true,
      requestProfile: false,
      profile: "",
    };
  },
  components: {
    AppSidebarAdmin,
    AppSidebarUser,
    MyProfile,
  },
  props: {
    isAdmin: Boolean,
  },

  methods: {
    ...mapActions("Account", ["logout"]),
    userLogout() {
      var logoutConfirm = confirm("로그아웃 하시겠습니까?");
      if (logoutConfirm) {
        this.logout();
      }
    },
    getMyProfile() {
      if (!this.mini) this.requestProfile = true;
    },
  },
};
</script>

<style>
.sidebar {
  position: absolute;
  z-index: 100;
  background: rgb(41, 41, 42);
  background: linear-gradient(
    199deg,
    rgba(41, 41, 42, 1) 35%,
    rgba(16, 16, 18, 1) 85%
  );
}
.sidebar-schema {
  max-height: 400px;
  overflow-y: auto;
  -ms-overflow-style: none !important;
}
.sidebar-schema::-webkit-scrollbar {
  display: none;
}

.v-list-item-group .v-list-item--active {
  background: linear-gradient(to right, #021999, #270c8b);
}

.sidebar-container {
  display: flex;
  flex-direction: column;
}
.logout {
  margin-top: auto;
  margin-right: auto;
  margin-left: 10px;
  margin-bottom: 10px;
}
</style>
