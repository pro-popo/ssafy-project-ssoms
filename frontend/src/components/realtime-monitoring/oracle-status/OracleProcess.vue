<template>
  <div style="height:15vh; margin:20px 0px 10px 0px">
    <!-- <h2>Oracle Process</h2> -->

    <div class="oracle-process">
      <v-card elevation="2">
        <v-card-text class="oracle-data">
          <div>
            <div>
              <span class="oracle-status-name">Executions Per Sec</span>
            </div>
            <div style="display:flex">
              <h1>
                {{ getExecutionsPerSec[getExecutionsPerSec.length - 1] }}
                <span class="oracle-unit">%</span>
              </h1>
              <IEcharts :option="option1" />
            </div>
          </div>
          <!-- <div><IEcharts :option="option1" /></div> -->
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
            <div>
              <span class="oracle-status-name">Total Parse Count Per Sec</span>
            </div>
            <h1>
              {{
                getTotalParseCountPerSec[getTotalParseCountPerSec.length - 1]
              }}
              <span class="oracle-unit">count</span>
            </h1>
          </div>
          <div>
            <IEcharts :option="option2" />
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
            <div>
              <span class="oracle-status-name">Open Cursors Per Sec</span>
            </div>
            <h1>
              {{ getOpenCursorsPerSec[getOpenCursorsPerSec.length - 1] }}
              <span class="oracle-unit">cursor</span>
            </h1>
          </div>
          <div>
            <IEcharts :option="option3" />
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
            <div>
              <span class="oracle-status-name">User Commit Per Sec</span>
            </div>
            <h1>
              {{ getUserCommitsPerSec[getUserCommitsPerSec.length - 1] }}
              <span class="oracle-unit">commit</span>
            </h1>
          </div>
          <div>
            <IEcharts :option="option4" />
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
            color: "#2196F3",
            showSymbol: false
          }
        ]
      },
      option1123: {
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
            color: "#2196F3",
            showSymbol: false
          }
        ]
      },
      option2: {
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
            color: "#2196F3",
            showSymbol: false
          }
        ]
      },
      option3: {
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
            color: "#2196F3",
            showSymbol: false
          }
        ]
      },
      option4: {
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
            color: "#2196F3",
            showSymbol: false
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
.oracle-data {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}
.oracle-data h1 {
  margin-top: 5px;
  color: #6440e3;
}

.small-chart {
  height: 120px !important;
  width: 100px !important;
  /* top: -30px !important; */
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  position: initial;
}
</style>
