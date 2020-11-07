<template>
  <v-list-item-group mandatory>
    <v-list-item link>
      <v-list-item-icon>
        <v-icon> mdi-dns</v-icon>
      </v-list-item-icon>

      <v-list-item-content>
        <v-list-item-title @click="toSchemaList(1, 'Schema')"
          >Schema</v-list-item-title
        >
      </v-list-item-content>
    </v-list-item>
    <v-list-item
      v-for="(schema, index) in getSchemaList"
      :key="index"
      link
      @click="toSchemaList(2, schema.userID)"
    >
      <v-list-item-icon>
        <v-icon> mdi-database</v-icon>
      </v-list-item-icon>

      <v-list-item-content>
        <v-list-item-title>{{ schema.userID }}</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
    <v-divider style="margin:10px 0px;"></v-divider>
    <v-list-item link>
      <v-list-item-icon>
        <v-icon>mdi-file-chart</v-icon>
      </v-list-item-icon>

      <v-list-item-content>
        <v-list-item-title>Report</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
  </v-list-item-group>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from "vuex";
export default {
  name: "AppUserSidebar",
  data() {
    return {};
  },
  created() {
    this.getSettingSchema();
  },
  computed: {
    ...mapGetters("Database", ["getSchemaList"])
  },
  methods: {
    toSchemaList(num, name) {
      if (num === 1) {
        if (this.$route.name !== "MainHome") {
          this.$router.push({ name: "MainHome" });
        }
      } else if (num === 2) {
        if (this.$route.name === "QueryMonitoring") {
          this.SET_SELECTED_SCHEMA(name);
        } else {
          this.$router.push({ name: "QueryMonitoring" });
          this.SET_SELECTED_SCHEMA(name);
        }
      }
    },
    ...mapActions("Database", ["getSettingSchema"]),
    ...mapMutations("Database", ["SET_SELECTED_SCHEMA"])
  }
};
</script>

<style></style>
