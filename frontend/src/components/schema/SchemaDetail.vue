<template>
  <v-card class="px-4 py-4" elevation="2" outlined>
      <table width="100%">
          <tr>
              <td>
                  <span>name
                    <div class="schema-detail-item">{{ SelectedSchema }}</div>
                    </span>
              </td>
              <td>
                    <span>cpuTimeAvg
                        <div class="schema-detail-item">{{ getPastTimeData.allSchemaStastics[SelectedSchema].cpuTimeAvg }}</div>
                    </span>
              </td>
              <td>
                <span
                        >cpuTimeMax
                        <div class="schema-detail-item">
                        {{ getPastTimeData.allSchemaStastics[SelectedSchema].cpuTimeMax }}
                        </div>
                    </span>
              </td>
              <td>
                  <span
                    >cpuTimeTot
                    <div class="schema-detail-item">
                    {{ getPastTimeData.allSchemaStastics[SelectedSchema].cpuTimeTot }}
                    </div>
                </span>
              </td>
          </tr>
          
          <tr>
              <td>
                  <span
        >diskReadsAvg
        <div class="schema-detail-item">
          {{ getPastTimeData.allSchemaStastics[SelectedSchema].diskReadsAvg }}
        </div>
      </span>
              </td>
              <td>
                  <span
        >elapsedTimeAvg
        <div class="schema-detail-item">
          {{ getPastTimeData.allSchemaStastics[SelectedSchema].elapsedTimeAvg }}
        </div>
      </span>
              </td>
              <td>
                  <span
        >elapsedTimeMax
        <div class="schema-detail-item">
          {{ getPastTimeData.allSchemaStastics[SelectedSchema].elapsedTimeMax }}
        </div>
      </span>
              </td>
              <td>
                  <span
        >elapsedTimeTot
        <div class="schema-detail-item">
          {{ getPastTimeData.allSchemaStastics[SelectedSchema].elapsedTimeTot }}
        </div>
      </span>
              </td>
          </tr>

          <tr>
              <td>
                <span>
                executions
                <div class="schema-detail-item">{{ getPastTimeData.allSchemaStastics[SelectedSchema].executions }}</div>
                </span>
      
              </td>
              <td>
<span
        >rowsProcessedAvg
        <div class="schema-detail-item">{{ getPastTimeData.allSchemaStastics[SelectedSchema].rowsProcessedAvg }}</div>
      </span>
      
              </td>
              <td>
<span
        >sqlCnt
        <div class="schema-detail-item">
          {{ getPastTimeData.allSchemaStastics[SelectedSchema].sqlCnt }}
        </div>
      </span>
      
              </td>
              <td>
<span
        >bufferGetsAvg
        <div class="schema-detail-item">
          {{ getPastTimeData.allSchemaStastics[SelectedSchema].bufferGetsAvg }}
        </div>
      </span>
              </td>
          </tr>
      </table>
  </v-card>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "SchemaDetail",
  components: {},
  computed: {
    ...mapGetters("Schema", ["getPastTimeData"]),
    ...mapGetters("Schema", ["SelectedSchema"])
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
