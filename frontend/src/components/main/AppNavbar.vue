<template>
  <v-app-bar class="nav-bar" dense dark color="#202021">
    <v-toolbar-title class="logo" onclick="location.href=''"
      ><img class="logo-back" src=@/assets/logoBack.png> SSOMS</v-toolbar-title
    >

    <!-- <v-toolbar-title>Samsung SDI Oracle Monitoring Solution</v-toolbar-title> -->
    <!-- <span><a href=""><img src=@/assets/SDI.png></a></span> -->
    <v-spacer></v-spacer>
  </v-app-bar>
</template>

<script>
export default {
  name: "AppNavbar"
};
</script>

<style>
.v-toolbar-title {
  color: linear-gradient(to right, var(--main-color), var(--main-point-color));
}
.logo {
  cursor: pointer;
  font-weight: 550;
}

.logo-back {
  width: 43px;
  margin-bottom: -6px;
  margin-right: -31px;
}
</style>
