<template>
  <v-app>
    <div class="main-container">
      <div v-if="isExistedAdmin">
        <transition name="fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </div>
      <div v-else class="main-container">
        <SignUpAdmin />
      </div>
    </div>
  </v-app>
</template>

<script>
// import Home from "@/views/Home.vue";

import { mapGetters, mapActions } from "vuex";
import SignUpAdmin from "@/components/non-account/SignUpAdmin.vue";

export default {
  name: "App",
  components: {
    // Home

    SignUpAdmin
  },
  computed: {
    ...mapGetters("Account", ["isExistedAdmin"])
  },
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
#app {
  overflow: hidden !important;

  -ms-overflow-style: none;
  background: #f3f3f3;
}
/* 스크롤 바 넓이 16px */
::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}
/* 스크롤 바 기본 색상 */
::-webkit-scrollbar-track {
  background-color: thistle;
}
/* 스크롤 구간 배경 색상 */
::-webkit-scrollbar-thumb {
  background-color: slateblue;
}
/* 스크롤 바 위에 마우스 올렸을 때(hover) 색상 */
::-webkit-scrollbar-thumb:hover {
  background-color: blueviolet;
}
/* 스크롤 상하단 버튼 넓이와 색상 */
::-webkit-scrollbar-button:start:decrement,
::-webkit-scrollbar-button:end:increment {
  width: 10px;
  height: 10px;
  background-color: mediumpurple;
}
</style>
