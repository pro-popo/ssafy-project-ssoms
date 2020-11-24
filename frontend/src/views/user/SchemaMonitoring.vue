<template>
  <div id="pastMonitering" class="schema-monitoring-container">
    <v-navigation-drawer
      v-if="getTimeAndCpuList.check"
      class="side-navbar-right"
      :expand-on-hover="fixedMini"
      :mini-variant.sync="mini"
      :width="260"
      permanent
      absolute
      right
      style="height: 100%; margin-top: 48px"
    >
      <div
        v-if="mini"
        style="
          height: 100%;
          margin-left: 1px;
          display: flex;
          align-items: center;
        "
      >
        <v-btn fab x-small outlined elevation="0" color="var(--font-sub-color)">
          <v-icon size="25">mdi-menu-left</v-icon>
        </v-btn>
      </div>
      <v-card
        elevation="8"
        style="
          height: 100%;
          margin-left: 15px;
          display: flex;
          flex-direction: column;
        "
        v-if="!mini"
        class="animate__animated animate__fadeInRight"
      >
        <v-card-text>
          <h3 style="margin-bottom: 10px">Database Monitorting</h3>
          <span style="font-size: 14px"
            ><v-icon size="16" style="margin-top: -2px"
              >mdi-clock-time-four-outline</v-icon
            >
            {{ dates[0] }} ~ {{ dates[1] }}</span
          >
          <br />
          <span style="font-size: 14px"
            ><v-icon size="16" style="margin-top: -2px">mdi-database</v-icon>
            {{ SelectedSchema }}</span
          >
        </v-card-text>

        <v-divider style="margin-bottom: 20px"></v-divider>
        <template>
          <v-tabs
            vertical
            background-color="transparent"
            color="var(--font-sub2-color)"
            v-model="tab"
          >
            <v-tab @click="moveScroll(0)"><span>Database Status</span> </v-tab>
            <v-tab @click="moveScroll(1)">Schema Status</v-tab>
            <v-tab @click="moveScroll(2)">Schema Top Query</v-tab>
          </v-tabs>
        </template>
        <div style="margin: auto 20px 0px auto; padding-bottom: 70px">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                elevation="2"
                color="secondary"
                fab
                small
                dark
                class="ml-3"
                @click="
                  dialog = true;
                  mini = true;
                "
                v-bind="attrs"
                v-on="on"
              >
                <v-icon> mdi-file-pdf </v-icon>
              </v-btn>
            </template>
            <span>Report</span>
          </v-tooltip>
        </div>
      </v-card>
    </v-navigation-drawer>

    <v-dialog v-model="dialog" max-width="600px">
      <ReportModal @kill-modal="dialog = false" />
    </v-dialog>
    <div
      id="schema-monitoring-top"
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
import ReportModal from "@/components/main/ReportModal.vue";

import { mapActions, mapMutations, mapGetters } from "vuex";

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
      tab: 0,
      mini: true,
      fixedMini: true,
      dialog: false,
    };
  },
  components: {
    SchemaWhole,
    SchemaTopQuery,
    SchemaDetail,
    Loading,
    NotExistData,
    ReportModal,
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
    moveScroll(index) {
      var node = null;
      switch (index) {
        case 0:
          node = document.getElementById("app-main");
          break;
        case 1:
          node = document.getElementById("SchemaDetail");
          break;
        case 2:
          node = document.getElementById("SchemaQuerys");
          break;
      }
      var yourHeight = 48;
      node.scrollIntoView(true);

      var scrolledY = window.scrollY;

      if (scrolledY) {
        window.scroll(0, scrolledY - yourHeight);
      }
    },
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
    document
      .getElementById("home-main")
      .addEventListener("scroll", this.getPositionScroll);
  },

  beforeDestory() {
    document
      .getElementById("home-main")
      .removeEventListener("scroll", this.getPositionScroll);
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
