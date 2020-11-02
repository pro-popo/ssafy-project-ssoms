<template>
  <div class="query-margin">
    <div class="mb-3">
      <b>
        <span>Top Query</span> &
        <span>Query Detail</span>
      </b>
    </div>
    <div class="mb-2">
      <span>스키마명 : {{ schemaName }}</span>
      <div class="query-calendar">
        <span><input type="date" id="startDate"/></span>
        <span><input type="date" id="endDate"/></span>
        <button class="query-button">조회</button>
      </div>
    </div>
    <div class="main-query-box">
      <WholeQuery class="query-box1" />
      <TimeQuery class="query-box2" />
      <TopQuery class="query-box3" />
    </div>
  </div>
</template>

<script>
import WholeQuery from "@/components/query/WholeQuery.vue";
import TimeQuery from "@/components/query/TimeQuery.vue";
import TopQuery from "@/components/query/TopQuery.vue";

import { mapGetters } from "vuex";

export default {
  name: "QueryMonitoring",
  components: {
    WholeQuery,
    TimeQuery,
    TopQuery
  },
  methods: {},
  computed: {
    ...mapGetters("Query", ["schemaName"])
  },
  mounted() {
    document.getElementById(
      "startDate"
    ).value = new Date().toISOString().substring(0, 10);
    document.getElementById(
      "endDate"
    ).value = new Date().toISOString().substring(0, 10);
  }
};
</script>

<style>
.query-margin {
  margin: -10px -20px 0px -20px;
}
.main-query-box {
  display: grid;
  grid-template-columns: 300px 390px 450px;
  grid-template-rows: 120px 180px;
}
.query-box1 {
  grid-column-start: 1;
  grid-column-end: 4;
  grid-row-start: 1;
  grid-row-end: 2;
}
.query-box2 {
  grid-column-start: 3;
  grid-column-end: 4;
  grid-row-start: 2;
  grid-row-end: 3;
}
.query-box3 {
  grid-column-start: 1;
  grid-column-end: 3;
  grid-row-start: 2;
  grid-row-end: 3;
}

.query-calendar {
  float: right;
}
.query-button {
  border: 1px solid black;
}
</style>
