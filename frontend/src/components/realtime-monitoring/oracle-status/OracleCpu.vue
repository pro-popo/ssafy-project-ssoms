<template>
  <div>
    <div class="oracle-cpu">
      <div class="oracle-cpu-card1">
        <v-card elevation="2">
          <v-card-text style="height:100%">
            <div v-if="true" style="height:100%">
              <div>
                <v-icon color="var(--main-sub-color)"
                  >mdi-desktop-classic</v-icon
                >
                <span class="oracle-status-name">
                  CpuTime & WaitTime Ratio</span
                >
              </div>
              <IEcharts :option="line" />
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
        color: ["#2196F3", "#4CAF50"],

        // title: { text: "Storage" },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: "value",
          max: 100
        },
        legend: {
          data: ["CpuTime Ratio", "WaitTime Ratio"]
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "CpuTime Ratio",
            data: [],
            type: "line"
          },
          {
            name: "WaitTime Ratio",
            data: [],
            areaStyle: "",
            type: "line"
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
  justify-content: space-between;
  height: 30vh;
}
.oracle-cpu-card1 {
  width: 70%;
  height: 100%;
  margin-right: 10px;
}
.oracle-cpu-card1 .v-card {
  height: 100%;
  width: 100%;
}
.oracle-cpu-card2 {
  display: flex;
  flex-direction: column;
  width: 30%;
  height: 100%;
  margin-left: 10px;
}
.oracle-cpu-card2 .v-card:nth-child(1) {
  margin-bottom: 10px;
}
.oracle-cpu-card2 .v-card:nth-child(2) {
  margin-top: 10px;
}
.oracle-cpu-card2 .v-card {
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
