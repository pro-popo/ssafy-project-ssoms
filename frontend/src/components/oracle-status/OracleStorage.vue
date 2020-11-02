<template>
  <div>
    <h2>Oracle Storage</h2>
    <div class="oracle-storage-chart">
      <v-icon size="20" color="var(--main-sub-color)"
        >mdi-file-cog-outline</v-icon
      >
      <span> File </span>

      <IEcharts :option="option" />
    </div>
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
        // title: { text: "Storage" },
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
.oracle-storage-chart {
  width: 50%;
  height: 300px;
  border: 1px solid var(--font-sub-color);
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 30px;
  color: var(--font-sub2-color);
}
</style>
