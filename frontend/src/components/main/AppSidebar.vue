<template>
  <div class="user-side-bar">
    <div class="user-id mb-4" @click="getMyProfile">
      <v-icon class="mr-2 mdi mdi-account-circle-outline"></v-icon>
      <span>{{ userID }}</span>
    </div>
    <hr class="mb-3" />
    <div>
      <AppAdminSidebar v-if="isAdmin" />
      <AppUserSidebar v-if="!isAdmin" />
    </div>
    <v-btn class="logout-btn" color="primary" @click="userLogout">
      LOGOUT
    </v-btn>
  </div>
</template>

<script>
import AppAdminSidebar from "@/components/main/AppAdminSidebar.vue";
import AppUserSidebar from "@/components/main/AppUserSidebar.vue";
import { mapActions } from "vuex";
export default {
  name: "AppSidebar",
  components: {
    AppAdminSidebar,
    AppUserSidebar
  },
  props: {
    isAdmin: Boolean
  },
  data() {
    return {
      schemaID: "스키마ID",
      userID: sessionStorage.getItem("loginSession")
    };
  },
  methods: {
    ...mapActions("Account", ["logout"]),
    userLogout() {
      this.logout();
    },
    getMyProfile() {
      this.$emit("user-profile");
    }
  }
};
</script>

<style>
.DB-icon {
  font-size: 25px;
}
.user-side-bar {
  padding-top: 20px;
  /* height: 100%; */
  width: 220px;
  /* position: absolute;
  z-index: 1;
  top: 0;
  left: 0; */
  background-color: rgb(221, 221, 221);
}
.user-side-bar hr {
  margin: 0px;
}
.user-side-bar a {
  padding: 8px 0px 8px 32px;
  text-decoration: none;
  font-size: 20px;
  display: block;
  transition: 0.2s ease-in-out;
}

.user-side-bar a:hover,
.offcanvas a:focus {
  color: #fff;
}

.user-id {
  padding: 0px 0px 0px 32px;
  height: 40px;
  line-height: 40px;
  font-size: 17px;
  cursor: pointer;
  display: flex;
}
</style>
