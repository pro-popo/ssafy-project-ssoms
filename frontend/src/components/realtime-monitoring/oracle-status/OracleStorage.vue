<template>
  <v-card elevation="2">
    <v-card-text class="oracle-storage">
      <div class="oracle-title-icon">
        <!-- <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-icon v-bind="attrs" v-on="on" size="23" dark
              >mdi-file-cog-outline</v-icon
            >
          </template>
          <span>File</span>
        </v-tooltip> -->
      </div>
      <div class="oracle-storage-chart">
        <div style="display:flex; justify-content: space-between;">
          <div>
            <v-icon color="var(--main-sub-color)">mdi-file-cog-outline</v-icon>
            <span class="oracle-status-name"> File</span>
          </div>
          <span style="font-size:12px">단위: mb</span>
        </div>
        <IEcharts :option="option" class="oracle-chart" />
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapGetters } from "vuex";
export default {
  name: "OracleStorage",
  components: {
    IEcharts
  },
  computed: {
    ...mapGetters("Oracle", [
      "getPhysicalReadsPerSec",
      "getPhysicalWritesPerSec"
    ]),
    ...mapGetters(["getRealTimeList"])
  },
  watch: {
    getPhysicalReadsPerSec: function() {
      this.option.xAxis.data = this.getRealTimeList;
      this.option.series[0].data = this.getPhysicalReadsPerSec;
      this.option.series[1].data = this.getPhysicalWritesPerSec;
    }
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
          data: []
        },
        yAxis: {
          type: "value"
        },
        legend: {
          data: ["Physical Reads", "Physical Writes"],
          bottom: 0
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "Physical Reads",
            data: [],
            type: "line",
            color: "#2196F3"
          },
          {
            name: "Physical Writes",
            data: [],
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
/* .oracle-storage-chart {
  width: 50%;
  height: 300px;
  border: 1px solid var(--font-sub-color);
  border-radius: 10px;
  padding: 15px 10px;
  margin-bottom: 30px;
  color: var(--font-sub2-color);
} */
.oracle-storage {
  height: 100%;
}
.oracle-storage-chart {
  height: 100%;
  width: 100%;
}
</style>
