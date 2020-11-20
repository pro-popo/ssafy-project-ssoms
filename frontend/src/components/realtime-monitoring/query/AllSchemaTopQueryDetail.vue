<template>
  <div style="margin-top:10px">
    <v-card
      elevation="2"
      class="animate__animated animate__fadeIn realtime-query-detail-container"
      v-if="getTopQueryDetail"
    >
      <v-card-text>
        <div>
          <v-icon>mdi-database</v-icon>
          <b style="font-size:16px">
            {{ getTopQueryDetail.parsingSchemaName }}</b
          >
        </div>
        <div class="realtime-query-detail-sql" style="padding: 5px 29px 0px">
          <div>
            <p>SQL ID</p>
            <b>{{ getTopQueryDetail.sqlId }}</b>
          </div>
          <div>
            <p>SQL</p>
            <b style="word-break:normal; width:90%"
              >{{ getTopQueryDetail.sql }}
            </b>
          </div>
        </div>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-text>
        <div class="realtime-query-detail">
          <div>
            <h4 class="query-detail-title">executions</h4>
            <span class="query-detail-text">
              {{ getTopQueryDetail.executions }} <span>count</span>
            </span>
          </div>

          <div>
            <h4 class="query-detail-title">cpuTimeAvg</h4>
            <span class="query-detail-text">
              {{ getTopQueryDetail.cpuTimeAvg }} <span>sec</span>
            </span>
          </div>

          <div>
            <h4 class="query-detail-title">bufferGetsAvg</h4>
            <span class="query-detail-text">
              {{ getTopQueryDetail.bufferGets }} <span>count</span>
            </span>
          </div>

          <div>
            <h4 class="query-detail-title">diskReadsAvg</h4>
            <span class="query-detail-text">
              {{ getTopQueryDetail.diskReads }} <span>count</span>
            </span>
          </div>
          <div>
            <h4 class="query-detail-title">rowsProcessed</h4>
            <span class="query-detail-text">
              {{ getTopQueryDetail.rowsProcessed }} <span>count</span>
            </span>
          </div>

          <div>
            <h4 class="query-detail-title">elapsedTimeAvg</h4>
            <span class="query-detail-text">
              {{ getTopQueryDetail.elapsedTimeAvg }} <span>sec</span>
            </span>
          </div>
        </div>
      </v-card-text>
    </v-card>
    <v-card
      elevation="2"
      color="#E0E0E0"
      class="realtime-non-query"
      height="200px"
      v-else
    >
      <v-card-text style="display:flex; justify-content:center">
        <h3>Query Detail</h3>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "AllSchemaTopQueryDetail",
  computed: {
    ...mapGetters("TopQuery", ["getTopQueryDetail"])
  }
};
</script>

<style>
.realtime-query-detail-container .v-card__text {
  padding: 18px 16px 16px 16px;
}
.realtime-query-detail {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.realtime-non-query {
  display: flex;
  justify-content: center;
  align-items: center;
}
.realtime-non-query > div {
  font-weight: 900;
  opacity: 0.6;
}
.query-detail-text {
  font-size: 30px;
  font-weight: 900;
  color: #6440e3;
}
.query-detail-text > span {
  font-size: 22px;
  font-weight: bold;
  color: var(--main-point-color);
}
.query-detail-title {
  /* opacity: 0.6; */
  margin-bottom: 5px;
}

.realtime-query-detail div {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 10px 25px;
  /* margin: 10px 0px; */
  width: 16.66%;
  border-right: 2px solid rgb(226, 226, 226);
}
.realtime-query-detail div:nth-child(6) {
  border-right: 0px !important;
}
.realtime-query-detail-sql div {
  display: flex;
}
.realtime-query-detail-sql p {
  width: 80px;
  margin-bottom: 5px;
}
</style>
