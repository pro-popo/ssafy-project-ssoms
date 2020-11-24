<template>
  <div class="realtime-container" id="realtime-container">
    <div
      style="
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
      "
    >
      <v-navigation-drawer
        class="side-navbar-right"
        overlay-color="red"
        :expand-on-hover="fixedMini"
        :mini-variant.sync="mini"
        :width="260"
        permanent
        absolute
        right
        style="height: 100%; margin-top: 48px"
      >
        <div
          v-if="mini"
          style="
            height: 100%;
            margin-left: 1px;
            display: flex;
            align-items: center;
          "
        >
          <v-btn
            fab
            x-small
            outlined
            elevation="0"
            color="var(--font-sub-color)"
          >
            <v-icon size="25">mdi-menu-left</v-icon>
          </v-btn>
        </div>
        <v-card
          elevation="8"
          style="height: 100%; margin-left: 15px"
          v-if="!mini"
          class="animate__animated animate__fadeInRight"
        >
          <v-card-text>
            <h3 style="margin-bottom: 10px">Real-Time Monitorting</h3>
            <span
              v-if="getRealTime !== 0"
              style="font-size: 14px; margin-right: 35px"
              ><v-icon size="16">mdi-clock-time-four-outline</v-icon>
              {{ getRealTime.substring(0, 11) }}
              {{ getRealTimeList[selectedRealTime] }}</span
            >
            <v-tooltip top style="margin-left: auto">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  v-if="selectedRealTime === getRealTimeList.length - 1"
                  color="red accent-4"
                  dark
                  x-small
                  elevation="2"
                  v-bind="attrs"
                  v-on="on"
                  @click="startRealTime"
                  >LIVE</v-btn
                >
                <v-btn
                  v-else
                  color="grey"
                  x-small
                  elevation="2"
                  v-bind="attrs"
                  v-on="on"
                  @click="startRealTime"
                  >LIVE</v-btn
                >
              </template>
              <span>Move Last Update</span>
            </v-tooltip>
          </v-card-text>
          <v-divider style="margin-bottom: 20px"></v-divider>
          <template>
            <v-tabs
              vertical
              background-color="transparent"
              color="var(--font-sub2-color)"
              v-model="tab"
            >
              <v-tab @click="moveScroll(0)"
                ><span>Oracle DB Status</span>
              </v-tab>
              <v-tab @click="moveScroll(1)">All Schema Status</v-tab>
              <v-tab @click="moveScroll(2)">All Schema Top Query</v-tab>
            </v-tabs>
          </template>
        </v-card>
      </v-navigation-drawer>
      <h3>
        Real-Time Monitorting
        <v-dialog v-model="dialog" max-width="600px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="secondary"
              fab
              small
              dark
              class="ml-3"
              v-bind="attrs"
              v-on="on"
              @click="moveScrollToTop"
            >
              <v-icon> mdi-file-pdf </v-icon>
            </v-btn>
          </template>
          <ReportModal @kill-modal="dialog = false" />
        </v-dialog>
      </h3>
    </div>

    <div id="oracleStatus"><OracleStatus /></div>

    <div id="allSchemaStatics">
      <AllSchemaStastics style="margin: 40px 0px" />
    </div>
    <div id="allSchemaTopQuery" class="realtime-top-query-outer">
      <AllSchemaTopQuery class="top-query-left" />
    </div>
  </div>
</template>

<script>
import OracleStatus from "@/components/realtime-monitoring/oracle-status/OracleStatus.vue";
import AllSchemaTopQuery from "@/components/realtime-monitoring/query/AllSchemaTopQuery.vue";
import AllSchemaStastics from "@/components/realtime-monitoring/schema-status/AllSchemaStastics.vue";
import ReportModal from "@/components/main/ReportModal";

import { mapGetters, mapActions, mapMutations } from "vuex";
export default {
  name: "RealTimeMonitoring",
  data() {
    return {
      dialog: false,
      tab: 0,
      drawer: true,
      model: 0,
      mini: true,
      fixedMini: true,
      slowlyTab: true,
    };
  },
  components: {
    OracleStatus,
    AllSchemaTopQuery,

    AllSchemaStastics,
    ReportModal,
  },
  computed: {
    ...mapGetters(["selectedRealTime", "getRealTimeList", "getRealTime"]),
  },

  methods: {
    ...mapMutations(["SET_SELECTED_REALTIME"]),
    ...mapActions(["initRealTimeData"]),
    startRealTime() {
      this.SET_SELECTED_REALTIME(-1);
      this.initRealTimeData();
    },
    moveScroll(index) {
      var node = null;
      switch (index) {
        case 0:
          node = document.getElementById("oracleStatus");
          break;
        case 1:
          node = document.getElementById("allSchemaStatics");
          break;
        case 2:
          node = document.getElementById("allSchemaTopQuery");
          break;
      }
      var yourHeight = 48;
      node.scrollIntoView(true);

      var scrolledY = window.scrollY;

      if (scrolledY) {
        window.scroll(0, scrolledY - yourHeight);
      }
    },
    moveScrollToTop() {
      document
        .getElementById("allSchemaTopQueryTable")
        .firstElementChild.scrollTo({ top: 0, left: 0, behavior: "auto" });
    },
    getPositionScroll() {
      const top = document.getElementById("home-main").scrollTop;
      if (this.mini) {
        if (top >= 1220) this.tab = 2;
        else if (top >= 500) this.tab = 1;
        else this.tab = 0;
      }
    },
  },

  created() {
    this.initRealTimeData();
  },

  mounted() {
    document
      .getElementById("home-main")
      .addEventListener("scroll", this.getPositionScroll);
  },
  beforeDestory() {
    document
      .getElementById("home-main")
      .removeEventListener("scroll", this.getPositionScroll);
  },
};
</script>

<style>
.realtime-top-query-outer {
  margin-top: 30px;
}
/* .top-query-left {
  width: 70%;
}
.top-query-right {
  width: 28%;
  margin: auto 0px;
} */
.v-tap {
  background: transparent;
}

.realtime-container h1,
h4 {
  flex: 1 0 auto;
}
.realtime-container {
  padding-bottom: 50px;
}
.side-navbar-right {
  z-index: 10;
  background: transparent !important;
  border: 0px !important;
}
.side-navbar-right .v-navigation-drawer__border {
  background: transparent !important;
  border: 0px !important;
}
.home-main {
  scroll-behavior: smooth;
}
</style>
