<template>
  <div class="whole-box">
    <v-card elevation="2" width="600px" class="mx-auto mt-1">
      <IEcharts width="600px" :option="chart1" @click="onClick" />
    </v-card>
    <v-card elevation="2" width="600px" class="mx-auto mt-1">
      <IEcharts width="600px" :option="chart2" />
    </v-card>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapMutations, mapGetters } from "vuex";
import echarts from 'echarts/lib/echarts';
import SERVER from "@/api/spring.js";
import axios from "axios";

export default {
  name: "SchemaWhole",
  components: {
    IEcharts
  },
  computed: {
    ...mapGetters("Schema", ["getTimeAndCpuList"]),
    ...mapGetters("Schema", ["getPastTimeData"]),
    chart1(){
        return {
            tooltip: {
                trigger: 'axis',
                position: function (pt) {
                    return [pt[0], '10%'];
                },
                // axisPointer: {
                //     type: 'shadow',
                //     triggerEvent: true,
                // },
                triggerEvent: true,
            },
            title: {
                left: 'center',
                text: 'CPU',
                padding: [15,0,0,0]
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: this.getTimeAndCpuList.time,
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%']
            },
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 10
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            series: [
                {
                    name: 'databaseCpuTimeRatio',
                    type: 'line',
                    smooth: true,
                    sampling: 'average',
                    itemStyle: {
                        color: 'rgb(255, 70, 131)',
                    },
                    areaStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgb(255, 158, 68)'
                        }, {
                            offset: 1,
                            color: 'rgb(255, 70, 131)'
                        }])
                    },
                    data: this.getTimeAndCpuList.cpu,
                }
            ]
        }
    },
    chart2(){
      return {
        tooltip: {},
        legend: {
          data: this.getPastTimeData.schemaList,
          bottom: 10
        },
        radar: {
          // shape: 'circle',
          name: {
            textStyle: {
              color: "#fff",
              backgroundColor: "#999",
              borderRadius: 3,
              padding: [3, 5]
            }
          },
          indicator: [
            { name: "bufferGetsAvg"},
            { name: "cpuTimeAvg"},
            { name: "cpuTimeMax"},
            { name: "cpuTimeTot"},
            { name: "sqlCnt"},
          ]
        },
        series: [
          {
            name: "预算 vs 开销（Budget vs spending）",
            type: "radar",
            // areaStyle: {normal: {}},
            data: this.getPastTimeData.radarchart
            
          }
        ]
      }
    }
  },
  methods: {
    ...mapMutations("Schema", ["SET_PAST_TIME_DATA"]),
    onClick(eventInfo){
      console.log(eventInfo.name)
      axios
        .get(SERVER.URL + SERVER.ROUTES.getPastData + '/' +eventInfo.name)
        .then((res) => {
          if (res.data.result === "success") {
            console.log(res.data.map)
            this.SET_PAST_TIME_DATA(res.data.map.realTimeMonitoring);
          }
        })
        .catch((err) => console.log(err));
    },
    click_event(params){
      console.log(params)
    }
  }
};
</script>

<style>
.whole-box {
  display: flex;
  height: 320px;
  /* padding-left: 50px; */
}
.whole-query-box1 {
  margin: 10px;
}
.whole-query-box2 {
  margin: 10px;
}
</style>
