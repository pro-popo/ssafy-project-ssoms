<template>
  <div class="realtime-container">
    <div
      style="display:flex; justify-content: space-between; align-items:flex-start; margin-bottom:20px"
    >
      <h3>
        Real-Time Monitorting
      </h3>

      <div>
        <span style="font-size:14px">Last Update </span>
        <span style="font-size:14px"
          ><v-icon size="16">mdi-clock-time-four-outline</v-icon>
          {{ getRealTime }}</span
        >
      </div>
    </div>
    <div>
      <template>
        <v-tabs
          vertical
          background-color="transparent"
          color="var(--font-sub2-color)"
        >
          <v-tab @click="moveScroll(0)">Oracle DB</v-tab>
          <v-tab @click="moveScroll(1)">All Schema</v-tab>
          <v-tab @click="moveScroll(2)">Top Query</v-tab>
        </v-tabs>
      </template>
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
        <ReportModal :Type="1" @killModal="dialog = false" />
      </v-dialog>
    </div>
    <br />
    <div id="oracleStatus"><OracleStatus /></div>

    <div id="allSchemaStatics">
      <AllSchemaStastics style="margin:40px 0px;" />
    </div>
    <div id="allSchemaTopQuery">
      <h2 class="mb-3 mt-3">Top Query</h2>
      <div class="realtime-top-query-outer">
        <AllSchemaTopQuery class="top-query-left" />
        <AllSchemaTopQueryDetail class="top-query-right" />
      </div>
    </div>
  </div>
</template>

<script>
import OracleStatus from "@/components/realtime-monitoring/oracle-status/OracleStatus.vue";
import AllSchemaTopQuery from "@/components/realtime-monitoring/query/AllSchemaTopQuery.vue";
import AllSchemaTopQueryDetail from "@/components/realtime-monitoring/query/AllSchemaTopQueryDetail.vue";
import AllSchemaStastics from "@/components/realtime-monitoring/schema-status/AllSchemaStastics.vue";
import ReportModal from "@/components/main/ReportModal";

import { mapGetters, mapActions } from "vuex";
export default {
  name: "RealTimeMonitoring",
  data() {
    return {
      dialog: false
    };
  },
  components: {
    OracleStatus,
    AllSchemaTopQuery,
    AllSchemaTopQueryDetail,
    AllSchemaStastics,
    ReportModal
  },
  computed: {
    ...mapGetters(["getRealTime"])
  },
  methods: {
    ...mapActions(["initRealTimeData"]),
    moveScroll(index) {
      switch (index) {
        case 0:
          document
            .getElementById("oracleStatus")
            .scrollIntoView({ behavior: "smooth" });
          break;
        case 1:
          document
            .getElementById("allSchemaStatics")
            .scrollIntoView({ behavior: "smooth" });
          break;
        case 2:
          document
            .getElementById("allSchemaTopQuery")
            .scrollIntoView({ behavior: "smooth" });
          break;
      }
    },
    moveScrollToTop(){
        document.getElementById("allSchemaTopQueryTable").firstElementChild.scrollTo({top:0, left:0, behavior:'auto'});
    }
  },
  created() {
    this.initRealTimeData();
  }
};
</script>

<style>
.realtime-top-query-outer {
  display: flex;
  justify-content: space-between;
}
.top-query-left {
  width: 65%;
}
.top-query-right {
  width: 30%;
  margin: auto 0px;
}
.v-tap {
  background: transparent;
}

.realtime-container h1,
h4 {
  flex: 1 0 auto;
}
.realtime-container {
  margin-bottom: 50px;
}
</style>
