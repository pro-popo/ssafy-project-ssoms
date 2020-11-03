<template>
  <div>
    <IEcharts :option="option" />
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";

export default {
  name: "SchemaChart",
  components: {
    IEcharts
  },
  data() {
    return {
      schemaName: "C##TESTDB2",

      cpu: [
        {
          percent: 0.0,
          date: "2020-11-02 11:20:30"
        }
      ],
      memory: [
        {
          value: 14000,
          data: "2020-11-02 11:20:30"
        }
      ],
      pie: {
        // title: {
        //   text: "Status"
        // },
        legend: {
          orient: "vertical",
          right: 10,
          data: ["CpuTime", "WaitTime"]
        },
        series: [
          {
            type: "pie",
            radius: ["40%", "65%"],
            label: {
              show: false,
              position: "center"
            },
            emphasis: {
              label: {
                show: true,
                lineHeight: 25,
                padding: [-12, 0, 0, 0],
                // fontSize: "20",

                formatter: "{b|{b}}\n{c|{c}} {d|%}",
                rich: {
                  b: {
                    color: "gray",
                    fontSize: "12",
                    fontWeight: "bold"
                  },
                  c: {
                    fontSize: "25",
                    fontWeight: "bold"
                  },
                  d: {
                    fontSize: "20",
                    fontWeight: "bold"
                  }
                }
              }
            },
            labelLine: {
              show: false
            },
            color: ["#2196F3", "#4CAF50"],
            data: [
              {
                value: 93.59,
                name: "CpuTime"
              },
              {
                value: 6.41,
                name: "WaitTime"
              }
            ]
          }
        ]
      }
    };
  }
};
</script>

<style></style>
