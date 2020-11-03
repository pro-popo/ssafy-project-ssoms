<template>
  <div class="mt-3">
    <IEcharts :option="option" />
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
export default {
  name: "TimeQuery",
  components: {
    IEcharts
  },
  data: () => ({
    option: {
      // title: {
      //   text: "Weather Statistics"
      // },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "shadow"
        }
      },
      legend: {
        data: ["Avg", "Max", "Total"]
      },
      grid: {
        left: 50
      },
      // toolbox: {
      //   show: true,
      //   feature: {
      //     saveAsImage: {}
      //   }
      // },
      xAxis: {
        type: "value",
        // name: "Days",
        axisLabel: {
          formatter: "{value}"
        }
      },
      yAxis: {
        type: "category",
        inverse: true,
        data: ["CPU", "Elapse"],
        axisLabel: {
          formatter: function(value) {
            return "{" + value + "| }\n{value|" + value + "}";
          },
          // margin: 20,
          rich: {
            value: {
              lineHeight: 30,
              align: "center"
            },
            Sunny: {
              height: 40,
              align: "center"
              // backgroundColor: {
              //   image: weatherIcons.Sunny
              // }
            },
            Showers: {
              height: 40,
              align: "center"
              // backgroundColor: {
              //   image: weatherIcons.Showers
              // }
            }
          }
        }
      },
      series: [
        {
          name: "Avg",
          type: "bar",
          data: [10, 20],
          // label: seriesLabel,
          markPoint: {
            symbolSize: 1,
            symbolOffset: [0, "50%"],
            label: {
              formatter: "{a|{a}\n}{b|{b} }{c|{c}}",
              backgroundColor: "rgb(242,242,242)",
              borderColor: "#aaa",
              borderWidth: 1,
              borderRadius: 4,
              // padding: [4, 10],
              lineHeight: 26,
              // shadowBlur: 5,
              // shadowColor: '#000',
              // shadowOffsetX: 0,
              // shadowOffsetY: 1,
              position: "right",
              distance: 20,
              rich: {
                a: {
                  align: "center",
                  color: "#fff",
                  fontSize: 18,
                  textShadowBlur: 2,
                  textShadowColor: "#000",
                  textShadowOffsetX: 0,
                  textShadowOffsetY: 1,
                  textBorderColor: "#333",
                  textBorderWidth: 2
                },
                b: {
                  color: "#333"
                },
                c: {
                  color: "#ff8811",
                  textBorderColor: "#000",
                  textBorderWidth: 1,
                  fontSize: 22
                }
              }
            }
            // data: [
            //   { type: "max", name: "max days: " },
            //   { type: "min", name: "min days: " }
            // ]
          }
        },
        {
          name: "Max",
          type: "bar",
          // label: seriesLabel,
          data: [150, 105]
        },
        {
          name: "Total",
          type: "bar",
          // label: seriesLabel,
          data: [220, 82]
        }
      ]
    }
  })
};
</script>

<style></style>
