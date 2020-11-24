<template>
  <div style="height: 72vh; margin-bottom:15px">
    <h2
      style="font-size:22px; margin-bottom:15px; color:var(--font-main-color);"
    >
      Oracle DB Status
      <div style="float: right">
        <span
          v-if="getRealTime !== 0"
          style="font-size: 14px; margin-right: 10px"
          ><v-icon size="16">mdi-clock-time-four-outline</v-icon>
          {{ getRealTime.substring(0, 11) }}
          {{ getRealTimeList[selectedRealTime] }}</span
        >
        <span class="live-font mr-1">LIVE</span>
        <v-tooltip top style="margin-left: auto">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              v-if="selectedRealTime === getRealTimeList.length - 1"
              fab
              color="red accent-4"
              dark
              x-small
              elevation="2"
              v-bind="attrs"
              v-on="on"
              @click="startRealTime"
              ><v-icon size="22">mdi-access-point</v-icon></v-btn
            >
            <v-btn
              v-else
              fab
              color="grey"
              x-small
              elevation="2"
              v-bind="attrs"
              v-on="on"
              @click="startRealTime"
              ><v-icon size="18">mdi-access-point</v-icon></v-btn
            >
          </template>
          <span>Move Last Update</span>
        </v-tooltip>
      </div>
    </h2>

    <div :class="{ mainCpu: isMainView, notMainCpu: !isMainView }">
      <OracleCpu @changeMainView="changeMainView" :isMainView="isMainView" />
    </div>
    <div style="display: flex; height: 32vh" v-show="!isMainView">
      <div
        style="
          width: 75%;
          height: 100%;
          margin: 15px 15px 15px 0px;
          display: flex;
        "
      >
        <div style="width: 25%">
          <OracleProcessExecution />
        </div>
        <div style="width: 75%; display: flex; flex-direction: column">
          <div style="height: 50%; width: 100%; margin-bottom: 15px">
            <OracleProcess />
          </div>
          <div style="height: 50%; width: 100%">
            <OracleMemory />
          </div>
        </div>
      </div>
      <div style="width: 25%; height: 100%; margin: 15px 0px 15px 0px">
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
import { mapGetters, mapMutations, mapActions } from "vuex";

export default {
  name: "OracleStatus",
  data() {
    return {
      isMainView: false
    };
  },
  components: {
    OracleCpu,
    OracleMemory,
    OracleProcess,
    OracleProcessExecution,
    OracleStorage
  },

  methods: {
    changeMainView() {
      this.isMainView = !this.isMainView;
    },
    ...mapMutations(["SET_SELECTED_REALTIME"]),
    ...mapActions(["initRealTimeData"]),
    startRealTime() {
      this.SET_SELECTED_REALTIME(-1);
      this.initRealTimeData();
    }
  },
  computed: {
    ...mapGetters(["selectedRealTime", "getRealTimeList", "getRealTime"])
  }
};
</script>

<style>
.mainCpu {
  height: 62vh;
}
.notMainCpu {
  height: 30vh;
}
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
  max-width: 90px !important;
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
.oracle-status-units {
  color: var(--font-sub-color);
  margin-left: 3px;
  font-size: 14px;
}

.data-default {
  margin-bottom: -5px;
}

.live-font {
  font-size: 15px;
  /* float: right; */
}
</style>
