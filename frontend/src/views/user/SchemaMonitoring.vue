<template>
  <div>
    <div class="query-calendar">
      <b>
        <span>Schema Status</span> & <span>Top Query</span> |
        <span>{{ SelectedSchema }}</span>
      </b>
      <div>
        <span class="solid-black py-1 mx-1"><input type="date" id="startDate"/></span>
        <span class="solid-black py-1 mx-1"><input type="date" id="endDate"/></span>
        <button class="solid-black query-button px-1 mx-1" @click="queryData">조회</button>
      </div>
    </div>

    <div class="main-query-box">
      <SchemaWhole class="query-box1" />
      <!-- <SchemaDetail /> -->
      <SchemaTopQuery />
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
import SchemaTopQuery from "@/components/schema/SchemaTopQuery.vue";
// import SchemaDetail from "@/components/schema/SchemaDetail.vue";
import SERVER from "@/api/spring.js";
import { mapMutations, mapGetters } from "vuex";
import axios from "axios";

export default {
  name: "QueryMonitoring",
  data() {
    return {
      change: this.SelectedSchema
    };
  },
  components: {
    SchemaWhole,
    SchemaTopQuery,
    // SchemaDetail
  },
  methods: {
    queryData() {
      const start = '/'+document.getElementById("startDate").value;
      const end = '/'+document.getElementById("endDate").value;
      console.log(start)
      axios
        .get(SERVER.URL + SERVER.ROUTES.getPastData + start + end)
        .then((res) => {
          if (res.data.result === "success") {
            this.SET_TIME_AND_CPU_LIST(res.data.map.timeAndCpuList)
          }
        })
        .catch((err) => console.log(err));
    },
    ...mapMutations("Schema", ["SET_TIME_AND_CPU_LIST"])
  },
  computed: {
    ...mapGetters("Schema", ["SelectedSchema"])
  },
  mounted() {
    document.getElementById(
      "startDate"
    ).value = new Date().toISOString().substring(0, 10);
    document.getElementById(
      "endDate"
    ).value = new Date().toISOString().substring(0, 10);
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
.solid-black{
  border: 1px solid black;
  border-radius: 4px;
}
.query-button {
  padding-top: 1.5px;
  padding-bottom: 1.5px;
  border-radius: 2px;
}
</style>
