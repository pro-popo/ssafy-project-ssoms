<template>
  <div class="echarts">
    <IEcharts
      :option="bar"
      :loading="loading"
      @ready="onReady"
      @click="onClick"
    />
    <button @click="doRandom">Random</button>
  </div>
</template>

<script type="text/babel">
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "view",
  components: {
    IEcharts
  },
  props: {},
  data: () => ({
    loading: false,
    bar: {
      title: {
        text: "ECharts Hello World"
      },
      tooltip: {},
      xAxis: {
        data: [
          "Shirt",
          "Sweater",
          "Chiffon Shirt",
          "Pants",
          "High Heels",
          "Socks"
        ]
      },
      yAxis: {},
      series: [
        {
          name: "Sales",
          type: "bar",
          data: [1, 5, 10, 15, 20, 25]
        }
      ]
    }
  }),
  methods: {
    doRandom() {
      const that = this;
      let data = [];
      for (let i = 0, min = 5, max = 99; i < 6; i++) {
        data.push(Math.floor(Math.random() * (max + 1 - min) + min));
      }
      that.loading = !that.loading;
      that.bar.series[0].data = data;
    },
    onReady(instance, ECharts) {
      console.log(instance, ECharts);
    },
    onClick() {
      console.log(arguments);
    }
  }
};
</script>

<style scoped>
.echarts {
  width: 400px;
  height: 400px;
}
</style>
