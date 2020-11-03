<template>
  <div class="whole-query-box1">
    <div>
      SQL cnt
      <div class="whole-text">{{ wholeQuery.sqlCount }}</div>
    </div>
    <div class="border-left">
      executions
      <div class="whole-text">{{ wholeQuery.executions }}</div>
    </div>
    <div class="border-left">
      bufferGetsAvg
      <div class="whole-text">{{ wholeQuery.bufferGetsAvg }}</div>
    </div>
    <div class="border-left">
      diskReadsAvg
      <div class="whole-text">{{ wholeQuery.diskReadsAvg }}</div>
    </div>
    <div class="border-left">
      rowProcessedAvg
      <div class="whole-text">{{ wholeQuery.rowsProcessedAvg }}</div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "WholeQuery",
  computed: {
    ...mapGetters("Query", ["wholeQuery"])
  }
};
</script>

<style>
.whole-query-box1 {
  /* background-color: rgba(135, 207, 235, 0.247); */
  /* background-color: yellow; */
  display: flex;
  padding: 5px;
}
.whole-query-box1 > div {
  width: 20%;
}
.border-left {
  border-left: 1px solid black;
  padding-left: 10px;
}
.whole-text {
  font-size: 40px;
  font-weight: 600;
}
</style>
