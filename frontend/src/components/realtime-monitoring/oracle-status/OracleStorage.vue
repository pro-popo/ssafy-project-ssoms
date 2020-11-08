<template>
  <v-card>
    <div style="margin-top:-25px">
      <v-icon size="20" color="var(--main-sub-color)"
        >mdi-file-cog-outline</v-icon
      >
      <span> File </span>
    </div>
    <div class="oracle-storage-chart">
      <IEcharts :option="option" />
    </div>
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
          data: ["Read Total", "Write Total"]
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "Read Total",
            data: [],
            type: "line",
            color: "#2196F3"
          },
          {
            name: "Write Total",
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
</style>
