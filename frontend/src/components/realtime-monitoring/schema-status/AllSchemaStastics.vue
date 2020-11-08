<template>
  <div class="chart1">
    <!-- <div
      v-for="schema in getRealTimeSchemaList"
      :key="schema.parsingSchemaName"
    >
      {{ schema.rowsProcessedAvg }}
    </div> -->
    <IEcharts :option="option" />
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
      testData: this.$store.state.Schema.realTimeSchemaList,
      option: {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          // data: ["A", "B", "C", "D", "E"]
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

          // boundaryGap: ["10%", "10%"]
        },
        series: [
          {
            name: "rowsProcessedAvg",
            type: "line",
            data: []
          }
          // {
          //   name: "B",
          //   type: "line",
          //   data: [220, 182, 191, 234, 290, 330, 310]
          // },
          // {
          //   name: "C",
          //   type: "line",
          //   data: [150, 232, 201, 154, 190, 330, 410]
          // },
          // {
          //   name: "D",
          //   type: "line",
          //   data: [320, 332, 301, 334, 390, 330, 320]
          // },
          // {
          //   name: "E",
          //   type: "line",
          //   data: [820, 932, 901, 934, 1290, 1330, 1320]
          // }
        ]
      }
    };
  },
  computed: {
    ...mapGetters("Schema", ["getRealTimeSchemaList"]),
    ...mapGetters(["getRealTime", "getRealTimeList"])
  },
  watch: {
    getRealTimeSchemaList: function(res) {
      this.option.series[0].data.push(res[0].executions);
      this.option.legend.data = [res.parsingSchemaName];
      this.option.series[0].name = res.parsingSchemaName;
      this.option.xAxis.data = this.getRealTimeList;
    }
  }
  // mounted() {
  //   setInterval(
  //     function() {
  //       console.log(this.getRealTimeSchemaList);
  //       this.option.series[0].data.push(
  //         this.getRealTimeSchemaList[0].rowsProcessedAvg
  //       );
  //     }.bind(this),
  //     2000
  //   );
  // },
  // created() {
  //   for (var i = 0; i < 5; i++) {
  //     this.option.series[0].data.push(i);
  //   }
  // }
};
</script>

<style>
.chart1 {
  height: 500px;
  width: 500px;
}
</style>
