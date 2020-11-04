<template>
  <v-list-item-group v-model="model" mandatory>
    <v-list-item link>
      <v-list-item-icon>
        <v-icon> mdi-dns</v-icon>
      </v-list-item-icon>

      <v-list-item-content>
        <v-list-item-title>Schema</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
    <div class="sidebar-schema">
      <v-list-item v-for="(schema, index) in getSchemaList" :key="index" link>
        <v-list-item-icon>
          <v-icon> mdi-database</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ schema.userID }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </div>
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

  <!-- <router-link :to="{ name: 'MainHome' }">Home</router-link>
      <router-link :to="{ name: 'PCResource' }">PC자원</router-link>
      <router-link :to="{ name: 'Traffic' }">트래픽</router-link>
      <router-link :to="{ name: 'QueryMonitoring' }"
        >Query 모니터링</router-link
      >
      <router-link :to="{ name: 'RealMonitoring' }"
        >실시간 모니터링</router-link
      > -->
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  name: "AppUserSidebar",
  data() {
    return {
      model: 0
    };
  },
  created() {
    this.getSettingSchema();
  },
  computed: {
    ...mapGetters("Database", ["getSchemaList"])
  },
  methods: {
    ...mapActions("Database", ["getSettingSchema"])
  }
};
</script>

<style></style>
