<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" max-width="320">
      <v-card>
        <v-card-title class="menu-container">
          <v-btn icon @click="dialog = false">
            <v-icon size="23" color="white">mdi-arrow-left</v-icon>
          </v-btn>
          <span style="color: white">Profile</span>
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-btn icon v-bind="attrs" v-on="on" @click="editUser">
                <v-icon color="white" size="23">mdi-cog</v-icon>
              </v-btn>
            </template>
            <span>EDIT</span>
          </v-tooltip>
        </v-card-title>
        <div class="profile-content">
          <div class="profile-avatar">
            <v-avatar class="avatar" size="90">
              <v-icon color="#FFF" dark size="55"> mdi-account </v-icon>
            </v-avatar>
          </div>
          <h4 style="margin-top: 50px; color: var(--font-main-color)">
            {{ profile.name }}
          </h4>
        </div>
        <div class="profile-text">
          <p>
            <v-icon size="20" color="var(--main-sub-color)"
              >mdi-clipboard-account</v-icon
            >
            <span>{{ profile.employeeId }}</span>
          </p>
          <p>
            <v-icon size="20" color="var(--main-sub-color)">mdi-email</v-icon>
            <span>{{ profile.email }}</span>
          </p>

          <p>
            <v-icon size="20" color="var(--main-sub-color)">mdi-phone</v-icon>
            <span>{{ profile.phoneNumber }}</span>
          </p>
        </div>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring.js";
export default {
  name: "MyProfile",
  data() {
    return {
      dialog: true,
      profile: {
        admin: false,
        email: "",
        employeeId: "",
        name: "",
        phoneNumber: "",
      },
    };
  },
  watch: {
    dialog: function () {
      this.$emit("close-profile");
    },
  },
  created() {
    axios
      .get(
        SERVER.URL +
          SERVER.ROUTES.getMyProfile +
          sessionStorage.getItem("loginSession")
      )
      .then((res) => {
        this.profile = res.data.map.userProfile.info;
        this.profile.email = res.data.map.userProfile.email;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    editUser() {
      this.dialog = false;
      this.$emit("edit-profile", this.profile);
    },
  },
};
</script>

<style>
.profile-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
}
.profile-avatar {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
  border-radius: 0px 0px 20% 20%;
  background: #bec8d4;
  background: -webkit-linear-gradient(
    to bottom,
    var(--main-color),
    var(--main-point-color)
  );
  background: linear-gradient(
    to bottom,
    var(--main-color),
    var(--main-point-color)
  );
}
.profile-avatar p {
  margin-bottom: 2px;
}
.menu-container {
  justify-content: space-between;
  background: var(--main-color);
  height: 50px;
  padding: 15px 12px 15px 12px !important;
}
.avatar {
  background: #acaeb0;
  top: 40px;
  box-shadow: 1.7px 2.5px 4px #7f7f81;
  border: 4px solid #fff;
}
.profile-text {
  padding: 25px 15px 20px 20px;
}
.profile-text p {
  margin-bottom: 12px;
}
.profile-text span {
  margin-left: 10px;
  color: var(--font-sub2-color);
}
</style>
