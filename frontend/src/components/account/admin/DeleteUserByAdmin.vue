<template>
  <v-dialog v-model="dialog" max-width="500px">
    <v-card>
      <v-card-title class="delete-headline">
        <span style="margin-left:10px; color:var(--font-sub2-color)">
          Delete Member
        </span>
        <v-btn icon @click="dialog = !dialog">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-divider></v-divider>
      <v-card-text style="padding-top:20px; padding-left:30px">
        <h2
          style="font-size:20px; padding:15px 0px 10px 0px ;  word-break:keep-all"
        >
          {{ deleteMemberEmail }} 님의 계정을
        </h2>
        <h2 style="font-size:20px;">영구적으로 삭제하시겠습니까?</h2>

        <p style="margin-top:30px">
          계정 삭제를 원하신다면, 당신의 이메일과 패스워드를 입력해주세요.
        </p>
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
        <v-btn text color="var(--main-color)" @click="deleteMember"
          >Submit</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import SERVER from "@/api/spring.js";
import axios from "axios";
export default {
  name: "DeleteUserByAdmin",
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
  methods: {
    deleteMember() {
      axios
        .post(SERVER.URL + SERVER.ROUTES.deleteMember, {
          adminemail: this.account.email,
          deleteemail: this.deleteMemberEmail,
          pw: this.account.pw
        })
        .then((res) => {
          if (res.data.result == "success") {
            alert("회원 계정을 성공적으로 삭제하였습니다.");
            this.dialog = false;
            this.$emit("delete-member-admin");
          } else if (res.data.result == "wrong_admin_pw") {
            this.deleteSuccess = false;
          } else if (res.data.result == "wrong_user_email") {
            alert(
              "삭제할 계정이 존재하지 않습니다. 새로고침 후, 다시 시도해주세요."
            );
          } else if (res.data.result == "wrong_admin_email") {
            alert(
              "현재 계정은 관리자 권한이 없습니다. 새로고침 후, 다시 시도해주세요."
            );
          }
        });
    }
  },
  props: {
    dialogDelete: Boolean,
    deleteMemberEmail: String
  },

  watch: {
    dialogDelete: function() {
      this.dialog = true;
    },
    dialog: function() {
      if (this.account.email != "" || this.account.pw != "") {
        this.$refs.form.reset();
        this.$refs.form.resetValidation();
      }
    }
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
  }
};
</script>

<style></style>
