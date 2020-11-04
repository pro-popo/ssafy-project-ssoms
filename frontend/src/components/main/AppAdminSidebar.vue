<template>
  <v-list-item-group v-model="model" mandatory>
    <v-subheader>Setting</v-subheader>
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

    <v-divider style="margin:10px 0px;"></v-divider>
    <v-list-item link>
      <v-list-item-icon>
        <v-icon>mdi-account-group</v-icon>
      </v-list-item-icon>

      <v-list-item-content>
        <v-list-item-title>Members</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
  </v-list-item-group>

  <!-- 
        <div>
      <div class="admin-setting">Setting</div>
    <div>
      <router-link :to="{ name: 'OracleDB' }">Oracle DB</router-link>
      <router-link :to="{ name: 'Schema' }">Schema</router-link>
      <router-link :to="{ name: 'Member' }">Member</router-link>
    </div>
  </div> -->
</template>

<script>
export default {
  name: "AppAdminSidebar",
  data() {
    return {
      model: 0,
      page: ["OracleDB", "Schema", "Member"]
    };
  },
  methods: {
    movePage() {}
  },
  watch: {
    model: function() {
      this.$router.push({ name: this.page[this.model] });
    }
  }
};
</script>

<style></style>
