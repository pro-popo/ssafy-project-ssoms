<template>
  <div class="realtime-top-box">
    <v-simple-table
      fixed-header
      height="300px"
      class="elevation-2 realtime-top-query"
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
            v-for="(query, index) in getTopQueryList"
            :key="query.sqlId"
            @click="getQueryDetail(index)"
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
    getQueryDetail(index) {
      this.SET_TOPQUERY_DETAIL(index);
    },
    ...mapMutations("TopQuery", ["SET_TOPQUERY_DETAIL"])
  },
  computed: {
    ...mapGetters("TopQuery", ["getTopQueryList"]),
    ...mapState("TopQuery", ["watch"])
  },
  watch: {}
};
</script>

<style>
.realtime-top-box {
  text-align: center;
  height: 30%;
}
.realtime-top-query {
  margin-bottom: 30px;
  display: inline-block;
  width: 75%;
}
.real-query-hover:hover {
  cursor: pointer;
}
</style>
