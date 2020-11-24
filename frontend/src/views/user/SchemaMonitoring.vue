<template>
  <div id="pastMonitering" class="schema-monitoring-container">
    <div
      style="
        display: flex;
        height: 60px;
        width: 350px;
        margin-right: 10px;
        margin-left: auto;
      "
    >
      <v-menu
        v-model="menu2"
        :close-on-content-click="false"
        :nudge-right="40"
        transition="scale-transition"
        offset-y
        min-width="250px;"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            width="250px;"
            v-model="dateRangeText"
            prepend-inner-icon="mdi-calendar"
            readonly
            solo
            v-bind="attrs"
            v-on="on"
            style="margin-right: 10px; border-radius: 30px"
          >
          </v-text-field>
        </template>
        <v-date-picker
          v-model="dates"
          no-title
          range
          scrollable
          :max="tomorrow"
        >
        </v-date-picker>
      </v-menu>
      <v-tooltip top>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            fab
            small
            dark
            elevation="2"
            style="margin-top: 5px"
            @click="queryData"
            v-bind="attrs"
            v-on="on"
            ><v-icon>mdi-magnify</v-icon></v-btn
          >
        </template>
        <span>Data Search</span>
      </v-tooltip>
    </div>
    <!-- <v-container fluid>
      <div
        class="text-right"
        style="width:100%"
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
    -->
    <div v-if="!getTimeAndCpuList.check">
      <Loading class="mt-10" />
    </div>

    <div
      v-else-if="getTimeAndCpuList.isEmpty"
      class="animate__animated animate__headShake v-spinner ma-10;"
      align="center"
    >
      <NotExistData />
    </div>

    <v-container
      v-if="
        getTimeAndCpuList.check &&
        !getTimeAndCpuList.isEmpty &&
        getTimeAndCpuList.cpu.length != 0
      "
      class="animate__animated animate__fadeIn"
      fluid
    >
      <SchemaWhole id="SchemaWhole" class="mb-2" />
      <SchemaDetail id="SchemaDetail" class="mb-2" />
      <SchemaTopQuery id="SchemaQuerys" />
    </v-container>
  </div>
</template>

<script>
import SchemaWhole from "@/components/schema/SchemaWhole.vue";
import SchemaTopQuery from "@/components/schema/SchemaTopQuery.vue";
import SchemaDetail from "@/components/schema/SchemaDetail.vue";
import Loading from "@/components/schema/Loading.vue";
import NotExistData from "@/components/schema/NotExistData.vue";
//import SERVER from "@/api/spring.js";
import { mapActions, mapMutations, mapGetters } from "vuex";
//import axios from "axios";

export default {
  name: "QueryMonitoring",
  data() {
    return {
      tomorrow: new Date(),

      dates: [
        new Date().toISOString().substr(0, 10),
        new Date().toISOString().substr(0, 10),
      ],
      menu: false,
      modal: false,
      menu2: false,
    };
  },
  components: {
    SchemaWhole,
    SchemaTopQuery,
    SchemaDetail,
    Loading,
    NotExistData,
  },
  watch: {
    dates: function () {
      if (this.dates.length == 2) {
        let temp = this.dates[0];
        if (this.dates[0] > this.dates[1]) {
          this.dates[0] = this.dates[1];
          this.dates[1] = temp;
        }
        this.menu2 = false;
      }
    },
  },
  methods: {
    queryData() {
      this.getTimeAndCpuList.ani_flag = true;
      this.SET_TIME_AND_CPU_LIST_CHECK(false);

      const start = "/" + this.dates[0];
      if (this.dates.length != 2) this.dates[1] = this.dates[0];
      const end = "/" + this.dates[1];
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
      "SET_TIME_AND_CPU_LIST_CHECK",
      "SET_PAST_TIME_DATA_CHECK",
    ]),
    ...mapActions("Schema", ["setTimeAndCpuList"]),
  },
  computed: {
    ...mapGetters("Schema", ["SelectedSchema"]),
    ...mapGetters("Schema", ["getTimeAndCpuList"]),
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
  },
  mounted() {
    // document.getElementById(
    //   "startDate"
    // ).value = new Date().toISOString().substring(0, 10);
    // document.getElementById(
    //   "endDate"
    // ).value = new Date().toISOString().substring(0, 10);
    this.queryData();

    this.tomorrow.setDate(new Date().getDate());
    this.tomorrow = this.tomorrow.toISOString().substring(0, 10);
  },
};
</script>

<style>
.data-notexist {
  width: 100%;
  height: 500px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
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
  width: 100%;
  padding-bottom: 50px;
}
</style>
