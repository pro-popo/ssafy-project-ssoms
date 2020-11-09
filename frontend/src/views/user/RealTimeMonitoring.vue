<template>
  <div>
    <div align="right">
      Last Update Time : <b>{{ getRealTime }}</b>
    </div>
    <OracleStatus />
    <AllSchemaStastics />
    <AllSchemaTopQuery />
    <AllSchemaTopQueryDetail v-if="getTopQueryDetail" />
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
    ...mapGetters(["getRealTime"]),
    ...mapGetters("TopQuery", ["getTopQueryDetail"])
  }
};
</script>

<style></style>
