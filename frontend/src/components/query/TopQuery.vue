<template>
  <div>
    <div class="query-select-box">
      <span><button @click="showBox(1)">CPU 기준</button></span> |
      <span><button @click="showBox(2)">시간 기준</button></span> |
      <span><button @click="showBox(3)">리소스 기준</button></span>
    </div>
    <div v-if="cpuMonitor" class="query-box-middle">
      <ul
        v-for="query in cpuTopQuery"
        :key="query.queryID"
        @click="test(query)"
      >
        {{
          query.queryID
        }}
        {{
          query.queryContent
        }}
      </ul>
    </div>
    <div v-if="timeMonitor" class="query-box-middle">
      <ul
        v-for="query in timeTopQuery"
        :key="query.queryID"
        @click="test(query)"
      >
        {{
          query.queryID
        }}

        {{
          query.queryContent
        }}
      </ul>
    </div>
    <div v-if="resourceMonitor" class="query-box-middle">
      <ul
        v-for="query in resourceTopQuery"
        :key="query.queryID"
        @click="test(query)"
      >
        {{
          query.queryID
        }}
        {{
          query.queryContent
        }}
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "TopQuery",
  data() {
    return {
      testdata: "nothing",
      cpuMonitor: true,
      timeMonitor: false,
      resourceMonitor: false
    };
  },
  methods: {
    // test(queryTest) {
    //   this.testdata = queryTest.queryData;
    // }
    showBox(number) {
      if (number == 1) {
        this.cpuMonitor = true;
        this.timeMonitor = false;
        this.resourceMonitor = false;
      } else if (number == 2) {
        this.cpuMonitor = false;
        this.timeMonitor = true;
        this.resourceMonitor = false;
      } else {
        this.cpuMonitor = false;
        this.timeMonitor = false;
        this.resourceMonitor = true;
      }
    }
  },
  computed: {
    ...mapGetters("Query", ["cpuTopQuery"]),
    ...mapGetters("Query", ["timeTopQuery"]),
    ...mapGetters("Query", ["resourceTopQuery"])
  }
};
</script>

<style>
.query-box-middle {
  background-color: rgba(135, 207, 235, 0.247);
  height: 170px;
  line-height: 34px;
  width: 100%;
  margin-bottom: 10px;
}

.query-select-box {
  /* background-color: rgba(135, 207, 235, 0.247); */
  margin: 0px 5px 0px 0px;
}
</style>
