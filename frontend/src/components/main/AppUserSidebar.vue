<template>
  <v-list-item-group mandatory v-model="model">
    <v-list-item link>
      <v-list-item-icon>
        <v-icon> mdi-monitor-dashboard</v-icon>
      </v-list-item-icon>

      <v-list-item-content>
        <v-list-item-title>Real-Time Monitoring</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
    <v-list-item @click="menuShow = !menuShow">
      <v-list-item-icon>
        <v-icon> mdi-dns</v-icon>
      </v-list-item-icon>
      <v-list-item-content>
        <v-list-item-title>Schema</v-list-item-title>
      </v-list-item-content>
      <v-list-item-icon>
        <v-icon v-show="menuShow" class="float-right"> mdi-menu-up </v-icon>
        <v-icon v-show="!menuShow" class="float-right"> mdi-menu-down </v-icon>
      </v-list-item-icon>
    </v-list-item>

    <div v-show="menuShow">
      <v-list-item
        v-for="(schema, index) in getSchemaList"
        :key="index"
        @click="toSchemaList(schema.userID)"
        link
      >
        <v-list-item-icon>
          <v-icon> mdi-database</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ schema.userID }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </div>
    <v-divider style="margin: 10px 0px"></v-divider>

    <v-dialog v-model="dialog" max-width="600px">
      <template v-slot:activator="{ on, attrs }">
        <v-list-item link v-bind="attrs" v-on="on">
          <v-list-item-icon>
            <v-icon>mdi-file-chart</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>Report</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </template>
      <ReportModal @kill-modal="dialog = false" />
    </v-dialog>
  </v-list-item-group>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from "vuex";
import ReportModal from "./ReportModal";
export default {
  name: "AppUserSidebar",
  components: {
    ReportModal,
  },
  data() {
    return {
      dialog: false,
      model: 0,
      menuShow: true,
      page: ["RealTimeMonitoring", "SchemaMonitoring", "SchemaMonitoring"],
    };
  },
  watch: {
    model: function() {
      if (this.model == 0) {
        this.$router.push({ name: "RealTimeMonitoring"});
      }
      // this.$router.push({ name: "RealTimeMonitoring" });
    }
  },
  created() {
    this.getSettingSchema();
  },
  computed: {
    ...mapGetters("Schema", ["getSchemaList"]),
  },
  methods: {
    asdf() {
      alert("asdf");
    },
    toSchemaList(name) {
        if (this.$route.name !== "SchemaMonitoring")
          this.$router.push({ name: "SchemaMonitoring" });
        this.SET_SELECTED_SCHEMA(name);
      
      // report
    },
    ...mapActions("Schema", ["getSettingSchema"]),
    ...mapMutations("Schema", ["SET_SELECTED_SCHEMA"]),
  },
};
</script>

<style>
.schema-list {
  padding: 8px 16px;
  display: flex;
  align-items: center;
}
.schema-title {
  margin-left: 32px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.schema-list span {
  font-size: 14px;
  font-weight: 500;
}
</style>
