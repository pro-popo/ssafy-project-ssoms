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
        title: {
          text: "Executions",
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
          data: ["executions"],
          bottom: 0
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "executions",
            data: [3.86, 3.35, 3.43, 3.24, 2.52, 2.83, 3.31, 3.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      },
      option2: {
        title: {
          text: "TotalParseCount",
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
          data: ["totalParseCount"],
          bottom: 0
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "totalParseCount",
            data: [1.24, 1.35, 1.43, 1.44, 1.52, 1.33, 1.31, 1.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      },
      option3: {
        title: {
          text: "OpenCursors",
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
          data: ["openCursors"],
          bottom: 0
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "openCursors",
            data: [1.44, 1.35, 1.43, 1.44, 1.42, 1.33, 1.31, 1.3],
            type: "line",
            color: "#2196F3"
          }
        ]
      },
      option4: {
        title: {
          text: "UserCommits",
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
          data: ["userCommits"],
          bottom: 0
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "userCommits",
            data: [0.83, 0.65, 0.73, 0.74, 0.72, 0.75, 0.81, 0.82],
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
  margin-bottom: 50px;
  color: var(--font-sub2-color);
}
</style>
