<template>
  <div>
    <v-tabs v-model="tab">
      <v-tab href="#tab-1">CPU</v-tab>
      <v-tab href="#tab-2">Memory</v-tab>
    </v-tabs>
    <v-btn-toggle group mandatory color="#2196F3" v-model="toggle_exclusive">
      <v-btn @click="changeChart('bar')"><v-icon>mdi-chart-bar</v-icon></v-btn>
      <v-btn @click="changeChart('line')"
        ><v-icon>mdi-chart-line</v-icon></v-btn
      >
      <v-btn @click="changeChart('areaspline')"
        ><v-icon>mdi-chart-areaspline-variant</v-icon></v-btn
      >
    </v-btn-toggle>
    <v-tabs-items v-model="tab">
      <v-tab-item :value="'tab-1'">
        <div class="oracle-storage-chart">
          <IEcharts :option="option" @click="onClick" />
        </div>
      </v-tab-item>
      <v-tab-item :value="'tab-2'">
        <div class="oracle-storage-chart">
          <IEcharts :option="option" @click="onClick" />
        </div>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "SchemaChart",
  components: {
    IEcharts
  },
  methods: {
    onClick(event, instance, ECharts) {
      console.log(event);
      console.log(instance);
      console.log(ECharts);
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
        element.color = this.color[cnt++];
      });
    }
  },
  data() {
    return {
      tab: null,
      toggle_exclusive: 1,
      color: ["#2196F3", "#4CAF50"],
      option: {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "line"
          },
          formatter: function(params, ticket, callback) {
            console.log("formatter params ", params);
            console.log("formatter ticket ", ticket);
            console.log("formatter ticket ", callback);
            return "Loading";
          }
        },

        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["14:25", "14:30", "14:35", "14:40", "14:45", "14:50"]
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            name: "C##TESTDB",
            type: "line",
            // stack: "resource",
            areaStyle: null,
            color: "#2196F3",
            data: [302, 301, 334, 390, 330, 320]
          },
          {
            name: "C##TESTDB2",
            type: "line",
            // stack: "resource",
            areaStyle: null,
            color: "#4CAF50",
            data: [120, 132, 101, 134, 90, 230, 210]
          }
        ]
      }
    };
  }
};
</script>

<style>
.oracle-storage-chart {
  width: 100%;
  height: 300px;
  border: 1px solid var(--font-sub-color);
  border-radius: 10px;
  padding: 15px 10px;
  margin-bottom: 30px;
  color: var(--font-sub2-color);
}
.v-btn-toggle button {
  border-radius: 100% !important;
  margin: 4px 0px !important;
}
</style>
