<template>
  <div>
    <h2>Oracle Status</h2>
    <div class="oracle-status-container">
      <div class="serial-session">
        <v-icon size="20" color="var(--main-sub-color)">mdi-connection</v-icon>
        <span> Active Serial Sessions</span>
        <h1 class="session-cnt">
          {{ activeSerialSessions }}
          <span style="font-size:30px;">cnt</span>
        </h1>
      </div>
      <v-divider></v-divider>
      <div class="oracle-status-chart">
        <v-icon size="20" color="var(--main-sub-color)"
          >mdi-desktop-classic</v-icon
        >
        <span> CpuTime & WaitTime Ratio</span>
        <IEcharts :option="pie" />
      </div>
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "OracleStatus",
  components: {
    IEcharts
  },
  data() {
    return {
      databaseCpuTimeRatio: 93.59, // (%)
      databaseWaitTimeRatio: 6.41, //(%)
      activeSerialSessions: 1.0, //(cnt)
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
                value: 93.59,
                name: "CpuTime"
              },
              {
                value: 6.41,
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
.oracle-status-container {
  width: 50%;
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
  width: 450px;
  height: 300px;
  padding: 0px 10px;
}</style
>s
