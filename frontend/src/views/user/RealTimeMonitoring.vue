<template>
  <div>
    {{ getRealTime }}
    <OracleStatus />
    <AllSchemaTopQuery />
    <AllSchemaTopQueryDetail />
  </div>
</template>

<script>
import OracleStatus from "@/components/realtime-monitoring/oracle-status/OracleStatus.vue";
import AllSchemaTopQuery from "@/components/realtime-monitoring/query/AllSchemaTopQuery.vue";
import AllSchemaTopQueryDetail from "@/components/realtime-monitoring/query/AllSchemaTopQueryDetail.vue";
import { mapGetters } from "vuex";
export default {
  name: "RealTimeMonitoring",
  components: {
    OracleStatus,
    AllSchemaTopQuery,
    AllSchemaTopQueryDetail
  },
  computed: {
    ...mapGetters(["getRealTime"])
  }
};
</script>

<style></style>
