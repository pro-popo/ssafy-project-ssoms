<template>
  <div>
    <v-simple-table
      fixed-header
      height="300px"
      class="elevation-2 realtime-top-query"
      id="allSchemaTopQueryTable"
    >
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">
              Rank
            </th>
            <th class="text-center">
              SQL ID
            </th>
            <th class="text-center">
              SQL
            </th>
            <th class="text-center">
              Schema Name
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(query, index) in getTopQueryList[selectedRealTime]"
            :key="index"
            @click="getQueryDetail(selectedRealTime, index)"
            class="real-query-hover"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ query.sqlId }}</td>
            <td align="left">{{ query.sql }}</td>
            <td>{{ query.parsingSchemaName }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import { mapGetters, mapState, mapMutations } from "vuex";

export default {
  name: "AllSchemaTopQuery",
  data() {
    return {};
  },
  methods: {
    getQueryDetail(selectedRealTime, index) {
      this.SET_TOPQUERY_DETAIL([selectedRealTime, index]);
    },
    ...mapMutations("TopQuery", ["SET_TOPQUERY_DETAIL"])
  },
  computed: {
    ...mapGetters("TopQuery", ["getTopQueryList"]),
    ...mapState("TopQuery", ["watch"]),
    ...mapGetters(["selectedRealTime"])
  },
  watch: {}
};
</script>

<style>
.realtime-top-query {
  display: inline-block;
  width: 100%;
}
.real-query-hover:hover {
  cursor: pointer;
}
</style>
