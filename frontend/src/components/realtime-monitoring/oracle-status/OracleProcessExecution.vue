<template>
  <v-card elevation="2" style="height:100%; margin-right:15px">
    <v-card-text style="height:100%; display:flex; flex-direction: column;">
      <div style="height:30%; display:flex; flex-direction: column;">
        <div style="display:flex">
          <h3 class="oracle-status-name">
            Executions
          </h3>
          <span style="color:var(--font-sub-color);margin-left:5px"> /sec</span>
        </div>
        <!-- <div style="display:flex;" class="oracle-data">
          <h1>
            {{ getExecutionsPerSec[getExecutionsPerSec.length - 1] }}
            <span class="oracle-unit">%</span>
          </h1>
        </div> -->
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
      this.option.series[0].data[0].value = this.getExecutionsPerSec[
        this.getExecutionsPerSec.length - 1
      ];
      this.option.series[0].data[0].name = "Excutions";
      this.option.series[0].data[1].value =
        100 - this.option.series[0].data[0].value;
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
          bottom: 0
        },

        series: [
          {
            type: "pie",
            radius: ["60%", "90%"],
            label: {
              show: true,
              formatter: function(event) {
                if (event.data.name == "") return "";
                return (
                  "{b|" +
                  event.data.name +
                  "}" +
                  "\n {c|" +
                  event.data.value +
                  "} {d|%}"
                );
              },
              rich: {
                b: {
                  color: "gray",
                  fontSize: "12",
                  fontWeight: "bold"
                },
                c: {
                  fontSize: "20",
                  fontWeight: "bold",
                  padding: [0, 0, 5, 0]
                },
                d: {
                  fontSize: "16",
                  fontWeight: "bold",
                  padding: [0, 0, 5, 0]
                }
              },
              position: "center"
            },

            labelLine: {
              show: false
            },
            color: ["#2196F3", "#E0E0E0"],
            data: [
              {
                value: 0,
                name: ""
              },
              {
                value: 100,
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
  margin-top: -5px;
  height: 100% !important;
  width: 100% !important;
}
.execution-pie * {
  position: inherit !important;
}
</style>
