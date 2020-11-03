<template>
  <div class="detail-query-box">
    <div>
      <IEcharts :option="pie1" :loading="loading" />
    </div>
    <div>
      <IEcharts :option="pie2" />
    </div>
    <div>
      <IEcharts :option="pie3" />
    </div>
    <div>
      <IEcharts :option="pie2" />
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "DetailQuery",
  components: {
    IEcharts
  },
  data: () => ({
    loading: false,
    pie1: {
      tooltip: {
        trigger: "item",
        formatter: "{a} <br/>{b}: {c} ({d}%)"
      },
      legend: {
        orient: "vertical",
        left: 10,
        top: 25,
        data: ["A", "B", "C", "D", "E"]
      },
      series: [
        {
          name: "TEST",
          type: "pie",
          radius: ["50%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center"
          },
          emphasis: {
            label: {
              show: true,
              fontSize: "40",
              fontWeight: "bold"
            }
          },
          labelLine: {
            show: false
          },
          data: [
            { value: 1, name: "A" },
            { value: 2, name: "B" },
            { value: 3, name: "C" },
            { value: 4, name: "D" },
            { value: 5, name: "E" }
          ]
        }
      ]
    },
    pie2: {
      tooltip: {
        trigger: "item",
        formatter: "{a} <br/>{b}: {c} ({d}%)"
      },
      legend: {
        orient: "vertical",
        left: 10,
        top: 25,
        data: ["A", "B", "C", "D", "E"]
      },
      series: [
        {
          name: "TEST",
          type: "pie",
          radius: ["50%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center"
          },
          emphasis: {
            label: {
              show: true,
              fontSize: "30",
              fontWeight: "bold"
            }
          },
          labelLine: {
            show: false
          },
          data: [
            { value: 335, name: "A" },
            { value: 310, name: "B" },
            { value: 234, name: "C" },
            { value: 135, name: "D" },
            { value: 1548, name: "E" }
          ]
        }
      ]
    },
    pie3: {
      tooltip: {
        trigger: "item",
        formatter: "{a} <br/>{b}: {c} ({d}%)"
      },
      legend: {
        orient: "vertical",
        left: 10,
        top: 25,
        data: ["A", "B", "C", "D", "E"]
      },
      series: [
        {
          name: "TEST",
          type: "pie",
          radius: ["50%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center"
          },
          emphasis: {
            label: {
              show: true,
              fontSize: "30",
              fontWeight: "bold"
            }
          },
          labelLine: {
            show: false
          },
          data: [
            { value: 5, name: "A" },
            { value: 4, name: "B" },
            { value: 3, name: "C" },
            { value: 2, name: "D" },
            { value: 1, name: "E" }
          ]
        }
      ]
    }
  })
};
</script>

<style>
.detail-query-box {
  /* background-color: pink; */
  display: flex;
}
.detail-query-box > div {
  width: 300px;
  height: 100%;
}
</style>
