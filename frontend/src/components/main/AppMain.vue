<template>
  <div class="app-main" id="app-main">
    <router-view />
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "AppMain",
  computed: {
    ...mapGetters,
  },
  props: {
    isAdmin: Boolean,
  },
  watch: {
    isAdmin: function () {
      this.moveMainPage();
    },
  },
  created() {
    this.moveMainPage();
  },
  methods: {
    moveMainPage() {
      if (this.isAdmin != null) {
        if (this.isAdmin) {
          if (this.$route.name !== "Setting")
            this.$router.push({ name: "Setting" });
        } else if (this.$route.name !== "RealTimeMonitoring") {
          this.$router.push({ name: "RealTimeMonitoring" });
        }
      }
    },
  },
};
</script>

<style>
.app-main {
  width: 100%;
  height: 100%;
  padding-top: 50px;
}
</style>
