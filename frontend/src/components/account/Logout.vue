<template>
  <div>
    <v-btn class="logout-btn" color="primary" @click="userLogout">
      LOGOUT
    </v-btn>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "Logout",
  methods: {
    ...mapActions("Account", ["logout"]),
    userLogout() {
      this.logout();
    }
  }
};
</script>

<style>
.logout-btn {
  width: 100%;
  top: auto;
}
</style>
