<template>
  <div>
    <span>CPU</span> |
    <span>Memory</span>
    <div class="mt-3">
      <span><b>전체 CPU 점유율</b></span>
      <div class="CPU-calendar">
        <span><input type="date" id="startDate"/></span>
        <span><input type="date" id="endDate"/></span>
      </div>
    </div>
    <div id="chart" style="width: 600px;height:400px;"></div>
  </div>
</template>

<script>
export default {
  name: "PCResource",

  mounted() {
    document.getElementById(
      "startDate"
    ).value = new Date().toISOString().substring(0, 10);
    document.getElementById(
      "endDate"
    ).value = new Date().toISOString().substring(0, 10);
  }
  // created() {
  //   var myChart = echarts.init(document.getElementById("main"));
  // }
};
</script>

<style>
.CPU-calendar {
  float: right;
}
</style>
