<template>
  <div>
    <!-- <h2 style="margin-top:20px; color:var(--font-main-color);">
      {{ SelectedSchema }}'<span style="margin-left:3px" />s Top Query
    </h2> -->
    <v-tabs
      style=" width: 100%; "
      background-color="#fbfdfd"
      center-active
      v-model="tab"
    >
      <v-tab style="background:#fbfdfd" v-for="item in items" :key="item.tab">
        {{ item.tab }}
      </v-tab>
    </v-tabs>
    <v-row style="height: 300px; dispaly:flex" v-if="getPastTimeData.check">
      <v-col cols="7">
        <v-tabs-items v-model="tab">
          <v-tab-item>
            <v-simple-table
              fixed-header
              height="300px"
              class="elevation-2 realtime-top-query"
            >
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-center">Rank</th>
                    <th class="text-center">SQL ID</th>
                    <th class="text-center">SQL</th>
                    <th class="text-center">Schema Name</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(query, index) in getPastTimeData.schemas[
                      SelectedSchema
                    ].cpuUsed"
                    :key="index"
                    @click="getCPUQueryDetail(index)"
                    class="real-query-hover"
                  >
                    <td>{{ index + 1 }}</td>
                    <td>{{ query.sqlId }}</td>
                    <td align="left">{{ query.sql }}</td>
                    <td>{{ query.parsingSchemaName }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-tab-item>
          <v-tab-item>
            <v-simple-table
              fixed-header
              height="300px"
              class="elevation-2 realtime-top-query"
            >
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-center">Rank</th>
                    <th class="text-center">SQL ID</th>
                    <th class="text-center">SQL</th>
                    <th class="text-center">Schema Name</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(query, index) in getPastTimeData.schemas[
                      SelectedSchema
                    ].elapsedTime"
                    :key="index"
                    @click="getElapsedQueryDetail(index)"
                    class="real-query-hover"
                  >
                    <td>{{ index + 1 }}</td>
                    <td>{{ query.sqlId }}</td>
                    <td align="left">{{ query.sql }}</td>
                    <td>{{ query.parsingSchemaName }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-tab-item>
          <v-tab-item>
            <v-simple-table
              fixed-header
              height="300px"
              class="elevation-2 realtime-top-query"
            >
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-center">Rank</th>
                    <th class="text-center">SQL ID</th>
                    <th class="text-center">SQL</th>
                    <th class="text-center">Schema Name</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(query, index) in getPastTimeData.schemas[
                      SelectedSchema
                    ].bufferGets"
                    :key="index"
                    @click="getBufferQueryDetail(index)"
                    class="real-query-hover"
                  >
                    <td>{{ index + 1 }}</td>
                    <td>{{ query.sqlId }}</td>
                    <td align="left">{{ query.sql }}</td>
                    <td>{{ query.parsingSchemaName }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-tab-item>
        </v-tabs-items>
      </v-col>

      <v-col cols="5">
        <v-card
          elevation="2"
          class="realtime-detail-query"
          height="300px"
          style="display:flex; justify-content: space-around; align-items: center"
          v-if="detailData"
        >
          <div>
            <div class="query-detail-title">executions</div>
            <div class="query-detail-text">{{ detailData.executions }}</div>
            <div class="query-detail-title">bufferGetsRatio</div>
            <div class="query-detail-text">
              {{ detailData.bufferGetsRatio }}
            </div>
            <div class="query-detail-title">cpuTimeRatio</div>
            <div class="query-detail-text">{{ detailData.cpuTimeRatio }}</div>
          </div>
          <div>
            <div class="query-detail-title">elapsedTimeRatio</div>
            <div class="query-detail-text">
              {{ detailData.elapsedTimeRatio }}
            </div>
            <div class="query-detail-title">lastActiveTime</div>
            <div class="query-detail-text">
              {{ detailData.lastActiveTime.split(" ")[0] }}<br />{{
                detailData.lastActiveTime.split(" ")[1]
              }}
            </div>
            <!--<div class="query-detail-title">module</div>
            <div class="query-detail-text">
            {{ detailData.module }}
            </div>-->
          </div>
        </v-card>
        <v-card
          elevation="2"
          color="#E0E0E0"
          class="realtime-non-query"
          height="300px"
          v-else
        >
          <div>Query Detail</div>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "SchemaTopQuery",
  data() {
    return {
      detailData: false,
      tab: null,
      items: [
        { tab: "CPU", content: "" },
        { tab: "Elapsed", content: "" },
        { tab: "Buffer", content: "" }
      ]
    };
  },
  methods: {
    getCPUQueryDetail(index) {
      this.detailData = this.getPastTimeData.schemas[
        this.SelectedSchema
      ].cpuUsed[index];
    },
    getElapsedQueryDetail(index) {
      this.detailData = this.getPastTimeData.schemas[
        this.SelectedSchema
      ].elapsedTime[index];
    },
    getBufferQueryDetail(index) {
      this.detailData = this.getPastTimeData.schemas[
        this.SelectedSchema
      ].bufferGets[index];
    }
  },
  computed: {
    ...mapGetters("Schema", ["getPastTimeData"]),
    ...mapGetters("Schema", ["SelectedSchema"])
  }
};
</script>
