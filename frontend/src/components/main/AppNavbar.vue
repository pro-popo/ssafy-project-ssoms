<template>
  <v-app-bar class="nav-bar" dense dark color="#202021">
    <v-toolbar-title class="logo" onclick="location.href=''"
      ><img class="logo-back" src=@/assets/SSOMS-long.png>
    </v-toolbar-title>
    
    <!-- <v-toolbar-title>Samsung SDI Oracle Monitoring Solution</v-toolbar-title> -->
    <!-- <span><a href=""><img src=@/assets/SDI.png></a></span> -->
    <v-spacer></v-spacer>
    <v-btn icon @click="outlierlog"><v-icon>mdi-bell</v-icon></v-btn>
    <div class="log_div" v-bind:class="{display_hidden : logflag}">
        <div class="text-center py-5" v-if="getOutlierLog.length == 0">
            <p>이상치 데이터가 </p>
            <p>발견되지 않았습니다.</p>
        </div>
        <ul class="log_list" v-else>
            <li v-for="(log, index) in getOutlierLog" :key="'log' + index" class="log">
                <p>{{log.time}}</p>
                <p>CPU Time Ratio : {{log.databaseCpuTimeRatio}}</p>
            </li>
        </ul>
    </div>
  </v-app-bar>
  
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "AppNavbar",
  data() {
    return {
      logflag: true
    };
  },
  methods: {
      ...mapActions(["getOutlierData"]),
      outlierlog(){
        //  let start = new Date().toISOString().substr(0, 10);
        //  let end = new Date();
        //  end.setDate(end.getDate()+1);
        //  end = end.toISOString().substr(0, 10);
        //    this.getOutlierData({ start: '/' +start, end: '/' +end });
          this.logflag = !this.logflag;
      }
  },
  computed: {
    ...mapGetters(["getOutlierLog"]),
  }
};
</script>

<style>
.display_hidden{
    display: none;
}
.log_list {
    list-style-type : none;
    padding: 0px !important;
}
.log {
    border-bottom: 1px gray solid;
    z-index: 99999;
    text-align: center;
}
.log_div{
    color: black;
    position: absolute;
    top: 48px;
    right: 11px;
    z-index: 99999;
    width: 200px;
    max-height: 200px;
    color: white;
    opacity: 0.9;
    overflow: auto;
    padding : 0px;
    margin : 0px;
    background-color: rgb(41, 41, 42);
}
.nav-bar {
  background: rgb(41, 41, 42);
  /* background: linear-gradient(130deg, rgba(41,41,42,1) 66%, rgba(25,45,156,1) 100%); */
}
.v-toolbar-title {
  color: linear-gradient(to right, var(--main-color), var(--main-point-color));
}
.logo {
  margin-left: 56px;
  cursor: pointer;
  font-weight: 550;
}

.logo-back {
  margin-top: 0.5rem;
  width: 120px;
}
</style>
