<template>
  <div>
    <div align="right">
      Last Update Time : <b>{{ getRealTime }}</b>

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
          >
            <v-icon> mdi-file-pdf </v-icon>
          </v-btn>
        </template>
        <ReportModal :Type="1" @killModal="dialog = false" />
      </v-dialog>
    </div>
    <OracleStatus />
    <AllSchemaStastics />
    <v-divider></v-divider>
    <h2 class="mb-2 mt-2">Top Query</h2>
    <div class="realtime-top-query-outer">
      <AllSchemaTopQuery class="top-query-left" />
      <AllSchemaTopQueryDetail class="top-query-right" />
    </div>
  </div>
</template>

<script>
import OracleStatus from "@/components/realtime-monitoring/oracle-status/OracleStatus.vue";
import AllSchemaTopQuery from "@/components/realtime-monitoring/query/AllSchemaTopQuery.vue";
import AllSchemaTopQueryDetail from "@/components/realtime-monitoring/query/AllSchemaTopQueryDetail.vue";
import AllSchemaStastics from "@/components/realtime-monitoring/schema-status/AllSchemaStastics.vue";
import ReportModal from "@/components/main/ReportModal";

import { mapGetters } from "vuex";
export default {
  name: "RealTimeMonitoring",
  data() {
    return {
      dialog: false
    }
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
</style>
