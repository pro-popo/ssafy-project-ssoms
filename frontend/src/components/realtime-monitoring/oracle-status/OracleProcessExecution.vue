<template>
  <v-card elevation="2" style="height:100%; margin-right:15px">
    <v-card-text style="height:100%; display:flex; flex-direction: column;">
      <div style="height:30%; display:flex; flex-direction: column;">
        <div style="display:flex; align-items:center; margin-top:-5px">
          <h4 class="oracle-status-name">
            Executions
          </h4>
          <div style="margin-right:-10px">
            <v-menu offset-y attach>
              <template v-slot:activator="{ attrs, on }">
                <v-btn small icon v-bind="attrs" v-on="on">
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
              </template>
              <div style="background:white">
                <v-btn-toggle
                  group
                  mandatory
                  color="#039BE5"
                  v-model="toggle_exclusive"
                  style="display:flex;  flex-direction: column;"
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
        <div style="display:flex;" class="oracle-data">
          <h1>
            {{ getExecutionsPerSec[selectedRealTime] }}
            <span class="oracle-unit">count</span>
            <span class="oracle-status-units"> /sec</span>
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
      <div style="height:70%; width:auto;">
        <IEcharts :option="option" :resizable="true" />
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
// import OracleStorage from "@/components/realtime-monitoring/oracle-status/OracleStorage.vue";
import { mapGetters } from "vuex";
export default {
  name: "OracleCpu",
  components: {
    IEcharts
    // OracleStorage
  },
  methods: {
    changeChart(type) {
      console.log(type);
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
    }
  },
  computed: {
    ...mapGetters("Oracle", ["getExecutionsPerSec"]),
    ...mapGetters(["getRealTimeList", "selectedRealTime"]),

    changedExecutions: function() {
      if (this.selectedRealTime <= 1) return 0;
      return (
        this.getExecutionsPerSec[this.selectedRealTime] -
        this.getExecutionsPerSec[this.selectedRealTime - 1]
      ).toFixed(2);
    }
  },
  watch: {
    getExecutionsPerSec: function() {
      this.option.xAxis.data = this.getRealTimeList;
      this.option.series[0].data = this.getExecutionsPerSec;
    }
  },
  data() {
    return {
      executionsPerSec: 3.86,
      toggle_exclusive: 1,
      option: {
        color: ["#67abf6"],
        grid: {
          left: 30,
          bottom: 20,
          top: 40
        },
        // title: { text: "CPU Time" },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          }
          // axisTick: {
          //   show: false
          // }
        },
        yAxis: {
          type: "value",
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },

          axisTick: {
            show: false
          },
          max: function(item) {
            if (item.max > 10) return (item.max.toFixed(0) / 10) * 10 + 10;
            else if (item.max > 1) return 10;
            else return Math.ceil(item.max);
          }
        },

        tooltip: {
          trigger: "axis",
          position: ["100%", "50%"],
          axisPointer: {
            type: "line"
          }
        },

        series: [
          {
            name: "Excutions",
            data: [],
            areaStyle: null,
            type: "line",
            showSymbol: false
          }
        ]
      }
      // option: {
      //   // title: {
      //   //   text: "Status"
      //   // },
      //   grid: {
      //     width: "100%",
      //     height: "100%",
      //     right: 10,
      //     left: 10,
      //     bottom: 0
      //   },

      //   series: [
      //     {
      //       type: "pie",
      //       radius: ["60%", "90%"],
      //       label: {
      //         show: true,
      //         formatter: function(event) {
      //           if (event.data.name == "") return "";
      //           return (
      //             "{b|" +
      //             event.data.name +
      //             "}" +
      //             "\n {c|" +
      //             event.data.value +
      //             "} {d|%}"
      //           );
      //         },
      //         rich: {
      //           b: {
      //             color: "gray",
      //             fontSize: "12",
      //             fontWeight: "bold"
      //           },
      //           c: {
      //             fontSize: "20",
      //             fontWeight: "bold",
      //             padding: [0, 0, 5, 0]
      //           },
      //           d: {
      //             fontSize: "16",
      //             fontWeight: "bold",
      //             padding: [0, 0, 5, 0]
      //           }
      //         },
      //         position: "center"
      //       },

      //       labelLine: {
      //         show: false
      //       },
      //       color: ["#2196F3", "#E0E0E0"],
      //       data: [
      //         {
      //           value: 0,
      //           name: ""
      //         },
      //         {
      //           value: 100,
      //           name: ""
      //         }
      //       ]
      //     }
      //   ]
      // }
    };
  }
};
</script>

<style>
.execution-pie {
  margin-top: -5px;
}
.execution-pie * {
  position: inherit !important;
}
</style>
