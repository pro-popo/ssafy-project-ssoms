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

            <div style="margin: 0px 25px 0px auto">
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

          <div style="height:100%; width:70%;">
            <div style="height:100%" @click="selectedAxis">
              <IEcharts
                :option="option"
                style="padding-top:5px"
                :resizable="true"
              />
            </div>
          </div>
          <div style="height:95%; width:30%;">
            <IEcharts
              :option="gauge"
              style="margin-top:30px"
              :resizable="true"
            />
          </div>
        </v-card-text>
      </v-card>
    </div>
    <div class="oracle-cpu-card2">
      <div class="oracle-cpu-card2-2">
        <v-card elevation="2">
          <v-card-text class="oracle-cpu-data">
            <v-icon color="white">mdi-timeline-clock</v-icon>
            <div
              style="display:flex; flex-direction:column; justify-contents:center"
            >
              <h4>
                Response Time Per Transaction
              </h4>
              <h1>
                {{ getResponesTimePerTxn[selectedRealTime] }}
                <span class="oracle-cpu-unit">sec</span>
                <div style="height:50%">
                  <IEcharts
                    :option="small1"
                    class="small-chart"
                    :resizable="true"
                  />
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
              <h4>Active Serial Sessions</h4>
              <h1>
                <span>{{ getActiveSerialSessions[selectedRealTime] }}</span>
                <span class="oracle-cpu-unit">count</span>
                <div style="height:50%; width:auto">
                  <IEcharts
                    :option="small2"
                    class="small-chart"
                    :resizable="true"
                  />
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
import { mapGetters, mapMutations } from "vuex";

export default {
  name: "OracleCpu",
  components: {
    IEcharts
  },
  methods: {
    ...mapMutations(["SET_SELECTED_REALTIME", "SET_SETTING_SELECTED"]),
    changeXaxis(params) {
      var setTime = 0;
      if (!this.getIsSelected) {
        setTime = 100;
      }
      setTimeout(
        function() {
          if (params.seriesData[0] !== undefined && this.getIsSelected) {
            console.log("얘는 오라클111111");
            this.SET_SELECTED_REALTIME(params.seriesData[0].dataIndex);
          } else {
            console.log("얘는 오라클222222");
            this.SET_SELECTED_REALTIME(this.getRealTimeList.length - 1);
          }
        }.bind(this),
        setTime
      );
    },

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
    },
    selectedAxis() {
      this.SET_SETTING_SELECTED(true);
      this.testData = true;
    }
  },
  computed: {
    ...mapGetters("Oracle", [
      "getDatabaseCpuTimeRatioList",
      "getDatabaseWaitTimeRatio",
      "getResponesTimePerTxn",
      "getActiveSerialSessions"
    ]),
    ...mapGetters([
      "getRealTimeList",
      "selectedRealTime",
      "getIsSelected",
      "getIsRealShow"
    ])
  },
  created() {
    if (this.getDatabaseCpuTimeRatioList.length - 1 != 0) {
      setTimeout(
        function() {
          this.gauge.series[0].data[0].value = this.getDatabaseCpuTimeRatioList[
            this.getDatabaseCpuTimeRatioList.length - 1
          ];
        }.bind(this),
        1500
      );
    }
  },
  watch: {
    selectedRealTime: function(res) {
      this.gauge.series[0].data[0].value = this.getDatabaseCpuTimeRatioList[
        this.selectedRealTime
      ];
      // res는 변한 값
      this.option.xAxis.axisPointer.value = res;
    },
    getDatabaseCpuTimeRatioList: function() {
      this.option.series[0].data = this.getDatabaseCpuTimeRatioList;
      this.option.series[1].data = this.getDatabaseWaitTimeRatio;
      // this.gauge.series[0].data[0].value = this.getDatabaseCpuTimeRatioList[
      //   this.getDatabaseCpuTimeRatioList.length - 1
      // ];

      // this.pie.series[0].data[0].value = this.getDatabaseCpuTimeRatioList[
      //   this.getDatabaseCpuTimeRatioList.length - 1
      // ];
      // this.pie.series[0].data[1].value = this.getDatabaseWaitTimeRatio[
      //   this.getDatabaseWaitTimeRatio.length - 1
      // ];

      this.option.xAxis.data = this.getRealTimeList;
      this.small1.xAxis.data = this.getRealTimeList;
      this.small2.xAxis.data = this.getRealTimeList;

      this.small1.series[0].data = this.getResponesTimePerTxn;
      this.small2.series[0].data = this.getActiveSerialSessions;
    }
  },
  data() {
    return {
      activeSerialSessions: [],
      toggle_exclusive: 2,
      testData: false,
      option: {
        color: ["#81D4FA", "#42A5F5"],
        grid: {
          right: 20,
          left: 50,
          bottom: 25,
          top: 65
        },
        // title: { text: "CPU Time" },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          triggerEvent: true,
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisPointer: {
            handle: {
              show: true
            },
            value: this.selectedRealTime
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
        methods: {},
        tooltip: {
          trigger: "axis",
          triggerOn: "click",
          // alwaysShowContent: true,
          axisPointer: {
            type: "line",
            label: {
              background: "#ffff",
              show: true,
              formatter: function(params) {
                this.changeXaxis(params);
                return params.value;
              }.bind(this)
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
              color: "#6440e3",
              offsetCenter: ["0", "50%"],
              fontSize: 22,
              fontWeight: "bold"
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
              offsetCenter: [0, "80%"],
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
            color: "#039BE5",
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
            color: "#039BE5",
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
  align-items: center;
}
.oracle-cpu-data > .v-icon {
  width: 9vh;
  height: 90%;
  background: linear-gradient(
    to bottom right,
    var(--main-color),
    var(--main-point-color)
  );
  border-radius: 8px;
  margin-right: 16px;
  font-size: 1.5rem;
}

.oracle-cpu-data h1 {
  color: #6440e3;
  display: flex;
  align-items: flex-end;
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
.v-menu__content {
  margin: 5px 0px 0px -4px !important;
}
</style>
