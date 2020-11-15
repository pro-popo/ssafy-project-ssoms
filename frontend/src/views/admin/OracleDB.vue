<template>
  <div class="oracle-setting-container">
    <h2 class="mb-3">Oracle DB Setting</h2>
    <form>
      <div style="width:100%; max-width:800px">
        <div>
          <h4 style="color:var(--font-sub2-color);  margin-bottom:10px">
            <v-icon color="var(--font-sub2-color)">mdi-dns</v-icon> Oracle DB
          </h4>
          <v-card class="oracle-setting-card" elevation="1">
            <div class="input-group-oracle">
              <span class="setting-oracle-font">Oracle URL</span>
              <v-text-field
                color="primary"
                style="color:primary"
                label="연동할 Oracle URL과 포트번호를 입력해주세요."
                placeholder="ex) OracleServerURL:8080"
                v-model="oracleData.oracleURL"
              ></v-text-field>
            </div>
            <div class="input-group-oracle">
              <span class="setting-oracle-font">Oracle SID</span>
              <v-text-field
                color="primary"
                label="Oracle SID을 입력해주세요."
                placeholder="ex) XE"
                hint="SELECT instance FROM V$THREAD; 로 확인 가능합니다."
                v-model="oracleData.oracleSID"
              ></v-text-field>
            </div>
          </v-card>
        </div>
        <div style="margin-top:30px">
          <h4 style="color:var(--font-sub2-color); margin-bottom:10px">
            <v-icon color="var(--font-sub2-color)">mdi-account</v-icon> Oracle
            DB 계정
          </h4>
          <v-card class="oracle-setting-card" outlined elevation="1">
            <div class="input-group-oracle">
              <span class="setting-oracle-font">Oracle ID</span>
              <v-text-field
                color="primary"
                label="Oracle DB에 접속할 계정 ID를 입력해주세요."
                placeholder="ex) sys as sysdba"
                v-model="oracleData.oracleID"
              ></v-text-field>
            </div>
            <div class="input-group-oracle">
              <span class="setting-oracle-font">Password</span>
              <v-text-field
                color="primary"
                v-model="oracleData.oraclePassword"
                :type="'password'"
                name="input-10-1"
                label="Oracle DB에 접속할 계정의 Password를 입력해주세요."
              ></v-text-field>
            </div>
          </v-card>
        </div>
        <!-- <div class="setting-storage">
        <span class="setting-right" style="display:inline-block; width: 350px"
          >데이터 갱신 주기
          <v-text-field label="아직 X"></v-text-field>
        </span>
        <span style="display:inline-block; width: 350px"
          >데이터 배치 시각
          <v-text-field label="아직 X"></v-text-field>
        </span>
      </div> -->
        <div style="margin-top:30px; " align="right">
          <v-btn
            color="success"
            @click="setSettingsOracleDB"
            :disabled="saveDisable"
            style="margin-right:10px"
            >설정사항 저장
          </v-btn>
          <v-btn color="primary" @click="checkConOracleDB">
            연결 테스트
          </v-btn>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring.js";
export default {
  name: "OracleDB",
  data() {
    return {
      saveDisable: true,
      oracleData: {
        oracleURL: "",
        oracleID: "",
        oraclePassword: "",
        oracleSID: ""
      },
      testString: ""
    };
  },
  watch: {
    oracleData: {
      deep: true,
      handler() {
        this.saveDisable = true;
      }
    }
  },

  methods: {
    setSettingsOracleDB() {
      axios
        .post(SERVER.URL + SERVER.ROUTES.setSettingsOracleDB, this.oracleData)
        .then((res) => {
          if (res.data.result === "success") {
            alert("저장에 성공하였습니다.");
            this.$emit("oracle-save");
            this.saveDisable = true;
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
          this.oracleData.oracleURL = res.data.map.oracleDB.oracleURL;
          this.oracleData.oraclePassword = res.data.map.oracleDB.oraclePassword;
          this.oracleData.oracleID = res.data.map.oracleDB.oracleID;
          this.oracleData.oracleSID = res.data.map.oracleDB.oracleSID;
        })
        .catch((err) => console.log(err));
    },
    checkConOracleDB() {
      var { oracleURL, oracleSID, oracleID, oraclePassword } = this.oracleData;
      if (oracleURL === "") {
        alert("오라클 URL을 입력해주세요");
      } else if (oracleSID === "") {
        alert("오라클 SID를 입력해주세요");
      } else if (oracleID === "") {
        alert("오라클 접속 ID를 입력해주세요");
      } else if (oraclePassword === "") {
        alert("오라클 접속 비밀번호를 입력해주세요");
      } else {
        axios
          .post(SERVER.URL + SERVER.ROUTES.checkConOracleDB, this.oracleData)
          .then((res) => {
            if (res.data.result === "success") {
              alert("연결에 성공하였습니다.");
              this.saveDisable = false;
            } else if (res.data.result === "fail") {
              alert("연결에 실패하였습니다.");
            }
          })
          .catch((err) => console.log(err));
      }
    }
  },
  created() {
    this.getSettingsOracleDB();
  }
};
</script>

<style>
.setting-oracle-font {
  /* margin-right: 15px; */
  width: 95px;
}

.oracle-setting-container {
  height: 100%;
  width: 100%;
  color: var(--font-main-color);
}

.oracle-setting-card {
  width: 100%;
  padding: 20px;
  background: transparent !important;
  border: 1px solid rgb(204, 204, 204) !important;
}

.input-group-oracle {
  display: flex;
  align-items: center;
  width: 90%;
  /* line-height: 60px; */
}
.input-group-oracle span {
  color: var(--font-sub2-color);
}
</style>
