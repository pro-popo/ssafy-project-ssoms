<template>
  <div>
    <div>
      <router-link :to="{ name: 'MainHome' }">Home</router-link>
      <router-link :to="{ name: 'PCResource' }">PC자원</router-link>
      <router-link :to="{ name: 'Traffic' }">트래픽</router-link>
      <router-link :to="{ name: 'QueryMonitoring' }"
        >Query 모니터링</router-link
      >
      <router-link :to="{ name: 'RealMonitoring' }"
        >실시간 모니터링</router-link
      >
    </div>
    <hr class="mt-5" />
    <div class="ml-7 mt-3"><button>Report</button></div>
  </div>
</template>

<script>
export default {
  name: "AppUserSidebar"
};
</script>

<style>
.DB-icon {
  font-size: 25px;
}
.user-side-bar {
  /* margin-top: 60px; */
  /* padding-top: 70px; */
  height: 100%;
  width: 220px;
  /* position: absolute; */
  /* z-index: 1; */
  /* top: 0; */
  /* left: 0; */
  background-color: rgb(221, 221, 221);
}
.user-side-bar hr {
  margin: 0px;
}
/* .user-side-bar a {
  padding: 8px 0px 8px 32px;
  text-decoration: none;
  font-size: 20px;
  display: block;
  transition: 0.2s ease-in-out;
}

.user-side-bar a:hover,
.offcanvas a:focus {
  color: #fff;
}

.user-id {
  padding: 0px 0px 0px 32px;
  height: 40px;
  line-height: 40px;
  font-size: 17px;
  cursor: pointer;
  display: flex;
} */
</style>
