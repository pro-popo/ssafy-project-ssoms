<template>
  <div style="height: 100%">
    <v-card elevation="2" style="height: 100%">
      <v-card-text class="oracle-storage">
        <div class="oracle-storage-chart">
          <div class="storage-title-icon">
            <h3 class="storage-status-name">File</h3>
            <div style="margin-left: auto; padding-right: 25px">
              <v-menu offset-y attach>
                <template v-slot:activator="{ attrs, on }">
                  <v-btn small icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>
                <div style="background: white">
                  <v-btn-toggle
                    group
                    mandatory
                    color="#039BE5"
                    v-model="toggle_exclusive"
                    style="display: flex; flex-direction: column"
                  >
                    <v-btn small icon @click="changeChart('bar')"
                      ><v-icon>mdi-chart-bar</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart('line')"
                      ><v-icon>mdi-chart-line</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart('areaspline')"
                      ><v-icon>mdi-chart-areaspline-variant</v-icon></v-btn
                    >
                  </v-btn-toggle>
                </div>
              </v-menu>
            </div>
          </div>
          <div style="height: 100%">
            <IEcharts :option="option" :resizable="true" />
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapGetters } from "vuex";
export default {
  name: "OracleStorage",
  components: {
    IEcharts,
  },
  methods: {
    changeChart(type) {
      let areaStyle = null;
      if (type == "areaspline") {
        areaStyle = "";
        type = "line";
      }
      if (type == "bar") {
        this.option.xAxis.boundaryGap = true;
        this.option.tooltip.axisPointer.type = "shadow";
      } else {
        this.option.xAxis.boundaryGap = false;
        this.option.tooltip.axisPointer.type = "line";
      }
      let cnt = 0;
      this.option.series.forEach((element) => {
        element.type = type;
        element.areaStyle = areaStyle;
        element.color = this.option.color[cnt++];
      });
    },
  },
  computed: {
    ...mapGetters("Oracle", [
      "getPhysicalReadsPerSec",
      "getPhysicalWritesPerSec",
    ]),
    ...mapGetters("Realtime", ["getRealTimeList"]),
  },
  watch: {
    getPhysicalReadsPerSec: function () {
      this.option.xAxis.data = this.getRealTimeList;
      this.option.series[0].data = this.getPhysicalReadsPerSec;
      this.option.series[1].data = this.getPhysicalWritesPerSec;
    },
  },
  data() {
    return {
      physicalReadsPerSec: 0.0, // (mb / 초)
      physicalWritesPerSec: 0.33, // (mb / 초)
      toggle_exclusive: 1,
      option: {
        color: ["#81D4FA", "#42A5F5"],
        grid: {
          right: 30,
          left: 35,
          bottom: 30,
          top: 65,
        },
        // title: { text: "CPU Time" },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          // axisTick: {
          //   show: false
          // }
        },
        yAxis: {
          name: "(md)",
          type: "value",
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },

          axisTick: {
            show: false,
          },
          max: function (item) {
            if (item.max > 10) return (item.max.toFixed(0) / 10) * 10 + 10;
            else if (item.max > 5) return 10;
            else return Math.ceil(item.max);
          },
        },
        legend: {
          data: ["Physical Reads", "Physical Writes"],
          // icon: "circle",
          // bottom: 0,
          top: 3,
          textStyle: {
            fontSize: 10,
          },
        },
        tooltip: {
          trigger: "axis",
          position: ["-50%", "50%"],
          axisPointer: {
            type: "line",
          },
        },
        series: [
          {
            name: "Physical Reads",
            data: [],
            areaStyle: null,
            type: "line",
            showSymbol: false,
          },
          {
            name: "Physical Writes",
            data: [],
            areaStyle: null,
            type: "line",
            showSymbol: false,
          },
        ],
      },
    };
  },
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
.storage-title-icon {
  display: flex;
  align-items: center;
  position: absolute;
  justify-content: space-between;
  z-index: 2;
  width: 100%;
}

.storage-title-icon :nth-child(2) {
  color: var(--font-sub2-color);
  font-size: 16px;
  font-weight: bold;
}

.storage-logo-icon {
  background: linear-gradient(
    to bottom right,
    var(--main-color),
    var(--main-point-color)
  );
  border-radius: 100px;
  height: 30px;
  width: 30px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
}
</style>
