<template>
  <div>
    <h2>OracleDB Status</h2>
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

<style>
.oracle-chart {
  top: -30px !important;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  position: initial;
  height: 100%;
  width: 90%;
}

.oracle-title-icon {
  display: flex;
  align-items: center;
  position: absolute;
  width: 100%;
  z-index: 2;
}
.oracle-title-icon :nth-child(2) {
  color: var(--font-sub2-color);
  font-size: 16px;
  font-weight: bold;
}

.oracle-title-icon #chart-title-icon {
  background: linear-gradient(
    to bottom right,
    var(--main-color),
    var(--main-point-color)
  );
  border-radius: 100px;
  height: 30px;
  width: 30px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
}

.oracle-chart-menu {
  color: red;
}
.small-chart {
  height: 25px !important;
  width: 80px !important;

  position: initial;
}

.data-increase {
  color: #ef5350;
}
.data-increase .v-icon {
  color: #ef5350;
}

.data-decrease {
  color: #8bc34a;
}
.data-decrease .v-icon {
  color: #8bc34a;
}
</style>
