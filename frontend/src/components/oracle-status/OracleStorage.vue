<template>
  <div class="echart">
    <IEcharts :option="option" />
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "OracleStorage",
  components: {
    IEcharts
  },
  data() {
    return {
      physicalReadsPerSec: 0.0, // (mb / 초)
      physicalWritesPerSec: 0.33, // (mb / 초)
      option: {
        title: { text: "Storage" },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["14:20", "14:25", "14:30", "14:35", "14:40", "14:45", "14:50"]
        },
        yAxis: {
          type: "value"
        },
        legend: {
          data: ["Read Total", "Write Total"]
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "Read Total",
            data: [0.33, 0.35, 0.43, 0.44, 0.42, 0.33, 0.31, 0.3],
            type: "line",
            color: "#2196F3"
          },
          {
            name: "Write Total",
            data: [0.0, 0.01, 0.03, 0.05, 0.02, 0.01, 0.02, 0.02],
            type: "line",
            color: "#4CAF50"
          }
        ]
      }
    };
  }
};
</script>

<style>
.echart {
  width: 400px;
  height: 300px;
}
</style>
