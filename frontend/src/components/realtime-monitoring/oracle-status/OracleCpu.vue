<template>
  <div>
    <v-card elevation="1" height="300px">
      <v-card-text class="test">
        <IEcharts :option="line" />
      </v-card-text>
    </v-card>
    <div class="serial-session">
      <v-icon size="20" color="var(--main-sub-color)">mdi-responsive</v-icon>
      <span> Response Time Per Transaction</span>
      <h1 class="session-cnt">
        {{ getResponesTimePerTxn[getResponesTimePerTxn.length - 1] }}
        <span style="font-size:30px;">sec</span>
      </h1>
    </div>
    <div class="serial-session">
      <v-icon size="20" color="var(--main-sub-color)">mdi-connection</v-icon>
      <span> Active Serial Sessions </span>
      <h1 class="session-cnt">
        {{ getActiveSerialSessions[getActiveSerialSessions.length - 1] }}
        <span style="font-size:30px;">cnt</span>
      </h1>
    </div>

    <v-divider></v-divider>
    <div class="oracle-status-chart">
      <div style="margin-bottom:10px">
        <v-icon size="20" color="var(--main-sub-color)"
          >mdi-desktop-classic</v-icon
        >
        <span> CpuTime & WaitTime Ratio</span>
      </div>
      <IEcharts :option="pie" />
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
            type: "line",
            color: "#2196F3"
          },
          {
            name: "WaitTime Ratio",
            data: [],
            areaStyle: "",
            type: "line",
            color: "#4CAF50"
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
.test {
  height: 300px;
}
.oracle-cpu-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  color: var(--font-sub2-color);
  border: 1px solid var(--font-sub-color);
  border-radius: 10px;
  margin-bottom: 30px;
}
.serial-session {
  /* background: #f0f0f0; */
  border-radius: 10px;
  width: 250px;
  height: 100px;
  margin-bottom: 10px;
  padding: 10px;
}
.session-cnt {
  color: #2196f3;
  padding-left: 25px;
  font-size: 50px;
}
.oracle-status-chart {
  margin: 20px 0px;
  width: 100%;
  height: 300px;
  padding: 0px 10px;
}
.oracle-status-chart > span {
  margin-bottom: 100px;
}
</style>
