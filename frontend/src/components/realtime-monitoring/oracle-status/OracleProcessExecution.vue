<template>
  <v-card elevation="2" style="height:100%; margin-right:15px">
    <v-card-text style="height:100%; display:flex; flex-direction: column;">
      <div style="height:30%; display:flex; flex-direction: column;">
        <div>
          <h4 class="oracle-status-name">Executions</h4>
        </div>
        <div style="displayx:flex;" class="oracle-data">
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

      <div style="height:70%">
        <IEcharts :option="option" class="execution-pie" />
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
  computed: {
    ...mapGetters("Oracle", ["getExecutionsPerSec"]),
    ...mapGetters(["getRealTimeList"]),
    changedExecutions: function() {
      if (this.getExecutionsPerSec.length <= 1) return 0;
      return (
        this.getExecutionsPerSec[this.getExecutionsPerSec.length - 2] -
        this.getExecutionsPerSec[this.getExecutionsPerSec.length - 1]
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
      option: {
        // title: {
        //   text: "Status"
        // },
        grid: {
          width: "100%",
          height: "100%",
          right: 10,
          left: 10,
          bottom: 0,
          top: 15
        },
        legend: {
          orient: "vertical",
          right: 10,
          data: ["CpuTime"]
        },
        series: [
          {
            type: "pie",
            radius: ["40%", "65%"],
            label: {
              show: false,
              position: "center"
            },
            emphasis: {
              label: {
                show: true,
                lineHeight: 25,
                padding: [-12, 0, 0, 0],
                // fontSize: "20",

                formatter: "{b|{b}}\n{c|{c}} {d|%}",
                rich: {
                  b: {
                    color: "gray",
                    fontSize: "12",
                    fontWeight: "bold"
                  },
                  c: {
                    fontSize: "25",
                    fontWeight: "bold"
                  },
                  d: {
                    fontSize: "20",
                    fontWeight: "bold"
                  }
                }
              }
            },
            labelLine: {
              show: false
            },
            color: ["#2196F3", "#E0E0E0"],
            data: [
              {
                value: 0,
                name: "Excutions"
              },
              {
                value: 0,
                name: ""
              }
            ]
          }
        ]
      }
    };
  }
};
</script>

<style>
.execution-pie {
  height: 100% !important;
  width: 100% !important;
}
.execution-pie * {
  position: inherit !important;
}
</style>
