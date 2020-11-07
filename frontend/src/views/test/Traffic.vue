<template>
  <div>
    <span>IP</span> |
    <span>테이블</span>
    <div class="mt-3">
      <span><b>트래픽 상위 IP</b></span>
      <div class="CPU-calendar">
        <span><input type="date" id="startDate"/></span>
        <span><input type="date" id="endDate"/></span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Traffic",
  mounted() {
    document.getElementById(
      "startDate"
    ).value = new Date().toISOString().substring(0, 10);
    document.getElementById(
      "endDate"
    ).value = new Date().toISOString().substring(0, 10);
  }
};
</script>

<style></style>
