<template>
  <div>
    <b class="mb-3">{{ getRealTime }}</b>
    <OracleStatus />
    <AllSchemaStastics />
    <v-divider></v-divider>
    <h2 class="mb-2 mt-2">Top Query</h2>
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

import { mapGetters } from "vuex";
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
