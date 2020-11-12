<template>
  <div width="600px">
    <v-simple-table
      fixed-header
      height="300px"
      width="600px"
      class="elevation-2"
    >
      <template v-slot:default width="600px">
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
            v-for="(query, index) in getPastTimeData.schemas[SelectedSchema].cpuUsed"
            :key="index"
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
import { mapGetters } from "vuex";

export default {
  name: "SchemaTopQuery",
  methods: {

  },
  computed: {
    ...mapGetters("Schema", ["getPastTimeData"]),
    ...mapGetters("Schema", ["SelectedSchema"])
  }
};
</script>

<style>
</style>
