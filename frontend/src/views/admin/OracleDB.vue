<template>
  <div>
    <h2 class="mb-3">Oracle DB설정</h2>
    <v-btn color="error" @click="test">TEST</v-btn>
    <div class="zz">
      {{ testString }}
    </div>
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
          >Oracle SID
          <v-text-field
            label="오라클 SID을 입력해주세요."
            v-model="oracleData.oracleSID"
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
      class="setting-oracle-save-button1"
      @click="checkConOracleDB"
    >
      연결
    </v-btn>
    <v-btn
      color="primary"
      class="setting-oracle-save-button2"
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
        oracleSID: ""
      },
      testString: ""
    };
  },
  methods: {
    setSettingsOracleDB() {
      axios
        .post(SERVER.URL + SERVER.ROUTES.setSettingsOracleDB, this.oracleData)
        .then((res) => {
          if (res.data.result === "success") {
            alert("저장에 성공하였습니다.");
          } else if (res.data.result === "fail") {
            alert("저장에 실패하였습니다.");
          }
        })
        .catch((err) => console.log(err));
    },
    getSettingsOracleDB() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getSettingsOracleDB)
        .then((res) => {
          console.log(res.data.map);
          this.oracleData.oracleURL = res.data.map.oracleDB.oracleURL;
          this.oracleData.oraclePassword = res.data.map.oracleDB.oraclePassword;
          this.oracleData.oracleID = res.data.map.oracleDB.oracleID;
          this.oracleData.oracleSID = res.data.map.oracleDB.oracleSID;
        })
        .catch((err) => console.log(err));
    },
    checkConOracleDB() {
      axios
        .post(SERVER.URL + SERVER.ROUTES.checkConOracleDB, this.oracleData)
        .then((res) => {
          if (res.data.result === "success") {
            alert("연결에 성공하였습니다.");
          } else if (res.data.result === "fail") {
            alert("연결에 실패하였습니다.");
          }
        })
        .catch((err) => console.log(err));
    },
    test() {
      axios
        .get(SERVER.URL + "/admin/test")
        .then((res) => (this.testString = res));
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
.setting-oracle-save-button1 {
  margin-top: 50px;
  margin-right: 50px;
  padding: 15px 20px !important;
  font-size: 15px !important;
  float: right;
}
.setting-oracle-save-button2 {
  margin-top: 50px;
  margin-right: 10px;
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
