<template>
  <div>
    <div style="display: flex; justify-content: space-between" class="mt-2">
      <h2>Schema Status</h2>
      <v-menu transition="slide-y-transition" :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
          <v-btn v-bind="attrs" v-on="on" fab x-small>
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
      <div v-if="items[0].isShow">
        <h4>Executions(times)</h4>
        <v-card elevation="2" class="schema-chart-size">
          <IEcharts :option="option1" />
        </v-card>
      </div>
      <div v-if="items[1].isShow">
        <h4>CpuTimeTotal(%)</h4>
        <v-card elevation="2" class="schema-chart-size">
          <IEcharts :option="option2" />
        </v-card>
      </div>
      <div v-if="items[2].isShow">
        <h4>ElapsedTimeTotal(%)</h4>
        <v-card elevation="2" class="schema-chart-size">
          <IEcharts :option="option3" />
        </v-card>
      </div>
      <div v-if="items[3].isShow">
        <h4>BufferGetsAvg</h4>
        <v-card elevation="2" class="schema-chart-size">
          <IEcharts :option="option4" />
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
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
      option1: {
        tooltip: {
          trigger: "axis"
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
          data: []
        },
        yAxis: {
          min: function(value) {
            return value.min - value.min * 0.05;
          },
          max: function(value) {
            return value.max + value.max * 0.05;
          }
        },
        series: []
      },
      option2: {
        tooltip: {
          trigger: "axis"
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
          data: []
        },
        yAxis: {
          min: function(value) {
            return (value.min - value.min * 0.05).toFixed(2);
          },
          max: function(value) {
            return (value.max + value.max * 0.05).toFixed(2);
          }
        },
        series: []
      },
      option3: {
        // title: {
        //   text: "cpuTimeAvg"
        // },
        tooltip: {
          trigger: "axis"
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
          data: []
        },
        yAxis: {
          min: function(value) {
            return (value.min - value.min * 0.05).toFixed(2);
          },
          max: function(value) {
            return (value.max + value.max * 0.05).toFixed(2);
          }
        },
        series: []
      },
      option4: {
        tooltip: {
          trigger: "axis"
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
          data: []
        },
        yAxis: {
          min: function(value) {
            return value.min - value.min * 0.05;
          },
          max: function(value) {
            return value.max + value.max * 0.05;
          }
        },
        series: []
      }
    };
  },
  methods: {
    toggleCheckBox(index) {
      if (this.items[index].isShow === true) {
        this.items[index].isShow = false;
      } else {
        this.items[index].isShow = true;
      }
    }
  },
  computed: {
    ...mapGetters("Schema", [
      "getRealTimeSchemaList1",
      "getRealTimeSchemaList2",
      "getRealTimeSchemaList3",
      "getRealTimeSchemaList4",
      "getSchemaList"
    ]),
    ...mapGetters(["getRealTime", "getRealTimeList"])
  },
  watch: {
    getRealTimeSchemaList1: {
      deep: true,
      handler() {
        var legendList = [];
        for (var i = 0; i < this.getRealTimeSchemaList1.length; i++) {
          legendList.push(this.getRealTimeSchemaList1[i].name);
          this.option1.series[i].name = this.getRealTimeSchemaList1[i].name;
          this.option1.series[i].data = this.getRealTimeSchemaList1[i].data;
        }
        this.option1.legend.data = legendList;
        this.option1.xAxis.data = this.getRealTimeList;
      }
    },
    getRealTimeSchemaList2: {
      deep: true,
      handler() {
        var legendList = [];
        for (var i = 0; i < this.getRealTimeSchemaList2.length; i++) {
          legendList.push(this.getRealTimeSchemaList2[i].name);
          this.option2.series[i].name = this.getRealTimeSchemaList2[i].name;
          this.option2.series[i].data = this.getRealTimeSchemaList2[i].data;
        }
        this.option2.legend.data = legendList;
        this.option2.xAxis.data = this.getRealTimeList;
      }
    },
    getRealTimeSchemaList3: {
      deep: true,
      handler() {
        var legendList = [];
        for (var i = 0; i < this.getRealTimeSchemaList3.length; i++) {
          legendList.push(this.getRealTimeSchemaList3[i].name);
          this.option3.series[i].name = this.getRealTimeSchemaList3[i].name;
          this.option3.series[i].data = this.getRealTimeSchemaList3[i].data;
        }
        this.option3.legend.data = legendList;
        this.option3.xAxis.data = this.getRealTimeList;
      }
    },
    getRealTimeSchemaList4: {
      deep: true,
      handler() {
        var legendList = [];
        for (var i = 0; i < this.getRealTimeSchemaList4.length; i++) {
          legendList.push(this.getRealTimeSchemaList4[i].name);
          this.option4.series[i].name = this.getRealTimeSchemaList4[i].name;
          this.option4.series[i].data = this.getRealTimeSchemaList4[i].data;
        }
        this.option4.legend.data = legendList;
        this.option4.xAxis.data = this.getRealTimeList;
      }
    }
  },
  created() {
    axios.get(SERVER.URL + SERVER.ROUTES.getSettingsSchema).then((res) => {
      for (var i = 0; i < res.data.map.schema.length; i++) {
        this.option1.series.push({
          name: "",
          type: "line",
          data: []
        });
        this.option2.series.push({
          name: "",
          type: "line",
          data: []
        });
        this.option3.series.push({
          name: "",
          type: "line",
          data: []
        });
        this.option4.series.push({
          name: "",
          type: "line",
          data: []
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
  height: 300px;
}
.schema-chart-box > div {
  width: 22%;
  min-width: 220px;
}
.schema-chart-size {
  height: 250px;
}
</style>
