<template>
  <div class="schema-chart-box">
    <div>
      <h2>Executions</h2>
      <v-card elevation="2" class="schema-chart-size">
        <IEcharts :option="option1" />
      </v-card>
    </div>
    <div>
      <h2>CpuTimeTotal</h2>
      <v-card elevation="2" class="schema-chart-size">
        <IEcharts :option="option2" />
      </v-card>
    </div>
    <div>
      <h2>ElapsedTimeTotal</h2>
      <v-card elevation="2" class="schema-chart-size">
        <IEcharts :option="option3" />
      </v-card>
    </div>
    <!-- <v-card elevation="10">
      <IEcharts :option="option4" />
    </v-card> -->
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
            return (value.min - value.min * 0.05).toFixed(2);
          },
          max: function(value) {
            return (value.max + value.max * 0.05).toFixed(2);
          }
        },
        series: []
      },
      option3: {
        // title: {
        //   text: "cpuTimeAvg"
        // },
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
            return (value.min - value.min * 0.05).toFixed(2);
          },
          max: function(value) {
            return (value.max + value.max * 0.05).toFixed(2);
          }
        },
        series: []
      },
      option4: {
        title: {
          text: "elapsedTimeAvg"
        },
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
    ...mapGetters("Schema", [
      "getRealTimeSchemaList1",
      "getRealTimeSchemaList2",
      "getRealTimeSchemaList3",
      "getSchemaList"
    ]),
    ...mapGetters(["getRealTime", "getRealTimeList"])
  },
  watch: {
    getRealTimeSchemaList1: {
      deep: true,
      handler() {
        var legendList = [];
        for (var i = 0; i < this.getRealTimeSchemaList1.length; i++) {
          legendList.push(this.getRealTimeSchemaList1[i].name);
          this.option1.series[i].name = this.getRealTimeSchemaList1[i].name;
          this.option1.series[i].data = this.getRealTimeSchemaList1[i].data;
        }
        this.option1.legend.data = legendList;
        this.option1.xAxis.data = this.getRealTimeList;
      }
    },
    getRealTimeSchemaList2: {
      deep: true,
      handler() {
        var legendList = [];
        for (var i = 0; i < this.getRealTimeSchemaList2.length; i++) {
          legendList.push(this.getRealTimeSchemaList2[i].name);
          this.option2.series[i].name = this.getRealTimeSchemaList2[i].name;
          this.option2.series[i].data = this.getRealTimeSchemaList2[i].data;
        }
        this.option2.legend.data = legendList;
        this.option2.xAxis.data = this.getRealTimeList;
      }
    },
    getRealTimeSchemaList3: {
      deep: true,
      handler() {
        var legendList = [];
        for (var i = 0; i < this.getRealTimeSchemaList3.length; i++) {
          legendList.push(this.getRealTimeSchemaList3[i].name);
          this.option3.series[i].name = this.getRealTimeSchemaList3[i].name;
          this.option3.series[i].data = this.getRealTimeSchemaList3[i].data;
        }
        this.option3.legend.data = legendList;
        this.option3.xAxis.data = this.getRealTimeList;
      }
    },
    getSchemaList: {
      handler() {
        if (this.option1.series.length === 0) {
          for (var i = 0; i < this.getSchemaList.length; i++) {
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
      }
    }
  }
  // mounted() {
  //   console.log("길이측정", this.getSchemaList);
  // },
};
</script>

<style>
.schema-chart-box {
  display: flex;
  justify-content: space-between;
  margin: 20px 0px;
  height: 330px;
}
.schema-chart-box > div {
  width: 22%;
  min-width: 220px;
}
.schema-chart-size {
  height: 250px;
}
</style>
