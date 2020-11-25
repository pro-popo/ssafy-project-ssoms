<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <div style="display: flex; margin-bottom: 10px">
        <h2 style="font-size: 22px" class="mt-2 mr-3">
          All Schema'<span style="margin-left: 3px" />s Status
        </h2>
      </div>
      <v-menu
        style="z-index: 10"
        transition="slide-y-transition"
        :close-on-content-click="false"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn v-bind="attrs" v-on="on" dark fab small class="mt-1">
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
      <div v-if="items[0].isShow" style="width: 49.5%">
        <v-card elevation="2" class="schema-chart-size">
          <v-card-text
            style="padding: 8px 15px; display: flex; align-items: center"
          >
            <h3>Executions</h3>
            <div style="margin-left: auto">
              <v-menu offset-y attach>
                <template v-slot:activator="{ attrs, on }">
                  <v-btn small icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>
                <div style="background: white">
                  <v-btn-toggle
                    group
                    mandatory
                    color="#039BE5"
                    v-model="toggle_exclusive[0]"
                    style="display: flex; flex-direction: column"
                  >
                    <v-btn small icon @click="changeChart(1, 'bar')"
                      ><v-icon>mdi-chart-bar</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart(1, 'line')"
                      ><v-icon>mdi-chart-line</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart(1, 'areaspline')"
                      ><v-icon>mdi-chart-areaspline-variant</v-icon></v-btn
                    >
                  </v-btn-toggle>
                </div>
              </v-menu>
            </div>
          </v-card-text>
          <v-divider></v-divider>
          <v-card-text>
            <div
              class="schema-chart-size-in-line"
              @click="clickChart('option1')"
            >
              <IEcharts :option="option1_line" :resizable="true" />
            </div>
            <div class="schema-chart-size-in-pie">
              <IEcharts :option="option1_pie" :resizable="true" />
            </div>
          </v-card-text>
        </v-card>
      </div>
      <div v-if="items[1].isShow" style="width: 49.5%">
        <v-card elevation="2" class="schema-chart-size">
          <v-card-text
            style="padding: 8px 15px; display: flex; align-items: center"
          >
            <h3>CpuTimeTotal</h3>
            <div style="margin-left: auto">
              <v-menu offset-y attach>
                <template v-slot:activator="{ attrs, on }">
                  <v-btn small icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>
                <div style="background: white">
                  <v-btn-toggle
                    group
                    mandatory
                    color="#039BE5"
                    v-model="toggle_exclusive[1]"
                    style="display: flex; flex-direction: column"
                  >
                    <v-btn small icon @click="changeChart(2, 'bar')"
                      ><v-icon>mdi-chart-bar</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart(2, 'line')"
                      ><v-icon>mdi-chart-line</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart(2, 'areaspline')"
                      ><v-icon>mdi-chart-areaspline-variant</v-icon></v-btn
                    >
                  </v-btn-toggle>
                </div>
              </v-menu>
            </div>
          </v-card-text>

          <v-divider></v-divider>
          <v-card-text>
            <div
              class="schema-chart-size-in-line"
              @click="clickChart('option2')"
            >
              <IEcharts :option="option2_line" :resizable="true" />
            </div>
            <div class="schema-chart-size-in-pie">
              <IEcharts :option="option2_pie" :resizable="true" />
            </div>
          </v-card-text>
        </v-card>
      </div>
      <div v-if="items[2].isShow" style="width: 49.5%">
        <v-card elevation="2" class="schema-chart-size">
          <v-card-text
            style="padding: 8px 15px; display: flex; align-items: center"
          >
            <h3>ElapsedTimeTotal</h3>
            <div style="margin-left: auto">
              <v-menu offset-y attach>
                <template v-slot:activator="{ attrs, on }">
                  <v-btn small icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>
                <div style="background: white">
                  <v-btn-toggle
                    group
                    mandatory
                    color="#039BE5"
                    v-model="toggle_exclusive[2]"
                    style="display: flex; flex-direction: column"
                  >
                    <v-btn small icon @click="changeChart(3, 'bar')"
                      ><v-icon>mdi-chart-bar</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart(3, 'line')"
                      ><v-icon>mdi-chart-line</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart(3, 'areaspline')"
                      ><v-icon>mdi-chart-areaspline-variant</v-icon></v-btn
                    >
                  </v-btn-toggle>
                </div>
              </v-menu>
            </div>
          </v-card-text>

          <v-divider></v-divider>
          <v-card-text>
            <div
              class="schema-chart-size-in-line"
              @click="clickChart('option3')"
            >
              <IEcharts :option="option3_line" :resizable="true" />
            </div>
            <div class="schema-chart-size-in-pie">
              <IEcharts :option="option3_pie" :resizable="true" />
            </div>
          </v-card-text>
        </v-card>
      </div>
      <div v-if="items[3].isShow" style="width: 49.5%">
        <v-card elevation="2" class="schema-chart-size">
          <v-card-text
            style="padding: 8px 15px; display: flex; align-items: center"
          >
            <h3>BufferGetsAvg</h3>
            <div style="margin-left: auto">
              <v-menu offset-y attach>
                <template v-slot:activator="{ attrs, on }">
                  <v-btn small icon v-bind="attrs" v-on="on">
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </template>
                <div style="background: white">
                  <v-btn-toggle
                    group
                    mandatory
                    color="#039BE5"
                    v-model="toggle_exclusive[3]"
                    style="display: flex; flex-direction: column"
                  >
                    <v-btn small icon @click="changeChart(4, 'bar')"
                      ><v-icon>mdi-chart-bar</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart(4, 'line')"
                      ><v-icon>mdi-chart-line</v-icon></v-btn
                    >
                    <v-btn small icon @click="changeChart(4, 'areaspline')"
                      ><v-icon>mdi-chart-areaspline-variant</v-icon></v-btn
                    >
                  </v-btn-toggle>
                </div>
              </v-menu>
            </div>
          </v-card-text>

          <v-divider></v-divider>
          <v-card-text>
            <div
              class="schema-chart-size-in-line"
              @click="clickChart('option4')"
            >
              <IEcharts :option="option4_line" :resizable="true" />
            </div>
            <div class="schema-chart-size-in-pie">
              <IEcharts :option="option4_pie" :resizable="true" />
            </div>
          </v-card-text>
        </v-card>
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
    IEcharts,
  },
  data() {
    return {
      toggle_exclusive: [1, 1, 1, 1],

      items: [
        {
          title: "Executions",
          isShow: true,
          iconTrue: "mdi-checkbox-marked-circle-outline",
          iconFalse: "mdi-checkbox-blank-circle-outline",
        },
        {
          title: "CpuTimeTotal",
          isShow: true,
          iconTrue: "mdi-checkbox-marked-circle-outline",
          iconFalse: "mdi-checkbox-blank-circle-outline",
        },
        {
          title: "ElapsedTimeTotal",
          isShow: true,
          iconTrue: "mdi-checkbox-marked-circle-outline",
          iconFalse: "mdi-checkbox-blank-circle-outline",
        },
        {
          title: "BufferGetsAvg",
          isShow: true,
          iconTrue: "mdi-checkbox-marked-circle-outline",
          iconFalse: "mdi-checkbox-blank-circle-outline",
        },
      ],
      option1_line: {
        color: this.getColor(),
        tooltip: {
          trigger: "axis",
          triggerOn: "click",
          axisPointer: {
            type: "line",
            label: {
              background: "#ffff",
              show: true,
              formatter: function (params) {
                this.changeXaxis(params, "option1");
                return params.value;
              }.bind(this),
            },
          },
        },
        legend: {
          type: "scroll",
          icon: "roundRect",
          data: [],
        },
        grid: {
          left: 20,
          right: "35%",
          top: 70,
          bottom: 7,
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          axisPointer: {
            handle: {
              show: true,
              size: [0, 0],
            },
            value: this.selectedRealTime,
          },
        },
        yAxis: {
          name: "(count)",
          type: "value",
          min: function (value) {
            return (value.min - value.min * 0.05).toFixed(0);
          },
          max: function (value) {
            return (value.max + value.max * 0.05).toFixed(0);
          },
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          axisTick: {
            show: false,
          },
        },
        series: [],
      },
      option1_pie: {
        color: this.getColor(),
        // color: ["#f72585", "#7209b7", "#3a0ca3", "#4361ee", "#4cc9f0"],
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          left: "center",
          top: "bottom",
          data: [],
        },
        series: [
          {
            name: "Executions",
            type: "pie",
            radius: [30, 70],
            roseType: "radius",
            label: {
              show: false,
            },
            data: [],
          },
        ],
      },
      option2_line: {
        color: this.getColor(),
        tooltip: {
          trigger: "axis",
          triggerOn: "click",
          axisPointer: {
            label: {
              background: "#ffff",
              show: true,
              formatter: function (params) {
                this.changeXaxis(params, "option2");
                return params.value;
              }.bind(this),
            },
          },
        },
        legend: {
          type: "scroll",
          icon: "roundRect",
          data: [],
        },
        grid: {
          left: 20,
          right: "35%",
          top: 70,
          bottom: 7,
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          axisPointer: {
            handle: {
              show: true,
            },
            value: this.selectedRealTime,
          },
        },
        yAxis: {
          name: "(sec)",
          type: "value",
          min: function (value) {
            return (value.min - value.min * 0.3).toFixed(2);
          },
          max: function (value) {
            return (value.max + value.max * 0.3).toFixed(2);
          },
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          axisTick: {
            show: false,
          },
        },
        series: [],
      },
      option2_pie: {
        color: this.getColor(),
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          left: "center",
          top: "bottom",
          data: [],
        },
        series: [
          {
            name: "CpuTimeTotal",
            type: "pie",
            radius: [30, 70],
            roseType: "radius",
            label: {
              show: false,
            },
            data: [],
          },
        ],
      },
      option3_line: {
        xAxis: {
          type: "category",

          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          axisPointer: {
            handle: {
              show: true,
            },
            value: this.selectedRealTime,
          },
        },
        color: this.getColor(),
        tooltip: {
          trigger: "axis",
          triggerOn: "click",
          axisPointer: {
            label: {
              background: "#ffff",
              show: true,
              formatter: function (params) {
                this.changeXaxis(params, "option3");
                return params.value;
              }.bind(this),
            },
          },
        },
        legend: {
          type: "scroll",
          icon: "roundRect",
          data: [],
        },
        grid: {
          left: 20,
          right: "35%",
          top: 70,
          bottom: 7,
          containLabel: true,
        },
        yAxis: {
          name: "(sec)",
          type: "value",
          min: function (value) {
            return (value.min - value.min * 0.3).toFixed(2);
          },
          max: function (value) {
            return (value.max + value.max * 0.3).toFixed(2);
          },
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          axisTick: {
            show: false,
          },
        },
        series: [],
      },
      option3_pie: {
        color: this.getColor(),
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          left: "center",
          top: "bottom",
          data: [],
        },
        series: [
          {
            name: "ElapsedTimeTotal",
            type: "pie",
            radius: [30, 70],
            roseType: "radius",
            label: {
              show: false,
            },
            data: [],
          },
        ],
      },
      option4_line: {
        grid: {
          left: 20,
          right: "35%",
          top: 70,
          bottom: 7,
          containLabel: true,
        },
        xAxis: {
          type: "category",

          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          axisPointer: {
            handle: {
              show: true,
            },
            value: this.selectedRealTime,
          },
        },
        color: this.getColor(),
        tooltip: {
          trigger: "axis",
          triggerOn: "click",
          axisPointer: {
            label: {
              background: "#ffff",
              show: true,
              formatter: function (params) {
                this.changeXaxis(params, "option4");
                return params.value;
              }.bind(this),
            },
          },
        },
        legend: {
          type: "scroll",
          icon: "roundRect",
          data: [],
        },

        yAxis: {
          name: "(count)",
          type: "value",
          min: function (value) {
            return (value.min - value.min * 0.3).toFixed(2);
          },
          max: function (value) {
            return (value.max + value.max * 0.3).toFixed(2);
          },
          axisLine: {
            lineStyle: {
              color: "#ababab",
            },
          },
          axisTick: {
            show: false,
          },
        },
        series: [],
      },
      option4_pie: {
        color: this.getColor(),
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          left: "center",
          top: "bottom",
          data: [],
        },
        series: [
          {
            name: "BufferGetsAvg",
            type: "pie",
            radius: [30, 70],
            roseType: "radius",
            label: {
              show: false,
            },
            data: [],
          },
        ],
      },
    };
  },
  methods: {
    getColor() {
      return this.$store.state.graphColor;
    },
    ...mapMutations([
      "SET_SELECTED_REALTIME",
      "SET_SETTING_SELECTED",
      "SET_SELECTED_TOOLTIP",
    ]),
    toggleCheckBox(index) {
      if (this.items[index].isShow === true) {
        this.items[index].isShow = false;
      } else {
        this.items[index].isShow = true;
      }
    },
    changeChart(optionName, type) {
      let option = "";
      switch (optionName) {
        case 1:
          option = this.option1_line;
          break;
        case 2:
          option = this.option2_line;
          break;
        case 3:
          option = this.option3_line;
          break;
        case 4:
          option = this.option4_line;
          break;
      }

      let areaStyle = null;
      if (type == "areaspline") {
        areaStyle = "";
        type = "line";
      }
      let stack = null;
      if (type == "bar") {
        option.xAxis.boundaryGap = true;
        option.tooltip.axisPointer.type = "shadow";
        stack = "one";
      } else {
        option.xAxis.boundaryGap = false;
        option.tooltip.axisPointer.type = "line";
        stack = null;
      }
      let cnt = 0;
      option.series.forEach((element) => {
        element.type = type;
        element.areaStyle = areaStyle;
        element.color = option.color[cnt++];
        element.stack = stack;
      });
    },
    clickChart(optionText) {
      this.SET_SELECTED_TOOLTIP(optionText);
      this.SET_SETTING_SELECTED(true);
    },
    changeXaxis(params, optionText) {
      setTimeout(
        function () {
          if (this.selectedTooltip == optionText) {
            var setTime = 0;
            if (!this.getIsSelected) {
              setTime = 100;
            }
            setTimeout(
              function () {
                if (params.seriesData[0] !== undefined && this.getIsSelected) {
                  this.SET_SELECTED_REALTIME(params.seriesData[0].dataIndex);
                } else {
                  this.SET_SELECTED_REALTIME(this.getRealTimeList.length - 1);
                }
              }.bind(this),
              setTime
            );
          }
        }.bind(this),
        200
      );
    },

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
      "getSchemaList",
    ]),
    ...mapGetters([
      "getRealTime",
      "getRealTimeList",
      "getIsSelected",
      "selectedRealTime",
      "selectedTooltip",
    ]),
  },
  watch: {
    selectedRealTime: function () {
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
      },
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
      },
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
      },
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
      },
    },
  },
  created() {
    axios.get(SERVER.URL + SERVER.ROUTES.getSettingsSchema).then((res) => {
      for (var i = 0; i < res.data.map.schema.length; i++) {
        this.option1_line.series.push({
          name: "",
          type: "line",
          // stack: "one",
          areaStyle: null,
          showSymbol: false,
          data: [],
        });
        this.option1_pie.series[0].data.push({
          value: 0,
          name: "",
        });
        this.option2_line.series.push({
          name: "",
          type: "line",
          areaStyle: null,
          showSymbol: false,
          data: [],
        });
        this.option2_pie.series[0].data.push({
          value: 0,
          name: "",
        });
        this.option3_line.series.push({
          name: "",
          type: "line",
          areaStyle: null,
          showSymbol: false,
          data: [],
        });
        this.option3_pie.series[0].data.push({
          value: 0,
          name: "",
        });
        this.option4_line.series.push({
          name: "",
          type: "line",
          areaStyle: null,
          showSymbol: false,
          data: [],
        });
        this.option4_pie.series[0].data.push({
          value: 0,
          name: "",
        });
      }
    });
  },
};
</script>

<style>
.schema-chart-box {
  display: flex;
  justify-content: space-between;
  /* height: 80vh; */
  width: 100%;
  flex-wrap: wrap;
}
.schema-chart-box > div {
  margin-bottom: 15px;
}
.schema-chart-size {
  height: 300px;
  /* display: flex; */
}
.schema-chart-size-in-line {
  position: absolute;
  width: 92%;
  height: 75%;
}
.schema-chart-size-in-pie {
  position: absolute;
  width: 35%;
  margin-left: 62%;
  margin-top: 10px;
  height: 85%;
}
</style>
