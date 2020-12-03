<template>
  <v-app>
    <div class="app-container">
      <div>
        <transition name="fade" mode="out-in">
          <router-view></router-view>
        </transition>
      </div>
    </div>
  </v-app>
</template>

<script>
import { mapActions } from "vuex";
export default {
  name: "App",
  methods: {
    ...mapActions("Account", ["checkExistedAdmin"]),
  },
  created() {
    this.checkExistedAdmin();
  },
};
</script>

<style>
:root {
  --main-color: #034ea2;
  --main-sub-color: #4b74aa;
  --main-point-color: #3d2985;
  --font-main-color: #333333;
  --font-sub-color: #9e9e9e;
  --font-sub2-color: #636363;
}
.app-container {
  width: 100%;
  height: 100%;
}
html {
  overflow: hidden !important;
}
#app {
  background: #fbfdfd;
}

/* 스크롤 바 넓이 16px */
::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}
/* 스크롤 바 기본 색상 */
::-webkit-scrollbar-track {
  background-color: rgb(226, 226, 226);
}
/* 스크롤 구간 배경 색상 */
::-webkit-scrollbar-thumb {
  background-color: rgb(192, 192, 192);
}
/* 스크롤 바 위에 마우스 올렸을 때(hover) 색상 */
::-webkit-scrollbar-thumb:hover {
  background-color: rgb(134, 134, 134);
}
</style>
