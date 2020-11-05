<template>
  <div class="signup-form-container" align="center">
    <div v-if="isAdmin" class="signup-admin-icon">
      <v-icon style="font-size:50px; color:var(--main-color)"
        >mdi-account-cog</v-icon
      >
      <h3 class="admin-h2">
        ADMIN
      </h3>
    </div>
    <h1>Sign Up</h1>
    <v-form ref="form" class="signup-form">
      <v-text-field
        v-model="account.email"
        prepend-icon="mdi-email"
        label="E-mail"
        :rules="[rules.required, rules.emailRules, duplicateEmailRules]"
        required
      ></v-text-field>

      <v-text-field
        v-model="account.pw"
        prepend-icon="mdi-lock"
        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required]"
        :type="show1 ? 'text' : 'password'"
        name="input-10-1"
        label="Password"
        @click:append="show1 = !show1"
      ></v-text-field>

      <v-text-field
        v-model="confirmPw"
        prepend-icon="mdi-lock-check"
        :rules="[rules.required, pwConfirmRules]"
        :type="show1 ? 'text' : 'password'"
        name="input-10-1"
        label="Confirm Password"
        @click:append="show1 = !show1"
      ></v-text-field>

      <v-text-field
        v-model="account.name"
        prepend-icon="mdi-account"
        :rules="[rules.required]"
        label="Name"
        required
      ></v-text-field>

      <v-text-field
        v-model="account.employeeId"
        prepend-icon="mdi-clipboard-account"
        :rules="[rules.required]"
        label="Employee Id"
        required
      ></v-text-field>

      <v-text-field
        v-model="account.phoneNumber"
        prepend-icon="mdi-phone"
        :rules="[rules.required]"
        label="Phone Number"
        required
      ></v-text-field>
    </v-form>
    <v-btn
      color="var(--main-color)"
      rounded
      dark
      class="sign-btn"
      @click="signUp"
    >
      Sign Up
    </v-btn>

    <p class="reset-link" color="primary" @click="formReset">
      Reset
    </p>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring";
import { mapMutations } from "vuex";
export default {
  name: "SingUpForm",
  props: {
    isAdmin: Boolean
  },

  data: () => ({
    show1: false,
    account: {
      email: "",
      name: "",
      pw: "",
      employeeId: "",
      phoneNumber: "",
      admin: false
    },
    confirmPw: "",
    isDuplicateEmail: true,

    rules: {
      required: (v) => !!v || "Required.",
      emailRules: (v) => /.+@.+/.test(v) || "E-mail must be valid"
      // min: (v) => v.length >= 8 || "Min 8 characters",
    }
  }),
  computed: {
    pwConfirmRules() {
      return this.account.pw === this.confirmPw || "Password do not match.";
    },
    duplicateEmailRules() {
      return this.isDuplicateEmail || "E-mail already exists.";
    }
  },
  created() {
    this.account.admin = this.isAdmin;
  },
  methods: {
    ...mapMutations("Account", ["SET_EXISTED_ADMIN", "SET_SUCCESS_SIGNUP"]),
    signUp() {
      this.isDuplicateEmail = true;
      this.account.phoneNumber = this.account.phoneNumber.replaceAll("-", "");
      setTimeout(
        function() {
          if (this.$refs.form.validate()) {
            axios
              .put(SERVER.URL + SERVER.ROUTES.signup, this.account)
              .then((res) => {
                if (res.data.result == "success") {
                  if (this.isAdmin) {
                    this.SET_EXISTED_ADMIN(true);
                  }
                  this.SET_SUCCESS_SIGNUP(true);
                  this.$emit("signup-success");
                  this.formReset();
                  if (this.isAdmin) this.$router.push({ name: "Login" });
                } else if (res.data.result == "fail") {
                  this.isDuplicateEmail = false;
                }
              })
              .catch((err) => console.log(err));
          }
        }.bind(this),
        100
      );
    },
    formReset() {
      // this.$refs.form.reset();
      const resetAccount = {
        email: "",
        name: "",
        pw: "",
        employeeId: "",
        phoneNumber: "",
        admin: false
      };
      this.account = resetAccount;
      this.confirmPw = "";
      this.isDuplicateEmail = true;

      this.$refs.form.resetValidation();
    }
  }
};
</script>

<style>
.signup-form-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.signup-form {
  width: 100%;
  margin: 40px 0px;
}
.v-form {
  width: 70%;
  max-width: 350px;
}
.reset-link {
  color: var(--font-sub-color);
}
.reset-link:hover {
  color: var(--main-sub-color);
  cursor: pointer;
}
.sign-btn {
  width: 70%;
  max-width: 350px;
  margin-bottom: 10px;
}

.v-input__control {
  margin-left: 5px;
}

.admin-h2 {
  color: var(--main-color);
}

.signup-admin-icon {
  width: 130px;
  height: 130px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
