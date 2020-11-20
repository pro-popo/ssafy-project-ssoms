<template>
  <div id="pastMonitering" class="schema-monitoring-container">
    <v-container fluid>
      <b>
        <span>Schema Status</span> & <span>Top Query</span> |
        <span>{{ SelectedSchema }}</span>
      </b>

      <div
        class="text-center"
        v-bind:class="{ float_right: getTimeAndCpuList.ani_flag }"
      >
        <input
          class="solid-black py-1 px-1 mx-2 mb-3 ani_sm_f"
          v-bind:class="{ ani_sm: !getTimeAndCpuList.ani_flag }"
          type="date"
          id="startDate"
        />
        <input
          class="solid-black py-1 px-1 mx-2 mb-3 ani_sm_f"
          v-bind:class="{ ani_sm: !getTimeAndCpuList.ani_flag }"
          type="date"
          id="endDate"
        />
        <button
          class="solid-black py-1 px-1 mx-auto ani_sm_f"
          v-bind:class="{ ani_sm_btn: !getTimeAndCpuList.ani_flag }"
          @click="queryData"
        >
          조회
        </button>
      </div>
    </v-container>
    <v-container fluid v-if="getTimeAndCpuList.check">
      <SchemaWhole id="SchemaWhole" class="mb-2" />
      <SchemaDetail id="SchemaDetail" class="mb-2" />
      <SchemaTopQuery />
    </v-container>
    <div
      v-if="getTimeAndCpuList.ani_flag && !getTimeAndCpuList.check && loading"
    >
      <Loading class="mt-10" />
    </div>
    <!--<div class="text-center mt-10" v-else>
        <input class="fs2rem solid-black py-1 mx-1 mb-10" type="date" id="startDate"/>
        <input class="fs2rem solid-black py-1 mx-1 mb-10" type="date" id="endDate"/>
        <button class="fs1_5rem d-block mx-auto solid-black py-1 px-1 mx-1" @click="queryData">조회</button>
    </div>-->
  </div>
</template>

<script>
import SchemaWhole from "@/components/schema/SchemaWhole.vue";
import SchemaTopQuery from "@/components/schema/SchemaTopQuery.vue";
import SchemaDetail from "@/components/schema/SchemaDetail.vue";
import Loading from "@/components/schema/Loading.vue";
//import SERVER from "@/api/spring.js";
import { mapActions, mapMutations, mapGetters } from "vuex";
//import axios from "axios";

export default {
  name: "QueryMonitoring",
  data() {
    return {
      loading: false
    };
  },
  components: {
    SchemaWhole,
    SchemaTopQuery,
    SchemaDetail,
    Loading
  },
  methods: {
    queryData() {
      this.getTimeAndCpuList.ani_flag = true;
      setTimeout(() => {
        this.loading = true;
      }, 500);
      const start = "/" + document.getElementById("startDate").value;
      const end = "/" + document.getElementById("endDate").value;
      this.setTimeAndCpuList({ start: start, end: end });

      // init top query style
      this.SET_PAST_TIME_DATA_CHECK();

      //  axios
      //    .get(SERVER.URL + SERVER.ROUTES.getPastData + start + end)
      //    .then((res) => {
      //        if(res.data.result === "empty"){
      //            alert("data not exist");
      //        }
      //      else if (res.data.result === "success") {
      //        this.SET_TIME_AND_CPU_LIST(res.data.map.timeAndCpuList)
      //      }
      //    })
      //    .catch((err) => console.log(err));
    },
    ...mapMutations("Schema", [
      "SET_TIME_AND_CPU_LIST",
      "SET_PAST_TIME_DATA_CHECK"
    ]),
    ...mapActions("Schema", ["setTimeAndCpuList"])
  },
  computed: {
    ...mapGetters("Schema", ["SelectedSchema"]),
    ...mapGetters("Schema", ["getTimeAndCpuList"])
  },
  mounted() {
    document.getElementById(
      "startDate"
    ).value = new Date().toISOString().substring(0, 10);
    document.getElementById(
      "endDate"
    ).value = new Date().toISOString().substring(0, 10);
    this.queryData();
  }
};
</script>

<style>
.fs2rem {
  font-size: 2rem;
}
.fs1_5rem {
  font-size: 1.5rem;
}
.mb_5 {
  margin-bottom: 5px;
}
.float_right {
  transform: translateY(-7px);
  display: block;
  float: right;
}
.ani_sm {
  font-size: 2rem;
  margin-bottom: 5px;
  margin-top: 100px;
}
.ani_sm_btn {
  font-size: 1.5rem;
  display: block;
  margin: auto;
}
.ani_sm_f {
  transition-property: margin margin-bottom margin-top float font-size display;
  transition-duration: 0.5s;
  transition-timing-function: ease-in-out;
}
/* .main-query-box {
  display: grid;
  grid-template-columns: 50% 10% 40%;
  grid-template-rows: 90px 20px 200px 180px;
} */

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
.solid-black {
  border: 1px solid black;
  border-radius: 4px;
}
.query-button {
  padding-top: 1.5px;
  padding-bottom: 1.5px;
  border-radius: 2px;
}

.schema-monitoring-container {
  margin-bottom: 50px;
}
</style>
