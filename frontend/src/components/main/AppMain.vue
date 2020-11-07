<template>
  <div class="app-main">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "AppMain",
  created() {
    this.$router.push({ name: "RealTimeMonitoring" });
  }
};
</script>

<style></style>
