<template>
  <div>
    <h2 class="mb-3 mt-3">
      All Schema'<span style="margin-left:3px" />s CPU Top Query
    </h2>
    <v-simple-table
      fixed-header
      height="285px"
      class="elevation-2 realtime-top-query"
      id="allSchemaTopQueryTable"
    >
      <template v-slot:default>
        <thead style="border-radiu">
          <tr>
            <th class="text-center topquery-table-th">
              Rank
            </th>
            <th class="text-left topquery-table-th">
              SQL ID
            </th>
            <th class="text-left topquery-table-th">
              SQL
            </th>
            <th class="text-left topquery-table-th">
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
            <td class="text-center" style="width:70px">{{ index + 1 }}</td>
            <td>{{ query.sqlId }}</td>
            <td align="left" class="query-table-sql">
              {{ query.sql }}
            </td>
            <td>{{ query.parsingSchemaName }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <AllSchemaTopQueryDetail class="top-query-right" />
  </div>
</template>

<script>
import { mapGetters, mapState, mapMutations } from "vuex";
import AllSchemaTopQueryDetail from "@/components/realtime-monitoring/query/AllSchemaTopQueryDetail.vue";

export default {
  name: "AllSchemaTopQuery",
  components: {
    AllSchemaTopQueryDetail
  },
  data() {
    return {
      clickRow: -1
    };
  },

  methods: {
    getQueryDetail(selectedRealTime, index) {
      const trList = document
        .getElementById("allSchemaTopQueryTable")
        .getElementsByTagName("tr");
      if (this.clickRow != -1)
        trList[this.clickRow + 1].classList.remove("real-query-click-tr");

      trList[index + 1].classList.add("real-query-click-tr");
      this.clickRow = index;

      this.SET_TOPQUERY_DETAIL([selectedRealTime, index]);
    },
    ...mapMutations("TopQuery", [
      "SET_TOPQUERY_DETAIL",
      "SET_TOPQUERY_DETAIL_EMPTY"
    ])
  },
  computed: {
    ...mapGetters("TopQuery", ["getTopQueryList"]),
    ...mapState("TopQuery", ["watch"]),
    ...mapGetters(["selectedRealTime"])
  },
  watch: {
    getTopQueryList: function() {
      this.getQueryDetail(this.selectedRealTime, this.clickRow);
    },
    selectedRealTime: function() {
      const trList = document
        .getElementById("allSchemaTopQueryTable")
        .getElementsByTagName("tr");
      if (this.clickRow != -1)
        trList[this.clickRow + 1].classList.remove("real-query-click-tr");
      this.SET_TOPQUERY_DETAIL_EMPTY();
    }
  }
};
</script>

<style>
.real-query-click-tr {
  background: rgb(214, 214, 214);
}
.realtime-top-query {
  width: 100%;
}
.real-query-hover:hover {
  cursor: pointer;
}

.topquery-table-th {
  color: white !important;
  background: #414141 !important;
}
.topquery-table-th:nth-child(1) {
  border-top-left-radius: 5px !important;
}
.query-table-sql {
  max-width: 600px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
