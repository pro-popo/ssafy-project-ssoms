<template>
  <div>
    <div class="oracle-cpu">
      <div class="oracle-cpu-card1">
        <v-card elevation="2">
          <v-card-text style="height:100%">
            <div v-if="true" style="height:100%; width:100%;">
              <div class="oracle-title-icon">
                <v-icon id="chart-title-icon" size="18" dark
                  >mdi-desktop-classic</v-icon
                >
                <span class="oracle-status-name"> CPU Time</span>
                <v-btn small icon style="margin: -10px 25px 0px auto">
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
                <!-- <span style="margin-left:auto;font-size:12px">단위(%)</span> -->
              </div>
              <div style="height:100%">
                <IEcharts :option="line" style="padding-top:5px" />
              </div>
            </div>

            <div v-if="false" style="height:100%">
              <div>
                <v-icon color="var(--main-sub-color)"
                  >mdi-desktop-classic</v-icon
                >
                <span class="oracle-status-name">
                  CpuTime & WaitTime Ratio</span
                >
              </div>
              <IEcharts :option="pie" />
            </div>
          </v-card-text>
        </v-card>
      </div>
      <div class="oracle-cpu-card2">
        <div class="oracle-cpu-card2-2">
          <v-card elevation="2">
            <v-card-text class="oracle-cpu-data">
              <v-icon color="white">mdi-timeline-clock</v-icon>
              <div>
                <span class="oracle-status-name">
                  Response Time Per Transaction</span
                >
                <h1>
                  {{ getResponesTimePerTxn[getResponesTimePerTxn.length - 1] }}
                  <span class="oracle-unit">sec</span>
                </h1>
                <span></span>
              </div>
              <span></span>
            </v-card-text>
          </v-card>

          <v-card elevation="2">
            <v-card-text class="oracle-cpu-data">
              <v-icon color="white">mdi-connection</v-icon>
              <div>
                <span class="oracle-status-name"> Active Serial Sessions </span>
                <h1>
                  {{
                    getActiveSerialSessions[getActiveSerialSessions.length - 1]
                  }}
                  <span class="oracle-unit">count</span>
                </h1>
                <span></span>
              </div>
              <span></span>
            </v-card-text>
          </v-card>
        </div>
        <div class="oracle-cpu-card2-1">
          <OracleStorage />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import OracleStorage from "@/components/realtime-monitoring/oracle-status/OracleStorage.vue";
import { mapGetters } from "vuex";
export default {
  name: "OracleCpu",
  components: {
    IEcharts,
    OracleStorage
  },
  computed: {
    ...mapGetters("Oracle", [
      "getDatabaseCpuTimeRatioList",
      "getDatabaseWaitTimeRatio",
      "getActiveSerialSessions",
      "getResponesTimePerTxn"
    ]),
    ...mapGetters(["getRealTimeList"])
  },
  watch: {
    getDatabaseCpuTimeRatioList: function() {
      this.line.series[0].data = this.getDatabaseCpuTimeRatioList;
      this.line.series[1].data = this.getDatabaseWaitTimeRatio;

      this.pie.series[0].data[0].value = this.getDatabaseCpuTimeRatioList[
        this.getDatabaseCpuTimeRatioList.length - 1
      ];
      this.pie.series[0].data[1].value = this.getDatabaseWaitTimeRatio[
        this.getDatabaseWaitTimeRatio.length - 1
      ];

      this.line.xAxis.data = this.getRealTimeList;
    }
  },
  data() {
    return {
      activeSerialSessions: [],

      line: {
        color: ["#81D4FA", "#42A5F5"],
        grid: {
          right: 40,
          left: 50,
          bottom: 20,
          top: 65
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
          name: "(%)",
          type: "value",
          max: 100,
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisTick: {
            show: false
          }
        },
        legend: {
          data: ["CpuTime", "WaitTime"],
          icon: "roundRect"
          // top: "30
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "CpuTime",
            data: [],
            areaStyle: "",
            type: "line",
            showSymbol: false
          },
          {
            name: "WaitTime",
            data: [],
            areaStyle: "",
            type: "line",
            showSymbol: false
          }
        ]
      },
      pie: {
        // title: {
        //   text: "Status"
        // },
        legend: {
          orient: "vertical",
          right: 10,
          data: ["CpuTime", "WaitTime"]
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
            color: ["#2196F3", "#4CAF50"],
            data: [
              {
                value: 0,
                name: "CpuTime"
              },
              {
                value: 0,
                name: "WaitTime"
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
.oracle-cpu {
  display: flex;
  height: 30vh;
}
.oracle-cpu-card1 {
  width: 50%;
  margin-right: 15px;
}
.oracle-cpu-card1 .v-card {
  height: 100%;
  width: 100%;
}

.oracle-cpu-card2 {
  width: 50%;
  display: flex;
}

.oracle-cpu-card2-1 .v-card {
  height: 100%;
  width: 100%;
}
.oracle-cpu-card2-1 {
  width: 50%;
  margin-left: 15px;
}

.oracle-cpu-card2-2 {
  display: flex;
  flex-direction: column;
  width: 50%;
  height: 100%;
}
.oracle-cpu-card2-2 .v-card:nth-child(1) {
  margin-bottom: 15px;
}

.oracle-cpu-card2-2 .v-card {
  height: 50%;
  width: 100%;
}

.oracle-cpu-data {
  height: 100%;
  display: flex;
}
.oracle-cpu-data > .v-icon {
  width: 10vh;
  height: 100%;
  background: linear-gradient(
    to bottom right,
    var(--main-color),
    var(--main-point-color)
  );
  border-radius: 8px;
  margin-right: 16px;
  font-size: 1.5rem;
}
.oracle-cpu-data div {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.oracle-cpu-data h1 {
  margin-top: 5px;
  color: #6440e3;
}
.oracle-unit {
  font-size: 1.4rem;
  color: var(--main-point-color);
}
</style>
