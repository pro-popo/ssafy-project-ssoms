<template>
  <v-card class="schema-detail" elevation="15" outlined>
    <div class="schema-detail-items1">
      <span>
        sql Cnt
        <div class="schema-detail-item">{{ getSchemaData.sqlCnt[0] }}</div>
      </span>
      <span
        >excutions
        <div class="schema-detail-item">{{ getSchemaData.executions[0] }}</div>
      </span>
      <span
        >rowProcessedAvg
        <div class="schema-detail-item">
          {{ getSchemaData.rowsProcessedAvg[0] }}
        </div>
      </span>
    </div>
    <div class="schema-detail-items2">
      <span
        >cpuTimeMax
        <div class="schema-detail-item">
          {{ getSchemaData.cpuTimeMax[0] }}
        </div>
      </span>
      <span
        >elapsedTimeMax
        <div class="schema-detail-item">
          {{ getSchemaData.elapsedTimeMax[0] }}
        </div>
      </span>
    </div>
  </v-card>

  <!-- {{ getSchemaData }} -->
  <!-- </div> -->
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "SchemaDetail",
  components: {},
  computed: {
    ...mapGetters("Database", ["getSchemaData"])
  },
  data() {
    return {};
  }
};
</script>

<style>
.schema-detail {
  padding: 20px;
  width: 500px;
  float: left;
}
.schema-detail-items1 {
  display: flex;
  justify-content: space-between;
}
.schema-detail-items2 {
  display: flex;
  justify-content: space-around;
}
.schema-detail-item {
  font-size: 30px;
  font-weight: 900;
}
</style>
