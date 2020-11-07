<template>
  <div class="app-main">
    <router-view />
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "AppMain",
  computed: {
    ...mapGetters
  },
  props: {
    isAdmin: Boolean
  },
  created() {
    if (this.isAdmin) {
      this.$router.push({ name: "OracleDB" });
    } else {
      this.$router.push({ name: "RealTimeMonitoring" });
    }
  }
};
</script>

<style></style>
