<template>
  <div class="whole-query-box1">
    <div>
      SQL cnt
      <div class="ttest">{{ wholeQuery.sqlCount }}</div>
    </div>
    <div>
      executions
      <div class="ttest">{{ wholeQuery.executions }}</div>
    </div>
    <div>
      bufferGetsAvg
      <div class="ttest">{{ wholeQuery.bufferGetsAvg }}</div>
    </div>
    <div>
      diskReadsAvg
      <div class="ttest">{{ wholeQuery.diskReadsAvg }}</div>
    </div>
    <div>
      rowProcessedAvg
      <div class="ttest">{{ wholeQuery.rowsProcessedAvg }}</div>
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
  background-color: yellow;
  height: 130px;
  display: flex;
  justify-content: space-between;
  padding: 5px;
}
.whole-query-box2 {
  /* background-color: rgba(135, 207, 235, 0.247); */
  background-color: red;
  height: 170px;
  display: flex;
  justify-content: space-between;
  /* float: right; */
}
.whole-query-box3 {
  /* background-color: rgba(135, 207, 235, 0.247); */
  background-color: yellow;
  height: 170px;
  display: flex;
  justify-content: space-between;
  /* float: right; */
}
.ttt {
  /* font-size: 30px; */
  float: right;
}
</style>
