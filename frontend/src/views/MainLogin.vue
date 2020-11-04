<template>
  <div class="main-login-container" id="main-login-container">
    <div class="form-container sign-up-container">
      <SignUpUser @signup-success="signUpSuccess" />
      <p class="signup-text">
        Already have an Account?
        <span @click="ChangeSignIn">Login here ➜ </span>
      </p>
    </div>
    <div class="form-container sign-in-container" align="center">
      <v-snackbar
        class="signup-snackbar"
        v-model="successSignUp"
        :timeout="timeout"
        absolute
        dark
        color="success"
        top
      >
        <v-icon>mdi-check-circle</v-icon>
        <span style="font-weight:bold; padding-left:10px;"
          >Sign Up Completed!</span
        >

        <template v-slot:action="{ attrs }">
          <v-btn
            color="white"
            text
            v-bind="attrs"
            @click="SET_SUCCESS_SIGNUP(false)"
          >
            Close
          </v-btn>
        </template>
      </v-snackbar>
      <Login />
      <v-btn
        class="change-btn"
        rounded
        outlined
        id="signUp"
        color="var(--main-sub-color)"
        @click="ChangeSignUp"
      >
        Sign Up
      </v-btn>
      <!-- <p class="find-pw-link">Forgot your password?</p> -->
    </div>

    <div class="overlay-container" id="overlay">
      <div class="overlay">
        <div class="overlay-panel overlay-left">
          <h1>회원가입창</h1>
          <p>소개글</p>
          <v-btn
            class="change-btn"
            rounded
            outlined
            color="white"
            id="signIn"
            @click="ChangeSignIn"
          >
            Login
          </v-btn>
        </div>
        <div class="overlay-panel overlay-right">
          <h1>SSOMS</h1>
          <h3>
            <span>S</span>amsung <span>S</span>DI <span>O</span>racle
            <span>M</span>onitoring <span>S</span>olution
          </h3>
          <p>소개글</p>
          <v-btn
            class="change-btn"
            rounded
            outlined
            color="white"
            id="signUp"
            @click="ChangeSignUp"
          >
            Sign Up
          </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Login from "@/components/non-account/Login.vue";
import SignUpUser from "@/components/non-account/SignUpUser.vue";
import { mapGetters, mapMutations } from "vuex";
export default {
  name: "MainLogin",
  data() {
    return {
      timeout: 2000
    };
  },
  components: {
    Login,
    SignUpUser
  },
  computed: {
    successSignUp: {
      get() {
        return this.isSuccessSignUp;
      },
      set(value) {
        this.SET_SUCCESS_SIGNUP(value);
      }
    },
    ...mapGetters("Account", ["isSuccessSignUp"])
  },
  methods: {
    ...mapMutations("Account", ["SET_SUCCESS_SIGNUP"]),
    ChangeSignIn() {
      const container = document.getElementById("main-login-container");
      container.classList.remove("right-panel-active");
    },
    ChangeSignUp() {
      const container = document.getElementById("main-login-container");
      container.classList.add("right-panel-active");
    },
    signUpSuccess() {
      this.ChangeSignIn();
    }
  }
};
</script>

<style>
.main-login-container {
  height: 100vh;
}
.logo-text-point span {
  color: white;
}
.form-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.sign-in-container {
  background: #fff;
  left: 0;
  width: 70%;
  z-index: 2;
}
.sign-up-container {
  background: #fff;
  left: 0;
  width: 70%;
  opacity: 0;
  z-index: 1;
}
.overlay-container {
  position: absolute;
  top: 0;
  left: 70%;
  width: 30%;
  height: 100%;
  overflow: hidden;
  transition: transform 0.6s ease-in-out;
  z-index: 100;
}

.signup-snackbar {
  position: absolute;
  z-index: 300;
  margin-top: 100px;
}

.main-login-container.right-panel-active .sign-in-container {
  transform: translateX(30vw);
}
.main-login-container.right-panel-active .sign-up-container {
  transform: translateX(30vw);
  opacity: 1;
  z-index: 5;
  animation: show 0.6s;
}

.main-login-container.right-panel-active .overlay-container {
  transform: translateX(-233.5%);
}

.overlay {
  background: var(--main-color);
  background: -webkit-linear-gradient(
    to right,
    var(--main-color),
    var(--main-sub-color)
  );
  background: linear-gradient(
    to right,
    var(--main-color),
    var(--main-sub-color)
  );
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 0 0;
  color: #ffffff;
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.main-login-container.right-panel-active .overlay {
  transform: translateX(50%);
}

.overlay-panel {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 40px;
  text-align: center;
  top: 0;
  height: 100%;
  width: 50%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.overlay-left {
  transform: translateX(-20%);
}

.main-login-container.right-panel-active .overlay-left {
  transform: translateX(0);
}

.overlay-right {
  right: 0;
  transform: translateX(0);
}

.main-login-container.right-panel-active .overlay-right {
  transform: translateX(20%);
}
.change-btn {
  width: 70%;
  max-width: 350px;
  margin-bottom: 10px;
}
.signup-text {
  color: var(--font-sub-color);
  margin-top: 10px;
}

.signup-text span {
  font-weight: bold;
  color: #838383;
}
.signup-text span:hover {
  color: var(--main-sub-color);
  cursor: pointer;
}
</style>
