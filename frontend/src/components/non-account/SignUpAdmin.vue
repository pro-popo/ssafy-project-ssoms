<template>
  <div class="admin-container">
    <div class="admin-container-left">
      <div align="center">
        <h1>Admin 회원가입</h1>
      </div>
    </div>
    <div class="admin-container-right">
      <SignUpForm :isAdmin="isAdmin" />
    </div>
  </div>
</template>

<script>
import SignUpForm from "@/components/non-account/SignUpForm.vue";

export default {
  name: "SignUpAdmin",
  components: {
    SignUpForm,
  },
  data() {
    return {
      isAdmin: true,
    };
  },
};
</script>

<style>
.admin-container {
  width: 100%;
  height: 100vh;
  display: flex;
}
.admin-container-left {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30%;
  height: 100%;
  color: white;
  background: var(--main-color);
  /* background: -webkit-linear-gradient(
    to right,
    var(--main-color),
    var(--main-sub-color)
  );
  background: linear-gradient(
    to right,
    var(--main-color),
    var(--main-sub-color)
  ); */
}
.admin-container-right {
  width: 70%;
  height: 100%;
  background: white;
}
</style>
