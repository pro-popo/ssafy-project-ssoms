<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="380px"
      height="850px"
      scrollable
    >
      <v-snackbar v-model="editSuccess" color="success">
        <div class="edit-snackbar">
          <v-icon>mdi-check-circle</v-icon>
          <span style="font-size: 16px; margin-left: 10px">
            Edit Profile Success !
          </span>
        </div>
        <template v-slot:action="{ attrs }">
          <v-btn color="white" text v-bind="attrs" @click="editSuccess = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>

      <v-card align="center">
        <v-card-title class="edit-headline">
          <span></span>
          <span style="margin-left: 30px; color: var(--font-sub2-color)"
            >Edit Profile</span
          >
          <v-btn icon @click="dialog = !dialog">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text class="edit-context" align="center">
          <div class="edit-form">
            <v-avatar class="edit-avatar" size="90">
              <v-icon color="#FFF" dark size="55"> mdi-account </v-icon>
            </v-avatar>
            <p class="user-email">{{ account.email }}</p>

            <v-form ref="form">
              <v-text-field
                v-model="account.name"
                prepend-icon="mdi-account"
                :rules="[rules.required]"
                label="Name"
                required
              ></v-text-field>
              <v-text-field
                class="pw-field"
                v-model="account.pw"
                prepend-icon="mdi-lock"
                :append-icon="
                  isChangePw ? 'mdi-close' : 'mdi-circle-edit-outline'
                "
                :rules="[rules.required, emailMatch]"
                :type="show1 ? 'text' : 'password'"
                name="input-10-1"
                label="Password"
                @click:append="isChangePw = !isChangePw"
              ></v-text-field>
              <transition name="fade">
                <div v-if="isChangePw">
                  <v-text-field
                    class="newpw-field"
                    v-model="newPw"
                    prepend-icon="mdi-lock-reset"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="[rules.required]"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="New Password"
                    @click:append="show1 = !show1"
                  ></v-text-field>

                  <v-text-field
                    class="newpw-field"
                    v-model="confirmPw"
                    prepend-icon="mdi-lock-reset"
                    :rules="[rules.required, pwConfirmRules]"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Confirm Password"
                    @click:append="show1 = !show1"
                  ></v-text-field>
                </div>
              </transition>
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
          </div>
          <v-btn
            @click="editUser"
            color="var(--main-color)"
            rounded
            dark
            class="edit-btn"
          >
            EDIT
          </v-btn>

          <p
            class="delete-text"
            @click="deleteDialog = !deleteDialog"
            v-if="!profile.admin"
          >
            Do you want to delete your account?
          </p>
        </v-card-text>
      </v-card>
    </v-dialog>
    <DeleteUser :deleteDialog="deleteDialog" />
  </v-row>
</template>
<script>
import SERVER from "@/api/spring.js";
import axios from "axios";
import DeleteUser from "@/components/account/DeleteUser.vue";
export default {
  name: "EditUser",
  components: {
    DeleteUser,
  },
  data() {
    return {
      dialog: true,
      deleteDialog: false,

      show1: false,
      account: this.profile,

      newPw: "",
      confirmPw: "",

      isDuplicateEmail: true,
      isChangePw: false,
      loginSuccess: true,
      editSuccess: false,

      rules: {
        required: (v) => !!v || "Required.",
        // min: (v) => v.length >= 8 || "Min 8 characters",
      },
    };
  },
  computed: {
    pwConfirmRules() {
      return this.newPw === this.confirmPw || "Password do not match.";
    },
    emailMatch() {
      return (
        this.loginSuccess || "The email and password you entered don't match"
      );
    },
  },

  watch: {
    dialog: function () {
      this.$emit("close-edit-profile");
    },
    isChangePw: function () {
      this.newPw = "";
      this.confirmPw = "";
    },
  },
  methods: {
    editUser() {
      this.loginSuccess = true;
      if (this.$refs.form.validate()) {
        if (this.isChangePw) {
          this.account.newPw = this.newPw;
        } else {
          this.account.newPw = null;
        }
        axios
          .put(SERVER.URL + SERVER.ROUTES.updateUser, this.account)
          .then((res) => {
            if (res.data.result == "success") {
              this.editSuccess = true;
              setTimeout(
                function () {
                  this.dialog = false;
                  this.$emit("edit-profile-success");
                }.bind(this),
                2000
              );
            } else if (res.data.result == "fail") {
              console.log("실패");
              this.loginSuccess = false;
            }
          })
          .catch((err) => console.log(err));
      }
    },
  },
  props: {
    profile: Object,
  },
};
</script>

<style>
.v-tab {
  justify-content: start !important;
}
.v-form {
  margin-top: 30px;
  width: 80% !important;
}
.edit-avatar {
  background: #acaeb0;
  box-shadow: 1.7px 2.5px 4px #7f7f81;
  border: 4px solid #fff;
  margin: 25px 0px 10px 0px;
  background: linear-gradient(
    to bottom right,
    var(--main-sub-color),
    var(--main-color)
  );
}
.user-email {
  font-size: 17px;
  font-weight: bold;
}
.delete-text {
  padding-bottom: 15px;
  margin-bottom: 0px !important;
  color: var(--font-sub2-color);
}
.delete-text:hover {
  color: var(--main-sub-color);
  cursor: pointer;
}
.edit-headline {
  justify-content: space-between;
  position: sticky;
  top: 0;
  height: 40px;
  padding: 8px 12px 40px 12px !important;
  border-bottom: 1px solid #d8d8d8;
  background: #fff;
  z-index: 2;
}

.edit-btn {
  width: 85%;
  margin: 15px 0px 10px 0px;
}

.edit-context {
  max-height: 600px;
  overflow-y: auto;
  -ms-overflow-style: none;
}
.edit-context::-webkit-scrollbar {
  display: none;
}

.mdi-circle-edit-outline:hover {
  color: var(--main-sub-color) !important;
}
.v-text-field .mdi-close:hover {
  color: #fd4e4e !important;
}

.newpw-field {
  margin-left: 35px;
}

.fade-enter-active {
  transition: opacity 0.5s;
}
.fade-leave-active {
  transition: opacity 0.1s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
.edit-snackbar {
  display: flex;
  align-items: center;
}
.v-dialog {
  overflow: hidden;
}
</style>
