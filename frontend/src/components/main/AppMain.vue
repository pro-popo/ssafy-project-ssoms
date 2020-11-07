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
  watch: {
    isAdmin: function() {
      this.moveMainPage();
    }
  },
  created() {
    this.moveMainPage();
  },
  methods: {
    moveMainPage() {
      setTimeout(
        function() {
          if (this.isAdmin) {
            if (this.$route.name !== "OracleDB")
              this.$router.push({ name: "OracleDB" });
          } else if (this.$route.name !== "RealTimeMonitoring") {
            this.$router.push({ name: "RealTimeMonitoring" });
          }
        }.bind(this),
        300
      );
    }
  }
};
</script>

<style></style>
