<template>
  <div class="realtime-detail-box">
    <v-card elevation="10" class="realtime-detail-query">
      <span class="realtime-detail-item"
        >executions : {{ getTopQueryDetail.executions }}</span
      >
      <span>bufferGets : {{ getTopQueryDetail.bufferGets }}</span>
      <span>diskReads : {{ getTopQueryDetail.diskReads }}</span>
      <span>rowsProcessed : {{ getTopQueryDetail.rowsProcessed }}</span>
      <span>cpuTimeAvg : {{ getTopQueryDetail.cpuTimeAvg }}</span>
      <span>elapsedTimeAvg : {{ getTopQueryDetail.elapsedTimeAvg }}</span>
    </v-card>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "AllSchemaTopQuery",
  computed: {
    ...mapGetters("TopQuery", ["getTopQueryDetail"])
  }
};
</script>

<style>
.realtime-detail-query {
  /* display: inline-block; */
  width: 1000px;
  height: 200px;
  margin: 0 auto;
}
</style>
