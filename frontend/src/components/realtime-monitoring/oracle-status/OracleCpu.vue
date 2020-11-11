<template>
  <div class="oracle-cpu">
    <div class="oracle-cpu-card1">
      <v-card elevation="2">
        <v-card-text style="height:100%; display:flex">
          <div class="oracle-title-icon">
            <!-- <v-icon id="chart-title-icon" size="18" dark
              >mdi-desktop-classic</v-icon
            > -->
            <h3 class="oracle-status-name">CPU & Wait Time</h3>
            <v-btn small icon style="margin: -10px 25px 0px auto">
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </div>

          <div style="height:100%; width:70%;">
            <div style="height:100%">
              <IEcharts :option="line" style="padding-top:5px" @click="test" />
            </div>
          </div>
          <div style="height:95%; width:30%;">
            <IEcharts :option="gauge" style="margin-top:30px" />
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
              <h4 class="oracle-status-name">
                Response Time Per Transaction
              </h4>
              <h1 style="display:flex">
                {{ getResponesTimePerTxn[getResponesTimePerTxn.length - 1] }}
                <span class="oracle-cpu-unit">sec</span>
                <div style="height:50%">
                  <IEcharts :option="small1" class="small-chart" />
                </div>
              </h1>
            </div>

            <span></span>
          </v-card-text>
        </v-card>

        <v-card elevation="2">
          <v-card-text class="oracle-cpu-data">
            <v-icon color="white">mdi-connection</v-icon>
            <div>
              <h4 class="oracle-status-name">Active Serial Sessions</h4>
              <h1 style="display:flex;">
                <span>{{
                  getActiveSerialSessions[getActiveSerialSessions.length - 1]
                }}</span>
                <span class="oracle-cpu-unit">count</span>
                <div style="height:50%; width:auto">
                  <IEcharts :option="small2" class="small-chart" />
                </div>
              </h1>
            </div>
            <span></span>
          </v-card-text>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapGetters } from "vuex";
export default {
  name: "OracleCpu",
  components: {
    IEcharts
  },
  methods: {
    test(params) {
      console.log("차트클릭!");
      console.log(params);
    }
  },
  computed: {
    ...mapGetters("Oracle", [
      "getDatabaseCpuTimeRatioList",
      "getDatabaseWaitTimeRatio",
      "getResponesTimePerTxn",
      "getActiveSerialSessions"
    ]),
    ...mapGetters(["getRealTimeList"])
  },
  watch: {
    getDatabaseCpuTimeRatioList: function() {
      this.line.series[0].data = this.getDatabaseCpuTimeRatioList;
      this.line.series[1].data = this.getDatabaseWaitTimeRatio;
      this.gauge.series[0].data[0].value = this.getDatabaseCpuTimeRatioList[
        this.getDatabaseCpuTimeRatioList.length - 1
      ];

      // this.pie.series[0].data[0].value = this.getDatabaseCpuTimeRatioList[
      //   this.getDatabaseCpuTimeRatioList.length - 1
      // ];
      // this.pie.series[0].data[1].value = this.getDatabaseWaitTimeRatio[
      //   this.getDatabaseWaitTimeRatio.length - 1
      // ];

      this.line.xAxis.data = this.getRealTimeList;
      this.small1.xAxis.data = this.getRealTimeList;
      this.small2.xAxis.data = this.getRealTimeList;

      this.small1.series[0].data = this.getResponesTimePerTxn;
      this.small2.series[0].data = this.getActiveSerialSessions;
    }
  },
  data() {
    return {
      activeSerialSessions: [],

      line: {
        color: ["#81D4FA", "#42A5F5"],
        grid: {
          right: 20,
          left: 50,
          bottom: 30,
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
          // triggerEvent: true
          // formatter: function(params, callback) {
          //   console.log(callback);
          //   this.test(params);
          // }.bind(this)
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
          trigger: "axis",
          axisPointer: {
            type: "line",
            triggerOn: "click",
            label: {
              show: false
              // formatter: function(params) {
              //   this.test(params);
              //   return null;
              // }.bind(this)
            }
          }
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
      gauge: {
        series: [
          {
            name: "CpuTime",
            type: "gauge",
            detail: {
              formatter: "{value}%",
              show: true,
              color: "rgba(92, 92, 92, 1)",
              offsetCenter: ["0", "50%"],
              fontSize: 22
            },
            data: [
              {
                value: 0,
                name: "CpuTime"
              }
            ],
            radius: "80%",
            startAngle: 180,
            endAngle: 0,
            splitNumber: 5,
            axisLine: {
              show: true,
              lineStyle: {
                color: [
                  [0.2, "#EF5350"],

                  [0.8, "#039BE5"],
                  [1, "#4CAF50"]
                ]
              }
            },
            splitLine: {
              show: true
            },
            axisTick: {
              show: true,
              splitNumber: 2,
              length: 6
            },
            axisLabel: {
              show: true,
              distance: -53
            },
            pointer: {
              show: true,
              length: "50%",
              width: 6
            },
            itemStyle: {
              color: "rgba(85, 85, 85, 1)"
            },
            title: {
              show: true,
              offsetCenter: [0, "85%"],
              color: "rgba(143, 143, 143, 1)",
              fontSize: 12,
              fontWeight: "bold"
            },
            markPoint: {
              data: [
                {
                  type: "max"
                }
              ]
            },
            animationEasing: "backOut"
          }
        ]
      },
      small1: {
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15
        },
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
            name: "ResponseTimePerTransaction",
            data: [],
            type: "line",
            color: "#4CAF50",
            showSymbol: false,
            areaStyle: ""
          }
        ]
      },
      small2: {
        grid: {
          right: 10,
          left: 10,
          bottom: 0,
          top: 15
        },
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
            name: "ActiveSerialSessions",
            data: [],
            type: "line",
            color: "#4CAF50",
            showSymbol: false,
            areaStyle: ""
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
  height: 100%;
}
.oracle-cpu-card1 {
  width: 75%;
  margin-right: 15px;
}
.oracle-cpu-card1 .v-card {
  height: 100%;
  width: 100%;
}

.oracle-cpu-card2 {
  width: 25%;
  display: flex;
}

.oracle-cpu-card2-1 .v-card {
  height: 100%;
  width: 100%;
}
.oracle-cpu-card2-1 {
  width: 30%;
  margin-left: 15px;
}

.oracle-cpu-card2-2 {
  display: flex;
  flex-direction: column;
  width: 100%;
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
.oracle-cpu-unit {
  margin: 1px 0px 0px 5px;
  font-size: 1.4rem;
  color: var(--main-point-color);
}
.oracle-unit {
  font-size: 1.4rem;
  color: var(--main-point-color);
}
</style>
