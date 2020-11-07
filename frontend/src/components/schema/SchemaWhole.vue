<template>
  <div class="whole-box">
    <div class="whole-query-box1">
      <IEcharts :option="chart1" />
    </div>
    <div class="whole-query-box2">
      <IEcharts :option="chart2" />
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "SchemaWhole",
  components: {
    IEcharts
  },
  data() {
    return {
      chart1: {
        xAxis: {
          type: "category",
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: "line"
          },
          {
            data: [700, 500, 400, 350, 600, 1200, 740],
            type: "line"
          },
          {
            data: [1000, 700, 300, 500, 700, 1000, 1100],
            type: "line"
          }
        ]
      },
      chart2: {
        tooltip: {},
        legend: {
          data: ["预算分配（Allocated Budget）", "实际开销（Actual Spending）"]
        },
        radar: {
          // shape: 'circle',
          name: {
            textStyle: {
              color: "#fff",
              backgroundColor: "#999",
              borderRadius: 3,
              padding: [3, 5]
            }
          },
          indicator: [
            { name: "销售（sales）", max: 6500 },
            { name: "管理（Administration）", max: 16000 },
            { name: "信息技术（Information Techology）", max: 30000 },
            { name: "客服（Customer Support）", max: 38000 },
            { name: "研发（Development）", max: 52000 },
            { name: "市场（Marketing）", max: 25000 }
          ]
        },
        series: [
          {
            name: "预算 vs 开销（Budget vs spending）",
            type: "radar",
            // areaStyle: {normal: {}},
            data: [
              {
                value: [4300, 10000, 28000, 35000, 50000, 19000],
                name: "预算分配（Allocated Budget）"
              },
              {
                value: [5000, 14000, 28000, 31000, 42000, 21000],
                name: "实际开销（Actual Spending）"
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
.whole-box {
  display: flex;
  height: 320px;
  /* padding-left: 50px; */
}
.whole-query-box1 {
  border: 1px solid black;
  margin: 10px;
  width: 40vw;
}
.whole-query-box2 {
  border: 1px solid black;
  margin: 10px;
  width: 30vw;
}
</style>
