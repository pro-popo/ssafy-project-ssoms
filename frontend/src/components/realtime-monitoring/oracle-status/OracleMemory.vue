<template>
  <div class="oracle-memory">
    <v-card elevation="2">
      <v-card-text class="oracle-data">
        <div>
          <div style="display: flex">
            <h4 class="oracle-status-name">Block Gets</h4>
          </div>
          <div style="display: flex">
            <h1>
              {{ getDbBlockGetsPerSec[selectedRealTime] }}
              <span class="oracle-unit">block</span>
              <span class="oracle-status-units"> /sec</span>
            </h1>
          </div>
          <div v-if="changedDbBlockGets == 0">
            <v-icon>mdi-menu-up</v-icon>
            <span>0</span>
          </div>
          <div
            v-else
            :class="changedDbBlockGets > 0 ? 'data-increase' : 'data-decrease'"
          >
            <v-icon v-if="changedDbBlockGets > 0"> mdi-menu-up </v-icon>
            <v-icon v-if="changedDbBlockGets <= 0"> mdi-menu-down </v-icon>
            <span>{{ changedDbBlockGets }}</span>
          </div>
        </div>
      </v-card-text>
    </v-card>
    <v-card elevation="2">
      <v-card-text class="oracle-data">
        <div>
          <div style="display: flex">
            <h4 class="oracle-status-name">Logical Reads</h4>
          </div>

          <div style="display: flex">
            <h1>
              {{ getLogicalReadsPerSec[selectedRealTime] }}
              <span class="oracle-unit">reads</span>
              <span class="oracle-status-units"> /sec</span>
            </h1>
          </div>
          <div v-if="changedLogicalReads == 0">
            <v-icon>mdi-menu-up</v-icon>
            <span>0</span>
          </div>
          <div
            v-else
            :class="changedLogicalReads > 0 ? 'data-increase' : 'data-decrease'"
          >
            <v-icon v-if="changedLogicalReads > 0"> mdi-menu-up </v-icon>
            <v-icon v-if="changedLogicalReads <= 0"> mdi-menu-down </v-icon>
            <span>{{ changedLogicalReads }}</span>
          </div>
        </div>
      </v-card-text>
    </v-card>
    <v-card elevation="2">
      <v-card-text class="oracle-data">
        <div>
          <div style="display: flex">
            <h4 class="oracle-status-name">Redo Generated</h4>
          </div>
          <div style="display: flex">
            <h1>
              {{ getRedoGeneratedPerSec[selectedRealTime] }}
              <span class="oracle-unit">byte</span>
              <span class="oracle-status-units"> /sec</span>
            </h1>
          </div>
          <div v-if="changedRedoGenerated == 0">
            <v-icon>mdi-menu-up</v-icon>
            <span>0</span>
          </div>
          <div
            v-else
            :class="
              changedRedoGenerated > 0 ? 'data-increase' : 'data-decrease'
            "
          >
            <v-icon v-if="changedRedoGenerated > 0"> mdi-menu-up </v-icon>
            <v-icon v-if="changedRedoGenerated <= 0"> mdi-menu-down </v-icon>
            <span>{{ changedRedoGenerated }}</span>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "OracleMemory",

  computed: {
    ...mapGetters("Oracle", [
      "getDbBlockGetsPerSec",
      "getLogicalReadsPerSec",
      "getRedoGeneratedPerSec",
    ]),
    ...mapGetters("Realtime", ["getRealTimeList", "selectedRealTime"]),
    changedDbBlockGets: function () {
      if (this.selectedRealTime <= 1) return 0;

      return (
        this.getDbBlockGetsPerSec[this.selectedRealTime] -
        this.getDbBlockGetsPerSec[this.selectedRealTime - 1]
      ).toFixed(2);
    },
    changedLogicalReads: function () {
      if (this.selectedRealTime <= 1) return 0;
      return (
        this.getLogicalReadsPerSec[this.selectedRealTime] -
        this.getLogicalReadsPerSec[this.selectedRealTime - 1]
      ).toFixed(2);
    },
    changedRedoGenerated: function () {
      if (this.selectedRealTime <= 1) return 0;
      return (
        this.getRedoGeneratedPerSec[this.selectedRealTime] -
        this.getRedoGeneratedPerSec[this.selectedRealTime - 1]
      ).toFixed(2);
    },
  },
  watch: {
    getDbBlockGetsPerSec: function () {
      this.option1.xAxis.data = this.getRealTimeList;
      this.option2.xAxis.data = this.getRealTimeList;
      this.option3.xAxis.data = this.getRealTimeList;
      this.option1.series[0].data = this.getDbBlockGetsPerSec;
      this.option2.series[0].data = this.getLogicalReadsPerSec;
      this.option3.series[0].data = this.getRedoGeneratedPerSec;
    },
  },
  data() {
    return {
      dbBlockGetsPerUserCall: 0.65, //- DB 블록 비율 ( / 초) T
      logicalReadsPerUserCall: 2.44, //- 논리적 읽기 ( / 초) T
      redoGeneratedPerSec: 792.25, //- 생성된 리두(바이트 / 초) T

      option1: {
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          splitLine: {
            show: false,
          },
          show: false,
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: false,
          },
          show: false,
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none",
          },
        },
        series: [
          {
            name: "BlockGets",
            data: [],
            type: "line",
            color: "#B39DDB",
            showSymbol: false,
            areaStyle: "",
          },
        ],
      },
      option2: {
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          splitLine: {
            show: false,
          },
          show: false,
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: false,
          },
          show: false,
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none",
          },
        },
        series: [
          {
            name: "logicalReads",
            data: [],
            type: "line",
            color: "#B39DDB",
            showSymbol: false,
            areaStyle: "",
          },
        ],
      },
      option3: {
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          splitLine: {
            show: false,
          },
          show: false,
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: false,
          },
          show: false,
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none",
          },
        },
        series: [
          {
            name: "redoGenerated",
            data: [],
            type: "line",
            color: "#B39DDB",
            showSymbol: false,
            areaStyle: "",
          },
        ],
      },
    };
  },
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
.oracle-memory {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}
.oracle-memory .v-card {
  width: 33.33%;
  margin-right: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.oracle-memory .v-card:last-child {
  margin-right: 0px !important;
}
</style>
