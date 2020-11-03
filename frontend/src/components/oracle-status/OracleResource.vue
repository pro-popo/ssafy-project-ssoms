<template>
  <div>
    <OracleStatus />
    <OracleStorage />
    <OracleMemory />
    <OracleProcess />
  </div>
</template>

<script>
import OracleStatus from "@/components/oracle-status/OracleStatus.vue";
import OracleStorage from "@/components/oracle-status/OracleStorage.vue";
import OracleMemory from "@/components/oracle-status/OracleMemory.vue";
import OracleProcess from "@/components/oracle-status/OracleProcess.vue";
export default {
  name: "OracleResource",
  components: {
    OracleStatus,
    OracleStorage,
    OracleMemory,
    OracleProcess
  }
};
</script>

<style></style>
