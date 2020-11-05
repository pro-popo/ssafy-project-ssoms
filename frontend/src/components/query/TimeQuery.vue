<template>
  <div class="mt-3">
    <IEcharts :option="option" />
    {{ this.$store.getters["Schema/showTTest"] }}
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapGetters } from "vuex";

var data1 = [0, 0];
var data2 = [0, 0];
var data3 = [0, 0];
var test1 = 0;
var test2 = 0;
var test3 = 0;
var move1 = 0.3;
var move2 = 0.2;
var move3 = 0.5;
setInterval(function() {
  data1.shift();
  data1.push(test1 + move1);
  data2.shift();
  data2.push(test2 + move2);
  data3.shift();
  data3.push(test3 + move3);
  test1 = test1 + move1;
  test2 = test2 + move2;
  test3 = test3 + move3;
  if (test1 > 2) {
    move1 = -0.3;
  } else if (test1 < 0.5) {
    move1 = 0.3;
  }
  if (test2 > 3) {
    move2 = -0.2;
  } else if (test2 < 1) {
    move2 = 0.2;
  }
  if (test3 > 2.5) {
    move3 = -0.5;
  } else if (test3 < 0.5) {
    move3 = 0.5;
  }
}, 1000);

export default {
  name: "TimeQuery",
  components: {
    IEcharts
  },
  computed: {
    ...mapGetters("Schema", ["showTTest"])
  },
  data() {
    return {
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
            data: data1,
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
            data: data2
          },
          {
            name: "Total",
            type: "bar",
            // label: seriesLabel,
            data: data3
          }
        ]
      }
    };
  }
};
</script>

<style></style>
