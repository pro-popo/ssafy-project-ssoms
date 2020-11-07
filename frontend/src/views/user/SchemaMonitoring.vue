<template>
  <div>
    <div class="query-calendar">
      <b>
        <span>Schema Status</span> & <span>Top Query</span> |
        <span>{{ SelectedSchema }}</span>
      </b>
      <div>
        <span><input type="datetime-local" id="startDate"/></span>
        <span><input type="datetime-local" id="endDate"/></span>
        <button class="query-button" @click="queryData">조회</button>
      </div>
    </div>

    <div class="main-query-box">
      <SchemaWhole class="query-box1" />
      <SchemaDetail />
      <!-- <SchemaTopQuery /> -->
      <!-- <v-carousel
        hide-delimiters
        light
        show-arrows-on-hover
        style="height: 250px; width: 140vh"
        class="schema-carousel"
      >
        <v-sheet>
          <v-carousel-item><SchemaTopQuery /></v-carousel-item>
        </v-sheet>
        <v-sheet>
          <v-carousel-item><SchemaDetail /></v-carousel-item>
        </v-sheet>
      </v-carousel> -->
    </div>
  </div>
</template>

<script>
import SchemaWhole from "@/components/schema/SchemaWhole.vue";
// import SchemaTopQuery from "@/components/schema/SchemaTopQuery.vue";
import SchemaDetail from "@/components/schema/SchemaDetail.vue";

import { mapGetters } from "vuex";

export default {
  name: "QueryMonitoring",
  data() {
    return {
      change: this.SelectedSchema
    };
  },
  components: {
    SchemaWhole,
    // SchemaTopQuery,
    SchemaDetail
  },
  methods: {
    queryData() {
      const start = document.getElementById("startDate").value;
      const end = document.getElementById("endDate").value;
      console.log(start, end);
    }
  },
  computed: {
    ...mapGetters("Database", ["SelectedSchema"])
  },
  mounted() {
    document.getElementById(
      "startDate"
    ).value = new Date().toISOString().substring(0, 10);
    document.getElementById(
      "endDate"
    ).value = new Date().toISOString().substring(0, 10);
  },
  watch: {
    SelectedSchema: function() {
      console.log("변했다.");
    }
  }
};
</script>

<style>
/* .main-query-box {
  display: grid;
  grid-template-columns: 50% 10% 40%;
  grid-template-rows: 90px 20px 200px 180px;
} */
.query-box1 {
  margin-bottom: 10px;
}
.query-box2 {
  /* grid-column-start: 3;
  grid-column-end: 4;
  grid-row-start: 3;
  grid-row-end: 4; */
  width: 300px;
  height: 100px;
}
.schema-carousel {
  margin: auto;
  border-radius: 8px;
}
/*
.query-box3 {
  grid-column-start: 1;
  grid-column-end: 3;
  grid-row-start: 3;
  grid-row-end: 4;
}
.query-box4 {
  grid-column-start: 1;
  grid-column-end: 4;
  grid-row-start: 4;
  grid-row-end: 5;
} */

.query-calendar {
  display: flex;
  justify-content: space-between;
}
.query-button {
  border: 1px solid black;
}
</style>
