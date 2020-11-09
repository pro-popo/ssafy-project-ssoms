<template>
  <div class="realtime-detail-box">
    <v-card elevation="2" class="realtime-detail-query">
      <span>
        executions
        <div>{{ getTopQueryDetail.executions }}</div>
      </span>
      <span
        >bufferGets
        <div>{{ getTopQueryDetail.bufferGets }}</div></span
      >
      <span
        >diskReads
        <div>{{ getTopQueryDetail.diskReads }}</div></span
      >
      <span
        >rowsProcessed
        <div>{{ getTopQueryDetail.rowsProcessed }}</div></span
      >
      <span
        >cpuTimeAvg
        <div>{{ getTopQueryDetail.cpuTimeAvg }}</div></span
      >
      <span
        >elapsedTimeAvg
        <div>{{ getTopQueryDetail.elapsedTimeAvg }}</div></span
      >
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
  width: 80%;
  height: 20%;
  margin: 0 auto;
  padding: 20px 0;
}
.realtime-detail-query > span > div {
  font-size: 30px;
  font-weight: 900;
}
</style>
