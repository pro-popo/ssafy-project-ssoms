<template>
  <div class="schema-chart-box">
    <!-- {{ getSchemaList }} -->
    <v-card elevation="10">
      <IEcharts :option="option1" />
    </v-card>
    <v-card elevation="10">
      <IEcharts :option="option2" />
    </v-card>
    <v-card elevation="10">
      <IEcharts :option="option3" />
    </v-card>
    <v-card elevation="10">
      <IEcharts :option="option4" />
    </v-card>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "AllSchemaStastice",
  components: {
    IEcharts
  },
  data() {
    return {
      option1: {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          type: "scroll",
          data: []
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
          data: []
        },
        yAxis: {
          min: function(value) {
            return value.min - value.min * 0.05;
          },
          max: function(value) {
            return value.max + value.max * 0.05;
          }
        },
        series: []
      },
      option2: {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          type: "scroll",
          data: []
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
          data: []
        },
        yAxis: {
          min: function(value) {
            return value.min - value.min * 0.05;
          },
          max: function(value) {
            return value.max + value.max * 0.05;
          }
        },
        series: []
      },
      option3: {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          type: "scroll",
          data: []
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
          data: []
        },
        yAxis: {
          min: function(value) {
            return value.min - value.min * 0.05;
          },
          max: function(value) {
            return value.max + value.max * 0.05;
          }
        },
        series: []
      },
      option4: {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          type: "scroll",
          data: []
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
          data: []
        },
        yAxis: {
          min: function(value) {
            return value.min - value.min * 0.05;
          },
          max: function(value) {
            return value.max + value.max * 0.05;
          }
        },
        series: []
      }
    };
  },
  computed: {
    ...mapGetters("Schema", ["getRealTimeSchemaList", "getSchemaList"]),
    ...mapGetters(["getRealTime", "getRealTimeList"])
  },
  watch: {
    getRealTimeSchemaList: function(res) {
      var legendList = [];
      for (var i = 0; i < res.length; i++) {
        legendList.push(res[i].parsingSchemaName);
        this.option1.series[i].name = res[i].parsingSchemaName;
        this.option2.series[i].name = res[i].parsingSchemaName;
        this.option3.series[i].name = res[i].parsingSchemaName;
        this.option4.series[i].name = res[i].parsingSchemaName;
        this.option1.series[i].data.push(res[i].bufferGetsAvg);
        if (this.option1.series[i].data.length > 12) {
          this.option1.series[i].data.shift();
          this.option2.series[i].data.shift();
          this.option3.series[i].data.shift();
          this.option4.series[i].data.shift();
        }
        this.option2.series[i].data.push(res[i].rowsProcessedAvg);
        this.option3.series[i].data.push(res[i].cpuTimeAvg);
        this.option4.series[i].data.push(res[i].elapsedTimeAvg);
      }
      this.option1.legend.data = legendList;
      this.option2.legend.data = legendList;
      this.option3.legend.data = legendList;
      this.option4.legend.data = legendList;
      this.option1.xAxis.data = this.getRealTimeList;
      this.option2.xAxis.data = this.getRealTimeList;
      this.option3.xAxis.data = this.getRealTimeList;
      this.option4.xAxis.data = this.getRealTimeList;
    }
  },
  created() {
    for (var i = 0; i < 5; i++) {
      this.option1.series.push({
        name: "",
        type: "line",
        data: []
      });
      this.option2.series.push({
        name: "",
        type: "line",
        data: []
      });
      this.option3.series.push({
        name: "",
        type: "line",
        data: []
      });
      this.option4.series.push({
        name: "",
        type: "line",
        data: []
      });
    }
  }
};
</script>

<style>
.schema-chart-box {
  display: flex;
  justify-content: space-between;
  margin: 20px 0px;
  height: 250px;
}
.schema-chart-box > div {
  width: 22%;
  min-width: 220px;
}
</style>
