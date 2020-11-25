<template>
  <div>
    <div id="SchemaQuerysTitle">
      <v-card-subtitle>
        <h2 style="margin-top: 30px; color: var(--font-main-color)">
          {{ SelectedSchema }}'<span style="margin-left: 3px" />s Top Query
        </h2>
      </v-card-subtitle>
      <v-tabs
        style="width: 100%; margin: 5px 0px 10px"
        background-color="rgba(1,1,1,0)"
        center-active
        v-model="tab"
        color="black"
      >
        <v-tab
          style="background: rgba(1, 1, 1, 0)"
          v-for="item in items"
          :key="item.tab"
        >
          {{ item.tab }}
        </v-tab>
      </v-tabs>
    </div>

    <div id="SchemaQuerys">
      <v-tabs-items v-model="tab">
        <v-tab-item>
          <v-simple-table
            fixed-header
            height="285px"
            class="elevation-2 realtime-top-query"
            id="schemaTopQueryTable1"
            v-if="getPastTimeData.schemas[SelectedSchema] != null"
          >
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center topquery-table-th">Rank</th>
                  <th class="text-left topquery-table-th">SQL ID</th>
                  <th class="text-left topquery-table-th">SQL</th>
                  <th class="text-left topquery-table-th">CPU Time</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(query, index) in getPastTimeData.schemas[
                    SelectedSchema
                  ].cpuUsed"
                  :key="index"
                  @click="getCPUQueryDetail('schemaTopQueryTable1', index)"
                  class="real-query-hover"
                >
                  <td class="text-center" style="width: 70px">
                    {{ index + 1 }}
                  </td>
                  <td style="width: 250px">{{ query.sqlId }}</td>
                  <td align="left" class="query-table-sql">{{ query.sql }}</td>
                  <td>
                    <div
                      style="
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        width: 85px;
                      "
                    >
                      {{ query.cpuTimeRatio }} %
                      <v-progress-circular
                        v-if="query.cpuTimeRatio != 0"
                        style="margin-left: 10px"
                        :value="query.cpuTimeRatio"
                        :size="20"
                        :width="5"
                        color="#6440e3 "
                      ></v-progress-circular>
                    </div>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-tab-item>
        <v-tab-item>
          <v-simple-table
            fixed-header
            height="285px"
            class="elevation-2 realtime-top-query"
            id="schemaTopQueryTable2"
            v-if="getPastTimeData.schemas[SelectedSchema] != null"
          >
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center topquery-table-th">Rank</th>
                  <th class="text-left topquery-table-th">SQL ID</th>
                  <th class="text-left topquery-table-th">SQL</th>
                  <th class="text-left topquery-table-th">Elapsed Time</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(query, index) in getPastTimeData.schemas[
                    SelectedSchema
                  ].elapsedTime"
                  :key="index"
                  @click="getElapsedQueryDetail('schemaTopQueryTable2', index)"
                  class="real-query-hover"
                >
                  <td class="text-center" style="width: 70px">
                    {{ index + 1 }}
                  </td>
                  <td style="width: 250px">{{ query.sqlId }}</td>
                  <td align="left" class="query-table-sql">{{ query.sql }}</td>
                  <td>
                    <div
                      style="
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        width: 85px;
                      "
                    >
                      {{ query.elapsedTimeRatio }}
                      %
                      <v-progress-circular
                        v-if="query.elapsedTimeRatio != 0"
                        style="margin-left: 10px"
                        :value="query.elapsedTimeRatio"
                        :size="20"
                        :width="5"
                        color="#6440e3 "
                      ></v-progress-circular>
                    </div>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-tab-item>
        <v-tab-item>
          <v-simple-table
            fixed-header
            height="285px"
            class="elevation-2 realtime-top-query"
            id="schemaTopQueryTable3"
            v-if="getPastTimeData.schemas[SelectedSchema] != null"
          >
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center topquery-table-th">Rank</th>
                  <th class="text-left topquery-table-th">SQL ID</th>
                  <th class="text-left topquery-table-th">SQL</th>
                  <th class="text-left topquery-table-th">Buffer Gets</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(query, index) in getPastTimeData.schemas[
                    SelectedSchema
                  ].bufferGets"
                  :key="index"
                  @click="getBufferQueryDetail('schemaTopQueryTable3', index)"
                  class="real-query-hover"
                >
                  <td class="text-center" style="width: 70px">
                    {{ index + 1 }}
                  </td>
                  <td style="width: 250px">{{ query.sqlId }}</td>
                  <td align="left" class="query-table-sql">{{ query.sql }}</td>
                  <td>
                    <div
                      style="
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        width: 85px;
                      "
                    >
                      <span>{{ query.bufferGetsRatio }} %</span>
                      <v-progress-circular
                        v-if="query.bufferGetsRatio != 0"
                        style="margin-left: 10px"
                        :value="query.bufferGetsRatio"
                        :size="20"
                        :width="5"
                        color="#6440e3 "
                      ></v-progress-circular>
                    </div>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-tab-item>
      </v-tabs-items>

      <!-- Top Query Detail -->
      <v-card
        elevation="2"
        v-if="detailData"
        style="margin-top: 10px"
        class="animate__animated animate__fadeIn animate__fadeIn"
      >
        <v-card-text>
          <div>
            <v-icon>mdi-database</v-icon>
            <b style="font-size: 16px"> {{ detailData.parsingSchemaName }}</b>
          </div>
          <div class="realtime-query-detail-sql" style="padding: 5px 29px 0px">
            <div>
              <p>SQL ID</p>
              <b>{{ detailData.sqlId }}</b>
            </div>
            <div>
              <p>SQL</p>
              <b style="word-break: normal; width: 90%">{{ detailData.sql }}</b>
            </div>
          </div>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-text style="padding: 10px 16px">
          <div style="display: flex">
            <div class="schema-query-detail-left">
              <div>
                <h4 class="query-detail-title">Executions</h4>
                <span class="query-detail-text">
                  {{ detailData.executions }} <span>count</span>
                </span>
              </div>

              <div>
                <h4 class="query-detail-title">LastActiveTime</h4>
                <span
                  class="query-detail-text"
                  style="font-size: 22px; color: var(--main-point-color)"
                >
                  {{ detailData.lastActiveTime.split(" ")[0] }}
                  <span style="margin-left: 5px"></span>
                  {{ detailData.lastActiveTime.split(" ")[1] }}
                </span>
              </div>
            </div>
            <div class="schema-query-detail-right">
              <div>
                <h3 class="query-detail-title" style="margin: 10px 0px -5px">
                  CpuTime
                </h3>
                <!-- <div class="query-detail-text">
                  {{ detailData.cpuTimeRatio }}
                </div> -->
                <div class="schema-query-detail-pie">
                  <IEcharts :option="option2" :resizable="true" />
                </div>
              </div>
              <div>
                <h3 class="query-detail-title" style="margin: 10px 0px -5px">
                  BufferGets
                </h3>
                <!-- <div class="query-detail-text">
                  {{ detailData.bufferGetsRatio }}
                </div> -->
                <div class="schema-query-detail-pie">
                  <IEcharts :option="option1" :resizable="true" />
                </div>
              </div>

              <div>
                <h3 class="query-detail-title" style="margin: 10px 0px -5px">
                  ElapsedTime
                </h3>
                <!-- <div class="query-detail-text">
                  {{ detailData.elapsedTimeRatio }}
                </div> -->
                <div class="schema-query-detail-pie">
                  <IEcharts :option="option3" :resizable="true" />
                </div>
              </div>
              <!--<div class="query-detail-title">module</div>
            <div class="query-detail-text">
            {{ detailData.module }}
            </div>-->
            </div>
          </div>
        </v-card-text>
      </v-card>

      <v-card
        elevation="2"
        color="#E0E0E0"
        class="realtime-non-query"
        style="margin-top: 10px"
        height="300px"
        v-else
      >
        <v-card-text style="display: flex; justify-content: center">
          <h3>Query Detail</h3>
        </v-card-text>
      </v-card>
    </div>
  </div>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapGetters } from "vuex";

export default {
  name: "SchemaTopQuery",
  components: {
    IEcharts,
  },
  data() {
    return {
      detailData: false,
      tab: null,
      items: [
        { tab: "CPU Time", content: "" },
        { tab: "Elapsed Time", content: "" },
        { tab: "Buffer Gets", content: "" },
      ],
      option1: {
        // title: {
        //   text: "Status"
        // },
        grid: {
          width: "100%",
          height: "100%",
          right: 10,
          left: 10,
          bottom: 0,
        },

        series: [
          {
            type: "pie",
            radius: ["50%", "80%"],
            label: {
              show: true,
              formatter: function (event) {
                if (event.data.name == "") return "";
                return "{c|" + event.data.value + "} {d|%}";
              },
              rich: {
                c: {
                  fontSize: "20",
                  fontWeight: "bold",
                  padding: [0, 0, 5, 0],
                },
                d: {
                  fontSize: "16",
                  fontWeight: "bold",
                  padding: [0, 0, 5, 0],
                },
              },
              position: "center",
            },

            labelLine: {
              show: false,
            },
            color: ["#6440e3", "#E0E0E0"],
            data: [
              {
                value: 0,
                name: "",
              },
              {
                value: 100,
                name: "",
              },
            ],
          },
        ],
      },
      option2: {
        // title: {
        //   text: "Status"
        // },
        grid: {
          width: "100%",
          height: "100%",
          right: 10,
          left: 10,
          bottom: 0,
        },

        series: [
          {
            type: "pie",
            radius: ["50%", "80%"],
            label: {
              show: true,
              formatter: function (event) {
                if (event.data.name == "") return "";
                return "{c|" + event.data.value + "} {d|%}";
              },
              rich: {
                c: {
                  fontSize: "20",
                  fontWeight: "bold",
                  padding: [0, 0, 5, 0],
                },
                d: {
                  fontSize: "16",
                  fontWeight: "bold",
                  padding: [0, 0, 5, 0],
                },
              },
              position: "center",
            },

            labelLine: {
              show: false,
            },
            color: ["#2196F3", "#E0E0E0"],
            data: [
              {
                value: 0,
                name: "",
              },
              {
                value: 100,
                name: "",
              },
            ],
          },
        ],
      },
      option3: {
        // title: {
        //   text: "Status"
        // },
        grid: {
          width: "100%",
          height: "100%",
          right: 10,
          left: 10,
          bottom: 0,
        },

        series: [
          {
            type: "pie",
            radius: ["50%", "80%"],
            label: {
              show: true,
              formatter: function (event) {
                if (event.data.name == "") return "";
                return "{c|" + event.data.value + "} {d|%}";
              },
              rich: {
                c: {
                  fontSize: "20",
                  fontWeight: "bold",
                  padding: [0, 0, 5, 0],
                },
                d: {
                  fontSize: "16",
                  fontWeight: "bold",
                  padding: [0, 0, 5, 0],
                },
              },
              position: "center",
            },

            labelLine: {
              show: false,
            },
            color: ["#2196F3", "#E0E0E0"],
            data: [
              {
                value: 0,
                name: "",
              },
              {
                value: 100,
                name: "",
              },
            ],
          },
        ],
      },
      clickRow: -1,
      selectedTableId: "",
    };
  },
  watch: {
    getPastTimeDataCheck: function () {
      if (this.clickRow != -1) {
        const trList = document
          .getElementById(this.selectedTableId)
          .getElementsByTagName("tr");
        trList[this.clickRow + 1].classList.remove("real-query-click-tr");
        this.clickRow = -1;
        this.selectedTableId = "";
      }
      this.tab = 0;
      this.detailData = false;
    },
  },
  methods: {
    tableSelect(tableId, index) {
      if (this.clickRow != -1) {
        const trList = document
          .getElementById(this.selectedTableId)
          .getElementsByTagName("tr");
        trList[this.clickRow + 1].classList.remove("real-query-click-tr");
      }
      const trList = document
        .getElementById(tableId)
        .getElementsByTagName("tr");
      trList[index + 1].classList.add("real-query-click-tr");
      this.clickRow = index;
      this.selectedTableId = tableId;
    },
    getCPUQueryDetail(tableId, index) {
      this.tableSelect(tableId, index);
      this.detailData = this.getPastTimeData.schemas[
        this.SelectedSchema
      ].cpuUsed[index];
      this.option2.series[0].data[0].value = this.detailData.cpuTimeRatio;
      this.option2.series[0].data[0].name = "CpuTime";

      this.option1.series[0].data[0].value = this.detailData.bufferGetsRatio;
      this.option1.series[0].data[0].name = "BufferGets";

      this.option3.series[0].data[0].value = this.detailData.elapsedTimeRatio;
      this.option3.series[0].data[0].name = "ElapsedTime";
    },
    getElapsedQueryDetail(tableIndex, index) {
      this.tableSelect(tableIndex, index);
      this.detailData = this.getPastTimeData.schemas[
        this.SelectedSchema
      ].elapsedTime[index];
      this.option2.series[0].data[0].value = this.detailData.cpuTimeRatio;
      this.option2.series[0].data[0].name = "CpuTime";

      this.option1.series[0].data[0].value = this.detailData.bufferGetsRatio;
      this.option1.series[0].data[0].name = "BufferGets";

      this.option3.series[0].data[0].value = this.detailData.elapsedTimeRatio;
      this.option3.series[0].data[0].name = "ElapsedTime";
    },
    getBufferQueryDetail(tableIndex, index) {
      this.tableSelect(tableIndex, index);
      this.detailData = this.getPastTimeData.schemas[
        this.SelectedSchema
      ].bufferGets[index];
      this.option1.series[0].data[0].value = this.detailData.bufferGetsRatio;
      this.option2.series[0].data[0].value = this.detailData.cpuTimeRatio;
      this.option2.series[0].data[0].name = "CpuTime";

      this.option1.series[0].data[0].name = "BufferGets";

      this.option3.series[0].data[0].value = this.detailData.elapsedTimeRatio;
      this.option3.series[0].data[0].name = "ElapsedTime";
    },
  },
  computed: {
    ...mapGetters("Schema", ["getPastTimeData", "getPastTimeDataCheck"]),
    ...mapGetters("Schema", ["SelectedSchema"]),
  },
};
</script>
<style>
.schema-query-detail-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 25%;
  margin-left: 25px;

  border-right: 2px solid rgb(226, 226, 226);
}
.schema-query-detail-left div:nth-child(1) {
  margin-bottom: 25px;
}
.schema-query-detail-right {
  display: flex;
  width: 75%;
  height: 200px;
  justify-content: space-around;
  align-items: center;
}
.schema-query-detail-right div {
  width: 33.33%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.schema-query-detail-right h4 {
  width: 100%;
  text-align: center;
}
.schema-query-detail-pie {
  width: 100% !important;
  height: 80%;
  padding: 10px 0px;
}
</style>
