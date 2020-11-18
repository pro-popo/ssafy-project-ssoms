<template>
  <div class="login-container">
    <h1 class="login-header-title">Login</h1>
    <v-form ref="form" class="login-form">
      <v-text-field
        prepend-icon="mdi-email"
        v-model="account.email"
        :rules="[rules.required]"
        label="E-mail"
        @keypress.enter="login"
        required
      ></v-text-field>
      <v-text-field
        prepend-icon="mdi-lock"
        v-model="account.pw"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, emailMatch]"
        :type="show1 ? 'text' : 'password'"
        name="input-10-1"
        label="Password"
        @click:append="show1 = !show1"
        @keypress.enter="login"
      ></v-text-field>
      <v-btn
        color="var(--main-color)"
        @click="login"
        class="login-btn"
        dark
        rounded
      >
        LOGIN
      </v-btn>
    </v-form>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring.js";

export default {
  name: "Login",
  data() {
    return {
      show1: false,
      account: {
        email: "",
        pw: ""
      },
      rules: {
        required: (v) => !!v || "Required."
      },
      loginSuccess: true
    };
  },
  computed: {
    emailMatch() {
      return (
        this.loginSuccess || "The email and password you entered don't match"
      );
    }
  },
  methods: {
    login() {
      this.loginSuccess = true;
      setTimeout(
        function() {
          if (this.$refs.form.validate()) {
            axios
              .post(SERVER.URL + SERVER.ROUTES.signin, this.account)
              .then((res) => {
                console.log(res);
                if (res.data.result == "success") {
                  sessionStorage.setItem("loginSession", this.account.email);
                  this.$refs.form.reset();
                  this.$router.push({ name: "Home" });
                } else if (res.data.result == "fail") {
                  this.loginSuccess = false;
                }
              })
              .catch((err) => {
                console.log(err);
              });
          }
        }.bind(this),
        100
      );
    },
    openSignUp() {
      this.$emit("open-signup");
    }
  }
};
</script>

<style>
.login-container {
  margin: 10px;
  min-width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.login-btn {
  width: 100%;
  margin-top: 20px;
}
.login-form {
  width: 70% !important;
  max-width: 350px;
  margin-top: 40px;
}

.find-pw-link {
  color: var(--font-sub-color);
}
.find-pw-link:hover {
  color: var(--main-sub-color);
  cursor: pointer;
}

.login-header-title {
  color: var(--font-main-color);
}
.v-input__control {
  margin-left: 5px;
}
</style>
