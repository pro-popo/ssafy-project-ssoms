<template>
  <div class="user-signup-container">
    <SignUpForm @signup-success="signUpSuccess" />
  </div>
</template>

<script>
import SignUpForm from "@/components/non-account/SignUpForm.vue";

export default {
  name: "SignUpUser",
  components: {
    SignUpForm
  },
  data() {
    return {
      isAdmin: false
    };
  },
  methods: {
    signUpSuccess() {
      this.$emit("signup-success");
    }
  }
};
</script>

<style>
.user-signup-container {
  width: 100%;
  height: 100%;
  padding-top: 30px;
}
</style>
