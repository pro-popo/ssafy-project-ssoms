<template>
  <v-dialog v-model="dialog" max-width="550px">
    <v-card>
      <v-card-title class="delete-headline">
        <span></span>
        <v-btn icon @click="dialog = !dialog">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text style="padding: 20px 30px">
        <v-icon size="40">mdi-account-convert</v-icon>
        <h2
          style="font-size:20px; padding:15px 0px 10px 0px ;  word-break:keep-all"
        >
          {{ transferAuthorityMember }} 님에게
        </h2>
        <h2 style="font-size:20px;">관리자 권한을 양도하시겠습니까?</h2>
        <h3 style="margin-top:40px; font-size:16px; word-break:keep-all">
          현재의 계정은 USER 권한으로 자동 변환되며, 완료 후 로그인 페이지로
          이동합니다.
        </h3>
        <p style="margin-top:10px">
          권한 양도를 원하신다면,
          <span style="color:var(--main-color)"><b>NEXT</b></span> 버튼을
          클릭해주세요.
        </p>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text color="var(--main-color)" @click="changeAdmin"
          >NEXT
          <v-icon style="margin-left:10px;color:var(--main-color)" size="20"
            >mdi-arrow-right</v-icon
          ></v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import SERVER from "@/api/spring.js";
import axios from "axios";
import { mapActions } from "vuex";
export default {
  name: "TransferAuthority",
  data() {
    return {
      dialog: false
    };
  },
  methods: {
    ...mapActions("Account", ["logout"]),
    changeAdmin() {
      axios
        .put(SERVER.URL + SERVER.ROUTES.changeAdmin, {
          prevAdmin: sessionStorage.getItem("loginSession"),
          nextAdmin: this.transferAuthorityMember
        })
        .then((res) => {
          if (res.data.result == "success") {
            alert("관리자 권한이 성공적으로 변경되었습니다.");
            this.logout();
          } else if (res.data.result == "fail") {
            alert(
              "선택하신 계정이 존재하지 않습니다. 새로고침 후, 다시 시도해주세요."
            );
          }
        })
        .catch((err) => {
          if (err.status == 200)
            alert(
              "현재 계정은 관리자 권한이 없습니다. 새로고침 후, 다시 시도해주세요."
            );
        });
    }
  },
  props: {
    dialogTransfer: Boolean,
    transferAuthorityMember: String
  },

  watch: {
    dialogTransfer: function() {
      this.dialog = true;
    }
  }
};
</script>

<style></style>
