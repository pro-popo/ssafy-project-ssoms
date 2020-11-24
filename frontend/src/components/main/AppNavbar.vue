<template>
  <v-app-bar class="nav-bar" dense dark color="#202021">
    <v-toolbar-title class="logo" onclick="location.href=''"
      ><img class="logo-back" src=@/assets/SSOMS-long.png>
    </v-toolbar-title>
    
    <!-- <v-toolbar-title>Samsung SDI Oracle Monitoring Solution</v-toolbar-title> -->
    <!-- <span><a href=""><img src=@/assets/SDI.png></a></span> -->
    <v-spacer></v-spacer>
    <v-btn icon @click="outlierlog"><v-icon>mdi-bell</v-icon></v-btn>
    <div class="log_list">
        <!--<p>testdddd</p>-->
    </div>
  </v-app-bar>
  
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "AppNavbar",
  data() {
    return {
      logflag: false
    };
  },
  methods: {
      ...mapActions(["getOutlierData"]),
      outlierlog(){
          let today = new Date().toISOString().substr(0, 10);
          console.log(today);
            this.getOutlierData({ start: '/' +today, end: '/' +today });
          this.logflag = !this.logflag;
      }
  },
  computed: {
    ...mapGetters(["getOutlierLog"]),
  }
};
</script>

<style>
.log_list{
    color: black;
    position: absolute;
    top: 48px;
    right: 200px;
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
