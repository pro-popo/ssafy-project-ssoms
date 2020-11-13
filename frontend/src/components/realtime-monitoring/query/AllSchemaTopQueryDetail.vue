<template>
  <div>
    <v-card
      elevation="2"
      class="realtime-detail-query"
      height="230px"
      v-if="getTopQueryDetail"
    >
      <div>
        <div class="query-detail-title">executions</div>
        <div class="query-detail-text">{{ getTopQueryDetail.executions }}</div>
        <div class="query-detail-title">bufferGets</div>
        <div class="query-detail-text">{{ getTopQueryDetail.bufferGets }}</div>
        <div class="query-detail-title">diskReads</div>
        <div class="query-detail-text">{{ getTopQueryDetail.diskReads }}</div>
      </div>
      <div>
        <div class="query-detail-title">rowsProcessed</div>
        <div class="query-detail-text">
          {{ getTopQueryDetail.rowsProcessed }}
        </div>
        <div class="query-detail-title">cpuTimeAvg</div>
        <div class="query-detail-text">{{ getTopQueryDetail.cpuTimeAvg }}</div>
        <div class="query-detail-title">elapsedTimeAvg</div>
        <div class="query-detail-text">
          {{ getTopQueryDetail.elapsedTimeAvg }}
        </div>
      </div>
    </v-card>
    <v-card
      elevation="2"
      color="#E0E0E0"
      class="realtime-non-query"
      height="230px"
      v-else
    >
      <div>Query Detail</div>
    </v-card>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "AllSchemaTopQueryDetail",
  computed: {
    ...mapGetters("TopQuery", ["getTopQueryDetail"])
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
