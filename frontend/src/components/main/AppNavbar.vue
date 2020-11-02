<template>
  <div class="nav-bar">
    <span><a href=""><img src=@/assets/SDI.png></a></span>
    <span>
      <span>한국어</span>
      |
      <span>ENGLISH</span>
    </span>
  </div>
</template>

<script>
export default {
  name: "AppNavbar"
};
</script>

<style>
.nav-bar {
  display: flex;
  justify-content: space-between;
  background-color: whitesmoke;
  height: 60px;
  line-height: 60px;
  padding: 0px 50px 0px 50px;
  position: sticky;
  top: 0px;
  z-index: 100;
}
</style>
