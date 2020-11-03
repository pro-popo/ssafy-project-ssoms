<template>
  <div>
    <h2>Oracle Memory</h2>
    <v-icon size="20" color="var(--main-sub-color)">mdi-memory</v-icon>
    <span> Memory </span>
    <div class="oracle-memory-chart">
      <IEcharts :option="option1" />
      <IEcharts :option="option2" />
      <IEcharts :option="option3" />
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "OracleMemory",
  components: {
    IEcharts
  },
  data() {
    return {
      dbBlockGetsPerUserCall: 0.65, //- DB 블록 비율 ( / 초) T
      logicalReadsPerUserCall: 2.44, //- 논리적 읽기 ( / 초) T
      redoGeneratedPerSec: 792.25, //- 생성된 리두(바이트 / 초) T

      option1: {
        title: {
          text: "BlockGets",
          subtext: "PerSec"
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["14:20", "14:25", "14:30", "14:35", "14:40", "14:45", "14:50"]
        },
        yAxis: {
          type: "value"
        },
        legend: {
          data: ["BlockGets"],
          bottom: 0
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "BlockGets",
            data: [0.33, 0.35, 0.43, 0.44, 0.42, 0.33, 0.31, 0.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      },
      option2: {
        title: {
          text: "LogicalReads",
          subtext: "PerSec"
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["14:20", "14:25", "14:30", "14:35", "14:40", "14:45", "14:50"]
        },
        yAxis: {
          type: "value"
        },
        legend: {
          data: ["logicalReads"],
          bottom: 0
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "logicalReads",
            data: [0.33, 0.35, 0.43, 0.44, 0.42, 0.33, 0.31, 0.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      },
      option3: {
        title: {
          text: "RedoGenerated",
          subtext: "PerSec"
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["14:20", "14:25", "14:30", "14:35", "14:40", "14:45", "14:50"]
        },
        yAxis: {
          type: "value"
        },
        legend: {
          data: ["redoGenerated"],
          bottom: 0
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "redoGenerated",
            data: [0.33, 0.35, 0.43, 0.44, 0.42, 0.33, 0.31, 0.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      }
    };
  }
};
</script>

<style>
.oracle-memory-chart {
  display: flex;
  width: auto;
  height: 300px;
  border: 1px solid var(--font-sub-color);
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 30px;
  color: var(--font-sub2-color);
}
</style>
