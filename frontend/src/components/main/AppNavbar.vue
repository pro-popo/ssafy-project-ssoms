<template>
  <v-app-bar class="nav-bar" dense dark color="#202021">
    <v-toolbar-title class="logo" onclick="location.href=''"
      ><img class="logo-back" src=@/assets/SSOMS-long.png>
    </v-toolbar-title>

    <v-spacer></v-spacer>
    <v-menu offset-y elevation="1">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          small
          icon
          v-bind="attrs"
          v-on="on"
          @click="outlierlog"
          style="margin-right: 0px"
        >
          <v-badge overlap dot color="red" :value="logAlarm">
            <v-icon>mdi-bell</v-icon>
          </v-badge>
        </v-btn>
      </template>
      <v-list
        v-if="getOutlierLog.length == 0"
        class="log-list"
        style="width: 300px"
        dark
        color="var(--font-main-color)"
      >
        <v-list-item>
          <v-list-item-title style="text-align: center; margin: 10px 0px">
            <v-icon size="60" color="success">mdi-check-circle-outline</v-icon>
          </v-list-item-title>
        </v-list-item>
        <v-list-item align="center">
          <span style="word-break: keep-all; margin-bottom: 10px">
            Less than 50% of Oracle DB CPU Time does not exist.
          </span>
        </v-list-item>
      </v-list>
      <v-list v-else class="log-list">
        <v-list-item style="height: 60px">
          <v-list-item-subtitle>
            <h3 style="padding-bottom: 5px">Oracle DB CPU Time Outlier Data</h3>
            <span>{{ getOutlierLog[0].time.substring(0, 11) }}</span>
          </v-list-item-subtitle>
        </v-list-item>
        <v-divider style="margin-top: 5px"></v-divider>
        <v-list-item v-for="(log, index) in getOutlierLog" :key="'log' + index">
          <v-list-item-title>
            <v-row justify="space-between" style="align-items: center">
              <v-col cols="1">
                <v-icon color="error">mdi-alert-circle</v-icon>
              </v-col>
              <v-col cols="4" style="color: var(--font-sub2-color)">
                <span>{{ log.time.substring(11) }}</span>
                <span style="font-size: 12px">
                  {{ log.time.substring(11, 13) > 12 ? "PM" : "AM" }}</span
                >
              </v-col>
              <v-col class="text-right" cols="6">
                <span style="font-size: 13px; color: var(--font-sub-color)">
                  Oracle DB CPU Time
                </span>
                <br /><span
                  style="font-size: 18px; color: var(--font-sub2-color)"
                >
                  <b>{{ log.databaseCpuTimeRatio }}</b>
                </span>
                %
              </v-col>
            </v-row>
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "AppNavbar",
  data() {
    return {
      logflag: true,
      logAlarm: null,
    };
  },
  watch: {
    getOutlierLog: function () {
      this.logAlarm = true;
    },
  },
  methods: {
    ...mapActions("Realtime", ["getOutlierData"]),
    outlierlog() {
      this.logAlarm = null;
      // let start = new Date().toISOString().substr(0, 10);
      // let end = new Date();
      // end.setDate(end.getDate() + 1);
      // end = end.toISOString().substr(0, 10);
      // this.getOutlierData({ start: "/" + start, end: "/" + end });
      this.logflag = !this.logflag;
    },
  },
  computed: {
    ...mapGetters("Realtime", ["getOutlierLog"]),
  },
};
</script>

<style>
.display_hidden {
  display: none;
}
.log_list {
  list-style-type: none;
  padding: 0px !important;
}
.log {
  border-bottom: 1px gray solid;
  text-align: center;
}
.log_div {
  color: black;
  position: absolute;
  top: 48px;
  right: 0px;
  width: 200px;
  max-height: 200px;
  color: white;
  opacity: 1;
  overflow: auto;
  padding: 0px;
  margin: 0px;
  background-color: rgb(41, 41, 42);
  z-index: 20;
}
.nav-bar {
  z-index: 10 !important;
  background: rgb(41, 41, 42);
}

.logo {
  margin-left: 56px;
  cursor: pointer;
  font-weight: 550;
}

.logo-back {
  margin-top: 0.5rem;
  width: 120px;
}

.log-list {
  max-height: 400px;
  width: 320px;
  overflow: auto;
}
</style>
