<template>
  <div>
    <span>IP</span> |
    <span>테이블</span>
    <div class="mt-3">
      <span><b>트래픽 상위 IP</b></span>
      <div class="CPU-calendar">
        <span><input type="date" id="startDate"/></span>
        <span><input type="date" id="endDate"/></span>
      </div>
    </div>
    <IEcharts :option="option" style="height: 500px" id="chart1" />
    <v-btn @click="test">test</v-btn>
    <!-- {{ data }} -->
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";

// var data = [];
// var now = +new Date(1997, 9, 3);
// var oneDay = 24 * 3600 * 1000;
// var value = Math.random() * 1000;
// for (var i = 0; i < 1000; i++) {
//   data.push(randomData());
// }
// function randomData() {
//   now = new Date(+now + oneDay);
//   value = value + Math.random() * 21 - 10;
//   return {
//     name: now.toString(),
//     value: [
//       [now.getFullYear(), now.getMonth() + 1, now.getDate()].join("/"),
//       Math.round(value)
//     ]
//   };
// }

export default {
  name: "Traffic",
  components: {
    IEcharts
  },
  methods: {
    test() {
      console.log("?>>>>>>>>>>?????????????" + this.option.series.data);
      for (var i = 0; i < 1000; i++) {
        this.option.series[0].data.push(this.randomData());
      }
    },
    randomData() {
      this.now = new Date(+this.now + this.oneDay);
      this.value = this.value + Math.random() * 21 - 10;
      return {
        name: this.now.toString(),
        value: [
          [
            this.now.getFullYear(),
            this.now.getMonth() + 1,
            this.now.getDate()
          ].join("/"),
          Math.round(this.value)
        ]
      };
    }
  },
  data() {
    return {
      now: +new Date(1997, 9, 3),
      oneDay: 24 * 3600 * 1000,
      value: Math.random() * 1000,
      option: {
        // title: {
        //   text: "动态数据 + 时间坐标轴"
        // },
        tooltip: {
          trigger: "axis",
          formatter: function(params) {
            params = params[0];
            var date = new Date(params.name);
            return (
              date.getDate() +
              "/" +
              (date.getMonth() + 1) +
              "/" +
              date.getFullYear() +
              " : " +
              params.value[1]
            );
          },
          axisPointer: {
            animation: false
          }
        },
        xAxis: {
          type: "time",
          splitLine: {
            show: false
          }
        },
        yAxis: {
          type: "value",
          boundaryGap: [0, "100%"],
          splitLine: {
            show: false
          }
        },
        series: [
          {
            name: "模拟数据",
            type: "line",
            showSymbol: false,
            hoverAnimation: false,
            data: []
          }
        ]
      }
    };
  },
  mounted() {
    document.getElementById(
      "startDate"
    ).value = new Date().toISOString().substring(0, 10);
    document.getElementById(
      "endDate"
    ).value = new Date().toISOString().substring(0, 10);
    setInterval(
      function() {
        for (var i = 0; i < 5; i++) {
          this.option.series[0].data.shift();
          this.option.series[0].data.push(this.randomData());
        }
        // const myChart = document.getElementById("chart1");
        // myChart.setOption({
        //   series: [
        //     {
        //       data: data
        //     }
        //   ]
        // });
      }.bind(this),
      1000
    );
  },
  created() {
    this.test();
  }
};
</script>

<style></style>
