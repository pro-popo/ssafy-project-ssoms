<template>
  <div class="oracle-process">
    <!-- <v-card elevation="2">
        <v-card-text class="oracle-data">
          <div>
            <div style="display:flex">
              <h4 class="oracle-status-name">Executions (per sec)</h4>
            </div>
            <div style="display:flex;">
              <h1>
                {{ getExecutionsPerSec[getExecutionsPerSec.length - 1] }}
                <span class="oracle-unit">%</span>
              </h1>
            </div>
            <div v-if="changedExecutions == 0">
              <v-icon>mdi-menu-up</v-icon>
              <span>0</span>
            </div>
            <div
              v-else
              :class="changedExecutions > 0 ? 'data-increase' : 'data-decrease'"
            >
              <v-icon v-if="changedExecutions > 0">
                mdi-menu-up
              </v-icon>
              <v-icon v-if="changedExecutions <= 0">
                mdi-menu-down
              </v-icon>
              <span>{{ changedExecutions }}</span>
            </div>
          </div> 
        </v-card-text>
      </v-card> -->
    <v-card elevation="2">
      <v-card-text class="oracle-data">
        <div>
          <div style="display:flex">
            <h4 class="oracle-status-name">Total Parse Count</h4>
            <span style="color:var(--font-sub-color);margin-left:5px">
              (per sec)</span
            >
          </div>
          <div style="display:flex;">
            <h1>
              {{
                getTotalParseCountPerSec[getTotalParseCountPerSec.length - 1]
              }}
              <span class="oracle-unit">count</span>
            </h1>

            <!-- <div style="height:30%">
                <IEcharts :option="option2" class="small-chart" />
              </div> -->
          </div>
          <div v-if="changedTotalParseCount == 0">
            <v-icon>mdi-menu-up</v-icon>
            <span>0</span>
          </div>
          <div
            v-else
            :class="
              changedTotalParseCount > 0 ? 'data-increase' : 'data-decrease'
            "
          >
            <v-icon v-if="changedTotalParseCount > 0">
              mdi-menu-up
            </v-icon>
            <v-icon v-if="changedTotalParseCount <= 0">
              mdi-menu-down
            </v-icon>
            <span>{{ changedTotalParseCount }}</span>
          </div>
        </div>

        <!-- <div class="oracle-title-icon">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <v-icon v-bind="attrs" v-on="on" size="25" dark
                  >mdi-arrow-decision-outline</v-icon
                >
              </template>
              <span>Process</span>
            </v-tooltip>
          </div> -->
      </v-card-text>
    </v-card>
    <v-card elevation="2">
      <v-card-text class="oracle-data">
        <div>
          <div style="display:flex">
            <h4 class="oracle-status-name">Open Cursors</h4>
            <span style="color:var(--font-sub-color);margin-left:5px">
              (per sec)</span
            >
          </div>
          <div style="display:flex;">
            <h1>
              {{ getOpenCursorsPerSec[getOpenCursorsPerSec.length - 1] }}
              <span class="oracle-unit">cursor</span>
            </h1>
            <!-- <div style="height:30%">
                <IEcharts :option="option3" class="small-chart" />
              </div> -->
          </div>
          <div v-if="changedOpenCursors == 0">
            <v-icon>mdi-menu-up</v-icon>
            <span>0</span>
          </div>
          <div
            v-else
            :class="changedOpenCursors > 0 ? 'data-increase' : 'data-decrease'"
          >
            <v-icon v-if="changedOpenCursors > 0">
              mdi-menu-up
            </v-icon>
            <v-icon v-if="changedOpenCursors <= 0">
              mdi-menu-down
            </v-icon>
            <span>{{ changedOpenCursors }}</span>
          </div>
        </div>

        <!-- <div class="oracle-title-icon">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <v-icon v-bind="attrs" v-on="on" size="25" dark
                  >mdi-arrow-decision-outline</v-icon
                >
              </template>
              <span>Process</span>
            </v-tooltip>
          </div> -->
      </v-card-text>
    </v-card>
    <v-card elevation="2">
      <v-card-text class="oracle-data">
        <div>
          <div style="display:flex">
            <h4 class="oracle-status-name">User Commit</h4>
            <span style="color:var(--font-sub-color);margin-left:5px">
              (per sec)</span
            >
          </div>
          <div style="display:flex;">
            <h1>
              {{ getUserCommitsPerSec[getUserCommitsPerSec.length - 1] }}
              <span class="oracle-unit">commit</span>
            </h1>
            <!-- <div style="height:30%">
                <IEcharts :option="option4" class="small-chart" />
              </div> -->
          </div>
          <div v-if="changedUserCommits == 0">
            <v-icon>mdi-menu-up</v-icon>
            <span>0</span>
          </div>
          <div
            v-else
            :class="changedUserCommits > 0 ? 'data-increase' : 'data-decrease'"
          >
            <v-icon v-if="changedUserCommits > 0">
              mdi-menu-up
            </v-icon>
            <v-icon v-if="changedUserCommits <= 0">
              mdi-menu-down
            </v-icon>
            <span>{{ changedUserCommits }}</span>
          </div>
        </div>

        <!-- <div class="oracle-title-icon">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <v-icon v-bind="attrs" v-on="on" size="25" dark
                  >mdi-arrow-decision-outline</v-icon
                >
              </template>
              <span>Process</span>
            </v-tooltip>
          </div> -->
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
// import IEcharts from "vue-echarts-v3/src/full.js";
import { mapGetters } from "vuex";

export default {
  name: "OracleProcess",
  components: {
    // IEcharts
  },
  computed: {
    ...mapGetters("Oracle", [
      "getExecutionsPerSec",
      "getTotalParseCountPerSec",
      "getOpenCursorsPerSec",
      "getUserCommitsPerSec"
    ]),
    ...mapGetters(["getRealTimeList"]),
    changedExecutions: function() {
      if (this.getExecutionsPerSec.length <= 1) return 0;
      return (
        this.getExecutionsPerSec[this.getExecutionsPerSec.length - 2] -
        this.getExecutionsPerSec[this.getExecutionsPerSec.length - 1]
      ).toFixed(2);
    },
    changedTotalParseCount: function() {
      if (this.getTotalParseCountPerSec.length <= 1) return 0;
      return (
        this.getTotalParseCountPerSec[
          this.getTotalParseCountPerSec.length - 2
        ] -
        this.getTotalParseCountPerSec[this.getTotalParseCountPerSec.length - 1]
      ).toFixed(2);
    },
    changedOpenCursors: function() {
      if (this.getOpenCursorsPerSec.length <= 1) return 0;
      return (
        this.getOpenCursorsPerSec[this.getOpenCursorsPerSec.length - 2] -
        this.getOpenCursorsPerSec[this.getOpenCursorsPerSec.length - 1]
      ).toFixed(2);
    },
    changedUserCommits: function() {
      if (this.getUserCommitsPerSec.length <= 1) return 0;
      return (
        this.getUserCommitsPerSec[this.getUserCommitsPerSec.length - 2] -
        this.getUserCommitsPerSec[this.getUserCommitsPerSec.length - 1]
      ).toFixed(2);
    }
  },
  watch: {
    getExecutionsPerSec: function() {
      this.option1.xAxis.data = this.getRealTimeList;
      this.option2.xAxis.data = this.getRealTimeList;
      this.option3.xAxis.data = this.getRealTimeList;
      this.option4.xAxis.data = this.getRealTimeList;

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
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          splitLine: {
            show: false
          },
          show: false
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: false
          },
          show: false
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none"
          }
        },
        series: [
          {
            name: "executions",
            data: [],
            type: "line",
            color: "#B39DDB",
            showSymbol: false,
            areaStyle: ""
          }
        ]
      },

      option2: {
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          splitLine: {
            show: false
          },
          show: false
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: false
          },
          show: false
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none"
          }
        },
        series: [
          {
            name: "totalParseCount",
            data: [],
            type: "line",
            color: "#B39DDB",
            showSymbol: false,
            areaStyle: ""
          }
        ]
      },
      option3: {
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          splitLine: {
            show: false
          },
          show: false
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: false
          },
          show: false
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none"
          }
        },
        series: [
          {
            name: "openCursors",
            data: [],
            type: "line",
            color: "#B39DDB",
            showSymbol: false,
            areaStyle: ""
          }
        ]
      },
      option4: {
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          splitLine: {
            show: false
          },
          show: false
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: false
          },
          show: false
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none"
          }
        },
        series: [
          {
            name: "userCommits",
            data: [],
            type: "line",
            color: "#B39DDB",
            showSymbol: false,
            areaStyle: ""
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

.oracle-process {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}
.oracle-process .v-card {
  margin-right: 15px;
  width: 33.33%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.oracle-process .v-card:last-child {
  margin-right: 0px !important;
}
.oracle-data {
  display: flex;
  align-items: center;
  height: 100%;
}
.oracle-data h1 {
  margin-top: 5px;
  color: #6440e3;
}
</style>
