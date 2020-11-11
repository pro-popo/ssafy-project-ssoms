<template>
  <div>
    <div
      style="display:flex;  justify-content: space-between; align-items:flex-start; margin-bottom:15px"
    >
      <h3>
        Real-Time Monitorting
      </h3>

      <div align="right">
        <span style="font-size:14px">Last Update</span>
        <br />
        <span style="font-size:14px"
          ><v-icon size="16">mdi-clock-time-four-outline</v-icon>
          {{ getRealTime }}</span
        >
      </div>
    </div>
    <OracleStatus />
    <v-divider></v-divider>
    <AllSchemaStastics />
    <v-divider></v-divider>
    <h2 class="mb-3 mt-3">Top Query</h2>
    <div class="realtime-top-query-outer">
      <AllSchemaTopQuery class="top-query-left" />
      <AllSchemaTopQueryDetail class="top-query-right" />
    </div>
  </div>
</template>

<script>
import OracleStatus from "@/components/realtime-monitoring/oracle-status/OracleStatus.vue";
import AllSchemaTopQuery from "@/components/realtime-monitoring/query/AllSchemaTopQuery.vue";
import AllSchemaTopQueryDetail from "@/components/realtime-monitoring/query/AllSchemaTopQueryDetail.vue";
import AllSchemaStastics from "@/components/realtime-monitoring/schema-status/AllSchemaStastics.vue";

import { mapGetters, mapActions } from "vuex";
export default {
  name: "RealTimeMonitoring",
  components: {
    OracleStatus,
    AllSchemaTopQuery,
    AllSchemaTopQueryDetail,
    AllSchemaStastics
  },
  computed: {
    ...mapGetters(["getRealTime"])
  },
  methods: {
    ...mapActions(["initRealTimeData"])
  },
  created() {
    this.initRealTimeData();
  }
};
</script>

<style>
.realtime-top-query-outer {
  display: flex;
  justify-content: space-between;
}
.top-query-left {
  width: 65%;
}
.top-query-right {
  width: 30%;
  margin: auto 0px;
}
</style>
