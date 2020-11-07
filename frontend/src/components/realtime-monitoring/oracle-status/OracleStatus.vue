<template>
  <div>
    <h2>Oracle Status</h2>
    <OracleCpu />
    <OracleStorage />
    <OracleMemory />
    <OracleProcess />
  </div>
</template>

<script>
import OracleCpu from "@/components/realtime-monitoring/oracle-status/OracleCpu.vue";
import OracleStorage from "@/components/realtime-monitoring/oracle-status/OracleStorage.vue";
import OracleMemory from "@/components/realtime-monitoring/oracle-status/OracleMemory.vue";
import OracleProcess from "@/components/realtime-monitoring/oracle-status/OracleProcess.vue";
export default {
  name: "OracleStatus",
  components: {
    OracleCpu,
    OracleStorage,
    OracleMemory,
    OracleProcess
  }
};
</script>

<style></style>
