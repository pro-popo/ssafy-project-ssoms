<template>
  <div class="d-flex" height="300px" v-if="getPastTimeData.check">
      <div class="top-query-left">
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

      
    <div class="top-query-right">
        <v-card
        elevation="2"
        class="realtime-detail-query"
        height="230px"
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
            <div class="query-detail-text">{{ detailData.lastActiveTime }}</div>
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
.realtime-detail-query {
  display: flex;
  justify-content: space-around;
}
.realtime-non-query {
  display: flex;
  justify-content: center;
  align-items: center;
}
.realtime-non-query > div {
  font-weight: 900;
  opacity: 0.6;
}
.query-detail-text {
  font-size: 30px;
  font-weight: 900;
  color: #6440e3;
}
.query-detail-title {
  opacity: 0.6;
}
.realtime-detail-query > div {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
