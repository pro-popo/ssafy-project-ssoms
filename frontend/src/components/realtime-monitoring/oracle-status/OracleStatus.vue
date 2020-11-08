<template>
  <div>
    <h2>Oracle Status</h2>
    <OracleCpu />
    <OracleProcess />
    <OracleMemory />
  </div>
</template>

<script>
import OracleCpu from "@/components/realtime-monitoring/oracle-status/OracleCpu.vue";
import OracleMemory from "@/components/realtime-monitoring/oracle-status/OracleMemory.vue";
import OracleProcess from "@/components/realtime-monitoring/oracle-status/OracleProcess.vue";
export default {
  name: "OracleStatus",
  components: {
    OracleCpu,
    OracleMemory,
    OracleProcess
  }
};
</script>

<style></style>
