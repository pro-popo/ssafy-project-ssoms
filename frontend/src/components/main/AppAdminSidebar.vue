<template>
  <div>
    <div class="admin-setting">Setting</div>
    <div>
      <router-link :to="{ name: 'OracleDB' }">Oracle DB</router-link>
      <router-link :to="{ name: 'Schema' }">Schema</router-link>
      <router-link :to="{ name: 'Member' }">Member</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "AppAdminSidebar"
};
</script>

<style>
.admin-side-bar {
  padding-top: 60px;
  height: 100%;
  width: 220px;
  position: absolute;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: rgb(221, 221, 221);
}
.admin-side-bar hr {
  margin: 0px;
}
.admin-side-bar a {
  padding: 8px 0px 8px 32px;
  text-decoration: none;
  font-size: 20px;
  display: block;
  transition: 0.2s ease-in-out;
}
.admin-setting {
  padding: 8px 0px 8px 32px;
  font-size: 25px;
}
.admin-side-bar a:hover,
.offcanvas a:focus {
  color: #fff;
}

.admin-id {
  padding: 0px 0px 0px 32px;
  height: 100px;
  line-height: 100px;
  font-size: 25px;
  cursor: pointer;
}

.mdi-account-circle-outline {
  font-size: 25px;
}

/* 미디어쿼리 적용 */
/* @media screen and (max-height: 450px) {
  .side-bar {
    padding-top: 15px;
  }
  .side-bar a {
    font-size: 18px;
  }
} */
</style>
