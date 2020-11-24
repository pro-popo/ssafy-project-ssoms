<template>
  <v-row>
    <v-col cols="6">
      <v-card elevation="2" height="350px">
        <v-card-subtitle align="center"
          ><h3 style="color: #333333; font-size: 20px">Oracle DB CPU Time</h3>
        </v-card-subtitle>
        <v-divider></v-divider>
        <v-card-text style="width: 100%; height: 85%">
          <div @click="clickOracleChart" style="width: 100%; height: 100%">
            <IEcharts :option="chart1" :resizable="true" />
          </div>
        </v-card-text>
      </v-card>
    </v-col>
    <v-col cols="6">
      <v-card elevation="2" height="350px">
        <v-card-subtitle align="center"
          ><h3 style="color: #333333; font-size: 20px">All Schema's Status</h3>
        </v-card-subtitle>
        <v-divider></v-divider>
        <v-card-text style="width: 100%; height: 85%">
          <IEcharts :option="chart2" :resizable="true" />
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapActions, mapMutations, mapGetters } from "vuex";
import echarts from "echarts/lib/echarts";
//import SERVER from "@/api/spring.js";
//import axios from "axios";

export default {
  name: "SchemaWhole",
  components: {
    IEcharts,
  },
  computed: {
    ...mapGetters("Schema", ["getTimeAndCpuList"]),
    ...mapGetters("Schema", ["getPastTimeData"]),
    chart1() {
      return {
        grid: {
          top: 20,
        },
        tooltip: {
          trigger: "axis",
          triggerOn: "click",
          position: function (pt) {
            return [pt[0], "10%"];
          },
          //
          axisPointer: {
            type: "line",
            label: {
              background: "#000000",
              show: true,
              snap: true,
              formatter: function (params) {
                this.setPastTimeData(params.value);
                return params.value;
              }.bind(this),
            },
          },
        },
        // title: {
        //   left: "center",
        //   text: "CPU Time",
        //   padding: [15, 0]
        // },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: this.getTimeAndCpuList.time,
          axisLine: {
            lineStyle: {
              color: "#303030",
            },
          },
          axisPointer: {
            handle: {
              show: true,
              size: [0, 0],
            },
            // value: this.selectedRealTime
          },
        },
        yAxis: {
          type: "value",
          boundaryGap: [0, "100%"],
        },
        dataZoom: [
          {
            type: "inside",
          },
          {
            handleIcon:
              "M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z",
            handleSize: "80%",
            handleStyle: {
              shadowBlur: 3,
              shadowColor: "rgba(0, 0, 0, 0.6)",
              shadowOffsetX: 2,
              shadowOffsetY: 2,
            },
            bottom: 0,
          },
        ],
        series: [
          {
            name: "databaseCpuTimeRatio",
            type: "line",
            smooth: true,
            sampling: "average",
            itemStyle: {
              color: "#1e88df",
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "#2196F3",
                },
                {
                  offset: 1,
                  color: "#280d8c",
                },
              ]),
            },
            data: this.getTimeAndCpuList.cpu,
          },
        ],
      };
    },
    chart2() {
      return {
        color: this.$store.state.graphColor,
        tooltip: {},

        legend: {
          data: this.getPastTimeData.schemaList,
          bottom: 5,
        },
        // title: {
        //   text: "All Schema's Status",
        //   left: "center",
        //   padding: [15, 0]
        // },
        radar: {
          // shape: 'circle',
          radius: "60%",
          splitNumber: 4,
          nameGap: 15,
          center: ["50%", "45%"],
          name: {
            textStyle: {
              color: "#fff",
              backgroundColor: "#999",
              borderRadius: 5,
              padding: [5, 7],
            },
          },
          indicator: [
            { name: "BufferGetsAvg" },
            { name: "CpuTimeAvg" },
            { name: "CpuTimeMax" },
            { name: "CpuTimeTot" },
            { name: "SqlCnt" },
          ],
        },
        series: [
          {
            name: "Schema status",
            type: "radar",
            // areaStyle: {normal: {}},
            data: this.getPastTimeData.radarchart,
          },
        ],
      };
    },
  },
  methods: {
    ...mapActions("Schema", ["setPastTimeData"]),
    ...mapMutations("Schema", [
      "SET_PAST_TIME_DATA",
      "SET_PAST_TIME_DATA_CHECK",
    ]),
    clickOracleChart() {
      this.SET_PAST_TIME_DATA_CHECK();
    },
  },
};
</script>

<style>
.whole-box {
  display: flex;
  height: 320px;
  /* padding-left: 50px; */
}
.whole-query-box1 {
  margin: 10px;
}
.whole-query-box2 {
  margin: 10px;
}

.width_0p {
  width: 0%;
  /*transform: scaleX(0%);*/
}
.width_100p {
  width: 100%;
  /*transform: scaleX(100%);*/
}
.v_hiden {
  visibility: hidden !important;
}
.width_50p {
  width: 50% !important;
  /*transition-property: all;
    transition-duration: 0.5s;
    transition-timing-function: ease-in;*/
}
</style>
