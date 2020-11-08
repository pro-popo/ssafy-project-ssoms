<template>
  <div style="height:15vh; margin-bottom:10px">
    <!-- <h2>Oracle Process</h2> -->
    <v-icon size="20" color="var(--main-sub-color)"
      >mdi-arrow-decision-outline</v-icon
    >
    <span> Process </span>
    <div class="oracle-process">
      <v-card elevation="2">
        <v-card-text class="oracle-data">
          <!-- <div>
            <v-icon>mdi-arrow-decision-outline</v-icon>
          </div> -->
          <div>
            <div>
              <span class="oracle-status-name">Executions Per Sec</span>
            </div>
            <h1>
              {{ getExecutionsPerSec[getExecutionsPerSec.length - 1] }}
              <span class="oracle-unit">%</span>
            </h1>
          </div>
        </v-card-text>
      </v-card>
      <v-card elevation="2">
        <v-card-text class="oracle-data">
          <div>
            <span class="oracle-status-name">Total Parse Count Per Sec</span>
          </div>
          <h1>
            {{ getTotalParseCountPerSec[getTotalParseCountPerSec.length - 1] }}
            <span class="oracle-unit">count</span>
          </h1>
          <span></span>
        </v-card-text>
      </v-card>
      <v-card elevation="2">
        <v-card-text class="oracle-data">
          <div>
            <span class="oracle-status-name">Open Cursors Per Sec</span>
          </div>
          <h1>
            {{ getOpenCursorsPerSec[getOpenCursorsPerSec.length - 1] }}
            <span class="oracle-unit">cursor</span>
          </h1>
          <span></span>
        </v-card-text>
      </v-card>
      <v-card elevation="2">
        <v-card-text class="oracle-data">
          <div>
            <span class="oracle-status-name">User Commit Per Sec</span>
          </div>
          <h1>
            {{ getUserCommitsPerSec[getUserCommitsPerSec.length - 1] }}
            <span class="oracle-unit">count</span>
          </h1>
          <span></span>
        </v-card-text>
      </v-card>
    </div>
    <div v-if="false" class="oracle-process-chart">
      <IEcharts :option="option1" />
      <IEcharts :option="option2" />
      <IEcharts :option="option3" />
      <IEcharts :option="option4" />
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapGetters } from "vuex";

export default {
  name: "OracleMemory",
  components: {
    IEcharts
  },
  computed: {
    ...mapGetters("Oracle", [
      "getExecutionsPerSec",
      "getTotalParseCountPerSec",
      "getOpenCursorsPerSec",
      "getUserCommitsPerSec"
    ]),
    ...mapGetters(["getRealTimeList"])
  },
  watch: {
    getExecutionsPerSec: function() {
      this.option1.xAxis.data = this.getRealTimeList;
      this.option2.xAxis.data = this.getRealTimeList;
      this.option3.xAxis.data = this.getRealTimeList;

      this.option1.series[0].data = this.getExecutionsPerSec;
      this.option2.series[0].data = this.getTotalParseCountPerSec;
      this.option3.series[0].data = this.getOpenCursorsPerSec;
      this.option4.series[0].data = this.getUserCommitsPerSec;
    }
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
          data: []
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
            data: [],
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
          data: []
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
            data: [],
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
          data: []
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
            data: [],
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
          data: []
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
            data: [],
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

.oracle-process {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 90%;
}
.oracle-process .v-card {
  width: 25%;
  margin-right: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.oracle-process .v-card:last-child {
  margin-right: 0px !important;
}
/* .oracle-data {
  display: flex;
} */
.oracle-data h1 {
  margin-top: 5px;
  color: #6440e3;
}
</style>
