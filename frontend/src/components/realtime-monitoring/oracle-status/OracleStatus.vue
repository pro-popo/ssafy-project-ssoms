<template>
  <div style="height: 70vh;margin-bottom:15px">
    <h2 style="margin-bottom:15px">OracleDB Status</h2>
    <div style="height: 30vh;">
      <OracleCpu />
    </div>
    <div style="display:flex; height:30vh">
      <div
        style="width:75%;height:100%; margin:15px 15px 15px 0px; display:flex"
      >
        <div style="width:25%;">
          <OracleProcessExecution />
        </div>
        <div style="width:75%;display:flex; flex-direction: column;">
          <div style="height:50%;width:100%; margin-bottom:15px">
            <OracleProcess />
          </div>
          <div style="height:50%;width:100%;">
            <OracleMemory />
          </div>
        </div>
      </div>
      <div style="width:25%;height:100%; margin:15px 0px 15px 0px;">
        <OracleStorage />
      </div>
    </div>
  </div>
</template>

<script>
import OracleCpu from "@/components/realtime-monitoring/oracle-status/OracleCpu.vue";
import OracleMemory from "@/components/realtime-monitoring/oracle-status/OracleMemory.vue";
import OracleProcess from "@/components/realtime-monitoring/oracle-status/OracleProcess.vue";
import OracleProcessExecution from "@/components/realtime-monitoring/oracle-status/OracleProcessExecution.vue";
import OracleStorage from "@/components/realtime-monitoring/oracle-status/OracleStorage.vue";
export default {
  name: "OracleStatus",
  components: {
    OracleCpu,
    OracleMemory,
    OracleProcess,
    OracleProcessExecution,
    OracleStorage
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
  width: 90px !important;

  position: initial;
}

.data-increase {
  color: #ef5350;
}
.data-increase .v-icon {
  color: #ef5350;
  margin-left: -5px;
}

.data-decrease {
  color: #8bc34a;
}
.data-decrease .v-icon {
  color: #8bc34a;
  margin-left: -5px;
}
</style>
