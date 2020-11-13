<template>
  <div class="realtime-top-query-outer" height="300px" v-if="getPastTimeData.check">
      <div class="top-query-left">
      <div>
            <v-simple-table
            fixed-header
            height="300px"
            class="elevation-2 realtime-top-query"
            >
            <template v-slot:default>
                <thead>
                <tr>
                    <th class="text-center">
                    Rank
                    </th>
                    <th class="text-center">
                    SQL ID
                    </th>
                    <th class="text-center">
                    SQL
                    </th>
                    <th class="text-center">
                    Schema Name
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr
                    v-for="(query, index) in getPastTimeData.schemas[SelectedSchema].cpuUsed"
                    :key="index"
                    @click="getQueryDetail(index)"
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
      </div>
      </div>
      <div class="top-query-right">
    <div >
        <v-card
        elevation="2"
        class="realtime-detail-query"
        height="230px"
        style="justify-content: space-around;"
        v-if="detailData"
        >
        <div>
            <div class="query-detail-title">executions</div>
            <div class="query-detail-text">{{ detailData.executions }}</div>
            <div class="query-detail-title">bufferGetsRatio</div>
            <div class="query-detail-text">{{ detailData.bufferGetsRatio }}</div>
            <div class="query-detail-title">cpuTimeRatio</div>
            <div class="query-detail-text">{{ detailData.cpuTimeRatio }}</div>
        </div>
        <div>
            <div class="query-detail-title">elapsedTimeRatio</div>
            <div class="query-detail-text">
            {{ detailData.elapsedTimeRatio }}
            </div>
            <div class="query-detail-title">lastActiveTime</div>
            <div class="query-detail-text">{{ detailData.lastActiveTime.split(' ')[0] }}<br>{{detailData.lastActiveTime.split(' ')[1]}}</div>
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
        height="230px"
        v-else
        >
        <div>자세히 보고싶은 Query를 선택해주세요.</div>
        </v-card>
    </div>
  </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "SchemaTopQuery",
  data() {
    return {
        detailData : false
    };
  },
  methods: {
    getQueryDetail(index) {
      this.detailData = this.getPastTimeData.schemas[this.SelectedSchema].cpuUsed[index]
    },
  },
  computed: {
    ...mapGetters("Schema", ["getPastTimeData"]),
    ...mapGetters("Schema", ["SelectedSchema"])
  }
};
</script>

<style>

</style>
