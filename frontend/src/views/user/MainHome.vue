<template>
  <div>
    {{ showlist }}
    <SchemaChart />
    <OracleResource />
  </div>
</template>

<script type="text/babel">
import SchemaChart from "@/components/pc-resource/SchemaChart.vue";
import OracleResource from "@/components/oracle-status/OracleResource.vue";
import { mapGetters } from "vuex";

export default {
  name: "MainHome",
  components: {
    SchemaChart,
    OracleResource
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
  },
  computed: {
    ...mapGetters("Oracle", ["showlist"])
  }
};
</script>

<style scoped>
.echarts {
  width: 400px;
  height: 400px;
}
</style>
