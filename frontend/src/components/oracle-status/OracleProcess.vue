<template>
  <div>
    <h2>Oracle Process</h2>
    <v-icon size="20" color="var(--main-sub-color)"
      >mdi-arrow-decision-outline</v-icon
    >
    <span> Process </span>
    <div class="oracle-process-chart">
      <IEcharts :option="option1" />
      <IEcharts :option="option2" />
      <IEcharts :option="option3" />
      <IEcharts :option="option4" />
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
      executionsPerSec: 3.86, //- 실행 비율 (실행 / 초)
      totalParseCountPerSec: 1.24, //- 구문분석 (총구문분석 / 초)
      openCursorsPerSec: 1.44, // - 열린 커서 (커서 / 초)
      userCommitsPerSec: 0.83, //- 커밋 비율 (커밋 / 초)

      option1: {
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
          data: ["executions"]
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "executions",
            data: [0.33, 0.35, 0.43, 0.44, 0.42, 0.33, 0.31, 0.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      },
      option2: {
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
          data: ["totalParseCount"]
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "totalParseCount",
            data: [0.33, 0.35, 0.43, 0.44, 0.42, 0.33, 0.31, 0.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      },
      option3: {
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
          data: ["openCursors"]
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "openCursors",
            data: [0.33, 0.35, 0.43, 0.44, 0.42, 0.33, 0.31, 0.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      },
      option4: {
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
          data: ["userCommits"]
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "userCommits",
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
.oracle-process-chart {
  display: flex;
  width: auto;
  height: 300px;
  border: 1px solid var(--font-sub-color);
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 300px;
  color: var(--font-sub2-color);
}
</style>
