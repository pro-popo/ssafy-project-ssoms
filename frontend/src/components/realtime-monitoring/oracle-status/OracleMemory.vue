<template>
  <div style="height:15vh">
    <!-- <h2>Oracle Memory</h2> -->
    <v-icon size="20" color="var(--main-sub-color)">mdi-memory</v-icon>
    <span> Memory </span>

    <div class="oracle-memory">
      <v-card elevation="2">
        <v-card-text class="oracle-data">
          <div>
            <span class="oracle-status-name">Block Gets Per Sec</span>
          </div>
          <h1>
            {{ getDbBlockGetsPerSec[getDbBlockGetsPerSec.length - 1] }}
            <span class="oracle-unit">block</span>
          </h1>
          <span></span>
        </v-card-text>
      </v-card>
      <v-card elevation="2">
        <v-card-text class="oracle-data">
          <div>
            <span class="oracle-status-name">Logical Reads Per Sec</span>
          </div>
          <h1>
            {{ getLogicalReadsPerSec[getLogicalReadsPerSec.length - 1] }}
            <span class="oracle-unit">reads</span>
          </h1>
          <span></span>
        </v-card-text>
      </v-card>
      <v-card elevation="2">
        <v-card-text class="oracle-data">
          <div>
            <span class="oracle-status-name">Redo Generated Per Sec</span>
          </div>
          <h1>
            {{ getRedoGeneratedPerSec[getRedoGeneratedPerSec.length - 1] }}
            <span class="oracle-unit">byte</span>
          </h1>
          <span></span>
        </v-card-text>
      </v-card>
      <OracleStorage />
    </div>

    <div v-if="false" class="oracle-memory-chart">
      <IEcharts :option="option1" />
      <IEcharts :option="option2" />
      <IEcharts :option="option3" />
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import OracleStorage from "@/components/realtime-monitoring/oracle-status/OracleStorage.vue";
import { mapGetters } from "vuex";
export default {
  name: "OracleMemory",
  components: {
    IEcharts,
    OracleStorage
  },
  computed: {
    ...mapGetters("Oracle", [
      "getDbBlockGetsPerSec",
      "getLogicalReadsPerSec",
      "getRedoGeneratedPerSec"
    ]),
    ...mapGetters(["getRealTimeList"])
  },
  watch: {
    getDbBlockGetsPerSec: function() {
      this.option1.xAxis.data = this.getRealTimeList;
      this.option2.xAxis.data = this.getRealTimeList;
      this.option3.xAxis.data = this.getRealTimeList;
      this.option1.series[0].data = this.getDbBlockGetsPerSec;
      this.option2.series[0].data = this.getLogicalReadsPerSec;
      this.option3.series[0].data = this.getRedoGeneratedPerSec;
    }
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
          data: []
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
            data: [],
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
          data: []
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
            data: [],
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
          data: []
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
  height: 90%;
}
.oracle-memory .v-card {
  width: 25%;
  margin-right: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.oracle-memory .v-card:last-child {
  margin-right: 0px !important;
}
</style>
