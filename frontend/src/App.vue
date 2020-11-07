<template>
  <v-app>
    <div class="main-container">
      <!-- <div v-if="!isExistedAdmin" class="main-container">
        <SignUpAdmin />
      </div> -->
      <div>
        <transition name="fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </div>
    </div>
  </v-app>
</template>

<script>
// import Home from "@/views/Home.vue";

import { mapActions } from "vuex";
// import SignUpAdmin from "@/components/non-account/SignUpAdmin.vue";

export default {
  name: "App",
  // components: {
  //   // Home

  //   SignUpAdmin
  // },
  // computed: {
  //   ...mapGetters("Account", ["isExistedAdmin"])
  // },
  methods: {
    ...mapActions("Account", ["checkExistedAdmin"])
  },
  created() {
    this.checkExistedAdmin();
  }
};
</script>

<style>
:root {
  --main-color: #273896;
  --main-sub-color: #4b74aa;
  --main-point-color: #3d2985;
  --font-main-color: #2b2b2b;
  --font-sub-color: #9e9e9e;
  --font-sub2-color: #636363;
}
.main-container {
  width: 100%;
  height: 100%;
}
html {
  overflow: hidden !important;
  /* -ms-overflow-style: none; */
}
#app {
  background: #f3f3f3;
}
/* 스크롤바의 width */
::-webkit-scrollbar {
  width: 10px;
}

/* 스크롤바의 전체 배경색 */
::-webkit-scrollbar-track {
  background-color: rgb(196, 196, 196);
}

/* 스크롤바 */
::-webkit-scrollbar-thumb {
  background: rgb(114, 114, 114);
  opacity: 10px;
  border-radius: 10px;
}
::-webkit-scrollbar-thumb:hover {
  background: rgb(3, 78, 162, 0.8);
}
</style>
