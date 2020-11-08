<template>
  <div>
    <div v-for="query in getTopQueryList" :key="query.sqlId">
      {{ query.sqlId }}
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "SchemaTopQuery",
  data() {
    return {
      cpuMonitor: true,
      timeMonitor: false,
      resourceMonitor: false
    };
  },
  methods: {
    // test(queryTest) {
    //   this.testdata = queryTest.queryData;
    // }
    showBox(number) {
      if (number == 1) {
        this.cpuMonitor = true;
        this.timeMonitor = false;
        this.resourceMonitor = false;
      } else if (number == 2) {
        this.cpuMonitor = false;
        this.timeMonitor = true;
        this.resourceMonitor = false;
      } else {
        this.cpuMonitor = false;
        this.timeMonitor = false;
        this.resourceMonitor = true;
      }
    }
  },
  computed: {
    ...mapGetters("TopQuery", ["getTopQueryList"])
  }
};
</script>

<style></style>
