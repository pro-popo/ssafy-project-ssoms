<template>
  <v-row>
    <v-col cols="12">
      <v-card-subtitle>
        <h2 style="color: var(--font-main-color); margin-top: 10px">
          {{ SelectedSchema }}'<span style="margin-left: 3px" />s Status
        </h2>
      </v-card-subtitle>
      <v-card width="100%" elevation="2" outlined>
        <v-card-text>
          <table
            width="100%"
            v-if="getPastTimeData.allSchemaStastics.length != 0"
            id="monitoring-schema-table"
          >
            <tr>
              <td>
                <div>
                  <h4 class="query-detail-title">cpuTimeAvg</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .cpuTimeAvg
                    }}
                    <span>sec</span>
                  </span>
                </div>
              </td>
              <td>
                <div>
                  <h4 class="query-detail-title">cpuTimeMax</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .cpuTimeMax
                    }}
                    <span>sec</span>
                  </span>
                </div>
              </td>
              <td>
                <div>
                  <h4 class="query-detail-title">cpuTimeTot</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .cpuTimeTot
                    }}
                    <span>sec</span>
                  </span>
                </div>
              </td>
              <td>
                <div>
                  <h4 class="query-detail-title">bufferGetsAvg</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .bufferGetsAvg
                    }}
                    <span>count</span>
                  </span>
                </div>
              </td>
            </tr>

            <tr>
              <td>
                <div>
                  <h4 class="query-detail-title">diskReadsAvg</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .diskReadsAvg
                    }}
                    <span>count</span>
                  </span>
                </div>
              </td>
              <td>
                <div>
                  <h4 class="query-detail-title">elapsedTimeAvg</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .elapsedTimeAvg
                    }}
                    <span>sec</span>
                  </span>
                </div>
              </td>
              <td>
                <div>
                  <h4 class="query-detail-title">elapsedTimeMax</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .elapsedTimeMax
                    }}
                    <span>sec</span>
                  </span>
                </div>
              </td>
              <td>
                <div>
                  <h4 class="query-detail-title">elapsedTimeTot</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .elapsedTimeTot
                    }}
                    <span>sec</span>
                  </span>
                </div>
              </td>
            </tr>

            <tr>
              <td>
                <div>
                  <h4 class="query-detail-title">executions</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .executions
                    }}
                    <span>count</span>
                  </span>
                </div>
              </td>
              <td>
                <div>
                  <h4 class="query-detail-title">rowsProcessedAvg</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema]
                        .rowsProcessedAvg
                    }}
                    <span>count</span>
                  </span>
                </div>
              </td>
              <td>
                <div>
                  <h4 class="query-detail-title">sqlCnt</h4>
                  <span class="query-detail-text">
                    {{
                      getPastTimeData.allSchemaStastics[SelectedSchema].sqlCnt
                    }}
                    <span>count</span>
                  </span>
                </div>
              </td>
            </tr>
          </table>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "SchemaDetail",
  components: {},
  computed: {
    ...mapGetters("Schema", ["getPastTimeData"]),
    ...mapGetters("Schema", ["SelectedSchema"]),
  },
  data() {
    return {};
  },
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

#monitoring-schema-table td {
  padding: 10px;
}
</style>
