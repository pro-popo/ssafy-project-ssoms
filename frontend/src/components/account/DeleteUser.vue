<template>
  <v-dialog v-model="dialog" max-width="500px">
    <v-card>
      <v-card-title class="delete-headline">
        <span style="margin-left:5px; color:var(--font-sub2-color)"
          >Delete Your Account
        </span>
        <v-btn icon @click="dialog = !dialog">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-divider></v-divider>
      <v-card-text style="padding-top:30px;">
        <span style="font-size:18px; padding-top:20px;">
          To delete your account:
        </span>
        <span>Please enter your email and password.</span>

        <v-form ref="form" class="delete-input">
          <v-text-field
            prepend-icon="mdi-email"
            v-model="account.email"
            :rules="[rules.required]"
            label="E-mail"
            required
          ></v-text-field>
          <v-text-field
            prepend-icon="mdi-lock"
            v-model="account.pw"
            :rules="[rules.required, emailMatch]"
            :type="'password'"
            name="input-10-1"
            label="Password"
            @keypress.enter="dialog = false"
          ></v-text-field>
          <v-text-field
            v-model="confirmPw"
            prepend-icon="mdi-lock-check"
            :rules="[rules.required, pwConfirmRules]"
            :type="'password'"
            name="input-10-1"
            label="Confirm Password"
            @click:append="show1 = !show1"
          ></v-text-field>
        </v-form>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text color="var(--main-color)" @click="deleteUser">Submit</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import SERVER from "@/api/spring.js";
import axios from "axios";
import { mapActions } from "vuex";

export default {
  name: "DeleteUser",
  data() {
    return {
      dialog: false,
      account: {
        email: "",
        pw: ""
      },
      confirmPw: "",
      rules: {
        required: (v) => !!v || "Required."
      },
      deleteSuccess: true
    };
  },
  computed: {
    emailMatch() {
      return (
        this.deleteSuccess || "The email and password you entered don't match"
      );
    },
    pwConfirmRules() {
      return this.account.pw === this.confirmPw || "Password do not match.";
    }
  },
  props: {
    deleteDialog: Boolean
  },
  watch: {
    deleteDialog: function() {
      this.dialog = true;
    },
    dialog: function() {
      this.$refs.form.resetValidation();
      if (this.account.email != "" || this.account.pw != "") {
        this.$refs.form.reset();
        this.deleteSuccess = true;
      }
    }
  },

  methods: {
    ...mapActions("Account", ["logout"]),
    deleteUser() {
      if (this.$refs.form.validate()) {
        axios
          .post(SERVER.URL + SERVER.ROUTES.deleteUser, this.account)
          .then((res) => {
            if (res.data.result == "success") {
              this.logout();
            } else if (res.data.result == "fail") {
              this.deleteSuccess = false;
              console.log("실패");
            }
          });
      }
    }
  }
};
</script>

<style>
.delete-headline {
  justify-content: space-between;
  height: 40px;
  padding: 10px 20px 42px 20px !important;
}
.delete-input {
  margin-top: 15px !important;
}
</style>
