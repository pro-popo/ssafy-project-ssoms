<template>
  <div>
    <h2 class="mb-3">Oracle DB설정</h2>
    <form>
      <div class="input-group-oracle">
        <span class="setting-oracle-font">Oracle URL</span>
        <v-text-field
          label="연동할 Oracle URL을 입력해주세요."
          v-model="oracleData.oracleURL"
        ></v-text-field>
      </div>
      <div class="input-group-oracle">
        <span class="setting-password-font">Password</span>
        <v-text-field
          v-model="oracleData.oraclePassword"
          :type="'password'"
          name="input-10-1"
          label="Oracle Password를 입력해주세요."
        ></v-text-field>
      </div>
      <hr class="oracle-line" />
      <div class="setting-storage">
        <span class="setting-right" style="display:inline-block; width: 350px"
          >Oracle ID
          <v-text-field
            label="Oracle ID를 입력해주세요."
            v-model="oracleData.oracleID"
          ></v-text-field>
        </span>
        <span style="display:inline-block; width: 350px"
          >Oracle Edition
          <v-text-field
            label="오라클 버전을 입력해주세요."
            v-model="oracleData.oracleEdition"
          ></v-text-field>
        </span>
      </div>
      <div class="setting-storage">
        <span class="setting-right" style="display:inline-block; width: 350px"
          >데이터 갱신 주기
          <v-text-field label="아직 X"></v-text-field>
        </span>
        <span style="display:inline-block; width: 350px"
          >데이터 배치 시각
          <v-text-field label="아직 X"></v-text-field>
        </span>
      </div>
    </form>
    <v-btn
      color="primary"
      class="setting-oracle-save-button"
      @click="setSettingsOracleDB"
    >
      저장
    </v-btn>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring.js";

export default {
  name: "OracleDB",
  data() {
    return {
      oracleData: {
        oracleURL: "",
        oracleID: "",
        oraclePassword: "",
        oracleEdition: ""
      }
    };
  },
  methods: {
    setSettingsOracleDB() {
      axios
        .post(SERVER.URL + SERVER.ROUTES.setSettingsOracleDB, this.oracleData)
        .then(() => {
          console.log(this.oracleData);
          alert("정보를 저장하였습니다.");
        })
        .catch((err) => console.log(err));
    },
    getSettingsOracleDB() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getSettingsOracleDB)
        .then((res) => {
          this.oracleData.oracleURL = res.data.map.oracleDB.oracleURL;
          this.oracleData.oraclePassword = res.data.map.oracleDB.oraclePassword;
          this.oracleData.oracleID = res.data.map.oracleDB.oracleID;
          this.oracleData.oracleEdition = res.data.map.oracleDB.oracleEdition;
        })
        .catch((err) => console.log(err));
    }
  },
  created() {
    this.getSettingsOracleDB();
  }
};
</script>

<style>
.setting-oracle-font {
  font-size: 20px;
  margin-right: 30px;
}
.setting-password-font {
  font-size: 20px;
  margin-right: 40px;
}
.setting-storage {
  font-size: 20px;
  margin-top: 40px;
}
.setting-right {
  margin-right: 120px;
}
.setting-oracle-save-button {
  margin-top: 50px;
  margin-right: 50px;
  padding: 15px 20px !important;
  font-size: 15px !important;
  float: right;
}
.input-group-oracle {
  height: 60px;
  line-height: 60px;
  display: flex;
}
.oracle-line {
  margin-top: 25px;
}
</style>
