<template>
  <div>
    <b>{{ getRealTime }}</b>
    <AllSchemaTopQuery />
    <AllSchemaTopQueryDetail />
  </div>
</template>

<script>
import AllSchemaTopQuery from "@/components/realtime-monitoring/query/AllSchemaTopQuery.vue";
import AllSchemaTopQueryDetail from "@/components/realtime-monitoring/query/AllSchemaTopQueryDetail.vue";
import { mapGetters } from "vuex";
export default {
  name: "RealTimeMonitoring",
  components: {
    AllSchemaTopQuery,
    AllSchemaTopQueryDetail
  },
  computed: {
    ...mapGetters(["getRealTime"])
  }
};
</script>

<style></style>
