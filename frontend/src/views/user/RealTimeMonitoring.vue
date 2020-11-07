<template>
  <div>
    Real-Time Monitoring

    {{ getRealTime }}
    <SchemaChart />
    <OracleResource />
    <RealTimeTopQuery />
    <RealTimeQueryDetail />
  </div>
</template>

<script>
import SchemaChart from "@/components/pc-resource/SchemaChart.vue";
import OracleResource from "@/components/oracle-status/OracleResource.vue";
import RealTimeTopQuery from "@/components/query/RealTimeTopQuery.vue";
import RealTimeQueryDetail from "@/components/query/RealTimeQueryDetail.vue";

export default {
  name: "RealTimeMonitoring",
  components: {
    SchemaChart,
    OracleResource,
    RealTimeTopQuery,
    RealTimeQueryDetail
  }
};
</script>

<style></style>
