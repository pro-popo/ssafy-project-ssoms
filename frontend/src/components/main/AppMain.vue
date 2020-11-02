<template>
  <div class="app-main">
    <!-- <v-btn color="error" @click="test">TEST</v-btn>
    <div class="zz">
      {{ testString }}
    </div> -->
    <router-view />
  </div>
</template>

<script>
import SERVER from "@/api/spring.js";
import axios from "axios";
export default {
  name: "AppMain",
  data() {
    return {
      testString: "결과값 입니다."
    };
  },
  methods: {
    test() {
      axios
        .get(SERVER.URL + "/admin/test")
        .then((res) => (this.testString = res));
    }
  }
};
</script>

<style>
.app-main {
  margin-left: 220px;
  padding: 50px 100px 0px 100px;
}
.zz {
  border: 3px solid gray;
  width: 500px;
  height: 500px;
}
</style>
