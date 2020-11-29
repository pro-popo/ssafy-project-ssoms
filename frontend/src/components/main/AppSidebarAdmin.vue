<template>
  <div>
    <v-list-item-group v-model="model" mandatory>
      <v-list-item link>
        <v-list-item-icon>
          <v-icon> mdi-cog</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>Setting</v-list-item-title>
        </v-list-item-content>
        <v-btn small icon v-if="menuShow" @click="menuShow = false">
          <v-icon> mdi-menu-down</v-icon>
        </v-btn>
        <v-btn small icon v-if="!menuShow" @click="menuShow = true">
          <v-icon> mdi-menu-up</v-icon>
        </v-btn>
      </v-list-item>

      <div v-show="menuShow">
        <v-list-item link>
          <v-list-item-icon>
            <v-icon> mdi-dns</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>OracleDB</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item link>
          <v-list-item-icon>
            <v-icon> mdi-database</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Schema</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </div>
      <v-divider style="margin: 10px 0px"></v-divider>
      <v-list-item link>
        <v-list-item-icon>
          <v-icon>mdi-account-group</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>Members</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list-item-group>

    <v-divider style="margin: 10px 0px"></v-divider>

    <v-list-item link @click="RealTimeShow = !RealTimeShow">
      <v-list-item-icon>
        <v-icon> mdi-monitor-dashboard</v-icon>
      </v-list-item-icon>

      <v-list-item-content>
        <v-list-item-title>Real-Time</v-list-item-title>
      </v-list-item-content>
      <v-btn small icon v-if="RealTimeShow && !mini">
        <v-icon> mdi-menu-down</v-icon>
      </v-btn>
      <v-btn small icon v-if="!RealTimeShow && !mini">
        <v-icon> mdi-menu-up</v-icon>
      </v-btn>
    </v-list-item>

    <v-list-item v-if="RealTimeShow && !mini">
      <v-list-item-content>
        <div>
          <RealTime />
        </div>
      </v-list-item-content>
    </v-list-item>
  </div>
</template>

<script>
import RealTime from "@/views/admin/RealTime.vue";
export default {
  name: "AppSidebarAdmin",
  props: {
    mini: Boolean,
  },
  data() {
    return {
      model: 0,
      menuShow: true,
      RealTimeShow: false,
      page: ["Setting", "OracleDB", "Schema", "Member"],
    };
  },
  components: {
    RealTime,
  },

  watch: {
    model: function () {
      this.$router.push({ name: this.page[this.model] });
    },
  },
};
</script>

<style></style>
