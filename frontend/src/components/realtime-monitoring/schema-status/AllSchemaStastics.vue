<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <div style="display: flex">
        <h2 class="mt-2 mr-3">Schema Status</h2>
        <v-btn-toggle
          group
          mandatory
          color="#2196F3"
          v-model="toggle_exclusive"
        >
          <v-btn @click="changeChart('line')" style="margin: 4px 0px;"
            ><v-icon>mdi-chart-line</v-icon></v-btn
          >
          <v-btn @click="changeChart('pie')" style="margin: 4px 0px;"
            ><v-icon>mdi-chart-pie</v-icon></v-btn
          >
        </v-btn-toggle>
      </div>
      <v-menu transition="slide-y-transition" :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
          <v-btn v-bind="attrs" v-on="on" fab x-small class="mt-2">
            <v-icon>mdi-playlist-plus</v-icon>
          </v-btn>
        </template>
        <v-list dense>
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            @click="toggleCheckBox(i)"
          >
            <v-list-item-icon>
              <v-icon v-text="item.iconTrue" v-if="item.isShow"></v-icon>
              <v-icon v-text="item.iconFalse" v-else></v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="item.title"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
    <div class="schema-chart-box">
      <div>
        <div v-if="items[0].isShow">
          <h4>Executions</h4>
          <v-card elevation="2" class="schema-chart-size">
            <IEcharts
              :option="option1_line"
              :resizable="true"
              class="schema-chart-size-in"
            />
            <IEcharts
              :option="option1_pie"
              :resizable="true"
              class="schema-chart-size-in"
            />
          </v-card>
        </div>
        <div v-if="items[1].isShow" class="mt-3">
          <h4>CpuTimeTotal</h4>
          <v-card elevation="2" class="schema-chart-size">
            <IEcharts
              :option="option2_line"
              :resizable="true"
              class="schema-chart-size-in"
            />
            <IEcharts
              :option="option2_pie"
              :resizable="true"
              class="schema-chart-size-in"
            />
          </v-card>
        </div>
      </div>
      <div>
        <div v-if="items[2].isShow">
          <h4>ElapsedTimeTotal</h4>
          <v-card elevation="2" class="schema-chart-size">
            <IEcharts
              :option="option3_line"
              :resizable="true"
              class="schema-chart-size-in"
            />
            <IEcharts
              :option="option3_pie"
              :resizable="true"
              class="schema-chart-size-in"
            />
          </v-card>
        </div>
        <div v-if="items[3].isShow" class="mt-3">
          <h4>BufferGetsAvg</h4>
          <v-card elevation="2" class="schema-chart-size">
            <IEcharts
              :option="option4_line"
              :resizable="true"
              class="schema-chart-size-in"
            />
            <IEcharts
              :option="option4_pie"
              :resizable="true"
              class="schema-chart-size-in"
            />
          </v-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";
import IEcharts from "vue-echarts-v3/src/full.js";
import axios from "axios";
import SERVER from "@/api/spring.js";

export default {
  name: "AllSchemaStastice",
  components: {
    IEcharts
  },
  data() {
    return {
      toggle_exclusive: 5,
      items: [
        {
          title: "Executions",
          isShow: true,
          iconTrue: "mdi-checkbox-marked-circle-outline",
          iconFalse: "mdi-checkbox-blank-circle-outline"
        },
        {
          title: "CpuTimeTotal",
          isShow: true,
          iconTrue: "mdi-checkbox-marked-circle-outline",
          iconFalse: "mdi-checkbox-blank-circle-outline"
        },
        {
          title: "ElapsedTimeTotal",
          isShow: true,
          iconTrue: "mdi-checkbox-marked-circle-outline",
          iconFalse: "mdi-checkbox-blank-circle-outline"
        },
        {
          title: "BufferGetsAvg",
          isShow: true,
          iconTrue: "mdi-checkbox-marked-circle-outline",
          iconFalse: "mdi-checkbox-blank-circle-outline"
        }
      ],
      option1_line: {
        tooltip: {
          trigger: "axis",
          triggerOn: "none",
          axisPointer: {
            label: {
              background: "#ffff",
              show: true,
              formatter: function(params) {
                // this.changeXaxis(params);
                return params.value;
              }.bind(this)
            }
          }
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
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisPointer: {
            handle: {
              show: true
            },
            value: this.selectedRealTime
          }
        },
        yAxis: {
          name: "(count)",
          type: "value",
          min: function(value) {
            return (value.min - value.min * 0.05).toFixed(0);
          },
          max: function(value) {
            return (value.max + value.max * 0.05).toFixed(0);
          },
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisTick: {
            show: false
          }
        },
        series: []
      },
      option1_pie: {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          left: "center",
          top: "bottom",
          data: []
        },
        series: [
          {
            name: "Executions",
            type: "pie",
            radius: [30, 90],
            roseType: "radius",
            label: {
              show: false
            },
            data: []
          }
        ]
      },
      option2_line: {
        tooltip: {
          trigger: "axis",
          triggerOn: "none",
          axisPointer: {
            label: {
              background: "#ffff",
              show: true,
              formatter: function(params) {
                return params.value;
              }.bind(this)
            }
          }
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
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisPointer: {
            handle: {
              show: true
            },
            value: this.selectedRealTime
          }
        },
        yAxis: {
          name: "(%)",
          type: "value",
          min: function(value) {
            return (value.min - value.min * 0.3).toFixed(3);
          },
          max: function(value) {
            return (value.max + value.max * 0.3).toFixed(3);
          },
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisTick: {
            show: false
          }
        },
        series: []
      },
      option2_pie: {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          left: "center",
          top: "bottom",
          data: []
        },
        series: [
          {
            name: "CpuTimeTotal",
            type: "pie",
            radius: [30, 90],
            roseType: "radius",
            label: {
              show: false
            },
            data: []
          }
        ]
      },
      option3_line: {
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisPointer: {
            handle: {
              show: true
            },
            value: this.selectedRealTime
          }
        },
        tooltip: {
          trigger: "axis",
          triggerOn: "none",
          axisPointer: {
            label: {
              background: "#ffff",
              show: true,
              formatter: function(params) {
                return params.value;
              }.bind(this)
            }
          }
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

        yAxis: {
          name: "(%)",
          type: "value",
          min: function(value) {
            return (value.min - value.min * 0.3).toFixed(3);
          },
          max: function(value) {
            return (value.max + value.max * 0.3).toFixed(3);
          },
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisTick: {
            show: false
          }
        },
        series: []
      },
      option3_pie: {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          left: "center",
          top: "bottom",
          data: []
        },
        series: [
          {
            name: "ElapsedTimeTotal",
            type: "pie",
            radius: [30, 90],
            roseType: "radius",
            label: {
              show: false
            },
            data: []
          }
        ]
      },
      option4_line: {
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisPointer: {
            handle: {
              show: true
            },
            value: this.selectedRealTime
          }
        },
        tooltip: {
          trigger: "axis",
          triggerOn: "none",
          axisPointer: {
            label: {
              background: "#ffff",
              show: true,
              formatter: function(params) {
                return params.value;
              }.bind(this)
            }
          }
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

        yAxis: {
          name: "(?)",
          type: "value",
          min: function(value) {
            return value.min - value.min * 0.3;
          },
          max: function(value) {
            return value.max + value.max * 0.3;
          },
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },
          axisTick: {
            show: false
          }
        },
        series: []
      },
      option4_pie: {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          left: "center",
          top: "bottom",
          data: []
        },
        series: [
          {
            name: "BufferGetsAvg",
            type: "pie",
            radius: [30, 90],
            roseType: "radius",
            label: {
              show: false
            },
            data: []
          }
        ]
      }
    };
  },
  methods: {
    ...mapMutations(["SET_SELECTED_REALTIME"]),
    toggleCheckBox(index) {
      if (this.items[index].isShow === true) {
        this.items[index].isShow = false;
      } else {
        this.items[index].isShow = true;
      }
    },
    changeXaxis(params) {
      var setTime = 0;
      if (!this.getIsSelected) {
        setTime = 100;
      }
      setTimeout(
        function() {
          if (params.seriesData[0] !== undefined && this.getIsSelected) {
            console.log("얘는 스키마11111111");
            this.SET_SELECTED_REALTIME(params.seriesData[0].dataIndex);
          } else {
            console.log("얘는 스키마22222222");
            this.SET_SELECTED_REALTIME(this.getRealTimeList.length - 1);
          }
        }.bind(this),
        setTime
      );
    }

    // changeAxis(index, params) {
    //   console.log("왜이래", index, params);
    //   if (index === 3) {
    //     if (params.seriesData[0] !== undefined)
    //       this.SET_SELECTED_REALTIME(params.seriesData[0].dataIndex);
    //     else this.SET_SELECTED_REALTIME(this.getRealTimeList.length - 1);
    //   } else if (index === 4) {
    //     if (params.seriesData[0] !== undefined)
    //       this.SET_SELECTED_REALTIME(params.seriesData[0].dataIndex);
    //     else this.SET_SELECTED_REALTIME(this.getRealTimeList.length - 1);
    //   }
    // }
    // changeChart(type) {
    //   if (type === "line") {
    //   } else if (type === "pie") {
    // }
  },
  computed: {
    ...mapGetters("Schema", [
      "getRealTimeSchemaList1",
      "getRealTimeSchemaList2",
      "getRealTimeSchemaList3",
      "getRealTimeSchemaList4",
      "getSchemaList"
    ]),
    ...mapGetters(["getRealTime", "getRealTimeList", "selectedRealTime"])
  },
  watch: {
    selectedRealTime: function() {
      const select = this.selectedRealTime;
      for (var i = 0; i < this.getRealTimeSchemaList2.length; i++) {
        this.option1_pie.series[0].data[i].value = this.getRealTimeSchemaList1[
          i
        ].data[select];
        this.option2_pie.series[0].data[i].value = this.getRealTimeSchemaList2[
          i
        ].data[select];
        this.option3_pie.series[0].data[i].value = this.getRealTimeSchemaList3[
          i
        ].data[select];
        this.option4_pie.series[0].data[i].value = this.getRealTimeSchemaList4[
          i
        ].data[select];
      }
      this.option1_line.xAxis.axisPointer.value = select;
      this.option2_line.xAxis.axisPointer.value = select;
      this.option3_line.xAxis.axisPointer.value = select;
      this.option4_line.xAxis.axisPointer.value = select;
    },
    getRealTimeSchemaList1: {
      deep: true,
      handler() {
        var legendList = [];
        const timeList = this.getRealTimeList;
        for (var i = 0; i < this.getRealTimeSchemaList1.length; i++) {
          legendList.push(this.getRealTimeSchemaList1[i].name);
          this.option1_line.series[i].name = this.getRealTimeSchemaList1[
            i
          ].name;
          this.option1_line.series[i].data = this.getRealTimeSchemaList1[
            i
          ].data;
          this.option1_pie.series[0].data[
            i
          ].value = this.getRealTimeSchemaList1[i].data[this.selectedRealTime];
          this.option1_pie.series[0].data[i].name = this.getRealTimeSchemaList1[
            i
          ].name;
        }
        this.option1_line.legend.data = legendList;
        this.option1_line.xAxis.data = timeList;
      }
    },
    getRealTimeSchemaList2: {
      deep: true,
      handler() {
        var legendList = [];
        const timeList = this.getRealTimeList;
        for (var i = 0; i < this.getRealTimeSchemaList2.length; i++) {
          legendList.push(this.getRealTimeSchemaList2[i].name);
          this.option2_line.series[i].name = this.getRealTimeSchemaList2[
            i
          ].name;
          this.option2_line.series[i].data = this.getRealTimeSchemaList2[
            i
          ].data;
          this.option2_pie.series[0].data[
            i
          ].value = this.getRealTimeSchemaList2[i].data[this.selectedRealTime];
          this.option2_pie.series[0].data[i].name = this.getRealTimeSchemaList2[
            i
          ].name;
        }
        this.option2_line.legend.data = legendList;
        this.option2_line.xAxis.data = timeList;
      }
    },
    getRealTimeSchemaList3: {
      deep: true,
      handler() {
        var legendList = [];
        const timeList = this.getRealTimeList;
        for (var i = 0; i < this.getRealTimeSchemaList3.length; i++) {
          legendList.push(this.getRealTimeSchemaList3[i].name);
          this.option3_line.series[i].name = this.getRealTimeSchemaList3[
            i
          ].name;
          this.option3_line.series[i].data = this.getRealTimeSchemaList3[
            i
          ].data;
          this.option3_pie.series[0].data[
            i
          ].value = this.getRealTimeSchemaList3[i].data[this.selectedRealTime];
          this.option3_pie.series[0].data[i].name = this.getRealTimeSchemaList3[
            i
          ].name;
        }
        this.option3_line.legend.data = legendList;
        this.option3_line.xAxis.data = timeList;
      }
    },
    getRealTimeSchemaList4: {
      deep: true,
      handler() {
        var legendList = [];
        const timeList = this.getRealTimeList;
        for (var i = 0; i < this.getRealTimeSchemaList4.length; i++) {
          legendList.push(this.getRealTimeSchemaList4[i].name);
          this.option4_line.series[i].name = this.getRealTimeSchemaList4[
            i
          ].name;
          this.option4_line.series[i].data = this.getRealTimeSchemaList4[
            i
          ].data;
          this.option4_pie.series[0].data[
            i
          ].value = this.getRealTimeSchemaList4[i].data[this.selectedRealTime];
          this.option4_pie.series[0].data[i].name = this.getRealTimeSchemaList4[
            i
          ].name;
        }
        this.option4_line.legend.data = legendList;
        this.option4_line.xAxis.data = timeList;
      }
    }
  },
  created() {
    axios.get(SERVER.URL + SERVER.ROUTES.getSettingsSchema).then((res) => {
      for (var i = 0; i < res.data.map.schema.length; i++) {
        this.option1_line.series.push({
          name: "",
          type: "line",
          data: []
        });
        this.option1_pie.series[0].data.push({
          value: 0,
          name: ""
        });
        this.option2_line.series.push({
          name: "",
          type: "line",
          data: []
        });
        this.option2_pie.series[0].data.push({
          value: 0,
          name: ""
        });
        this.option3_line.series.push({
          name: "",
          type: "line",
          data: []
        });
        this.option3_pie.series[0].data.push({
          value: 0,
          name: ""
        });
        this.option4_line.series.push({
          name: "",
          type: "line",
          data: []
        });
        this.option4_pie.series[0].data.push({
          value: 0,
          name: ""
        });
      }
    });
  }
};
</script>

<style>
.schema-chart-box {
  display: flex;
  justify-content: space-between;
  height: 550px;
}
.schema-chart-box > div {
  width: 49%;
  min-width: 500px;
}
.schema-chart-size {
  height: 250px;
  display: flex;
}
.schema-chart-size-in {
  width: 50% !important;
}
</style>
