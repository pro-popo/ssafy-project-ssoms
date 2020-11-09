<template>
  <div>
    <h2 class="mb-3">Oracle DB설정</h2>
    <div class="zz"></div>
    <form>
      <div class="input-group-oracle">
        <span class="setting-oracle-font">Oracle URL</span>
        <v-text-field
          label="연동할 Oracle URL과 포트번호를 입력해주세요."
          placeholder="ex) OracleServerURL:8080"
          v-model="oracleData.oracleURL"
        ></v-text-field>
      </div>
      <div class="input-group-oracle">
        <span class="setting-password-font">Oracle SID</span>
        <v-text-field
          label="오라클 SID을 입력해주세요. SELECT instance FROM V$THREAD; 로 확인할 수 있습니다."
          v-model="oracleData.oracleSID"
        ></v-text-field>
      </div>
      <div class="setting-storage">
        <span class="setting-right" style="display:inline-block; width: 350px"
          >Oracle ID
          <v-text-field
            label="Oracle DB 접속 ID를 입력해주세요."
            v-model="oracleData.oracleID"
          ></v-text-field>
        </span>
        <span style="display:inline-block; width: 350px"
          >Password
          <v-text-field
            v-model="oracleData.oraclePassword"
            :type="'password'"
            name="input-10-1"
            label="접속 ID의 Password를 입력해주세요."
          ></v-text-field>
        </span>
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
      <hr class="oracle-line" />
    </form>
    <v-btn
      color="primary"
      class="setting-oracle-save-button1"
      @click="checkConOracleDB"
    >
      연결 테스트
    </v-btn>
    <v-btn
      color="primary"
      class="setting-oracle-save-button2"
      @click="setSettingsOracleDB"
      :disabled="saveDisable"
    >
      설정사항 저장
    </v-btn>
    <v-divider style="margin : 100px 0px 30px 0px"></v-divider>
    <h2 style="margin-bottom:10px">실시간 모니터링</h2>
    <v-btn color="success" v-if="scheduler" @click="connectScheduler"
      >실시간 모니터링 시작</v-btn
    >

    <v-btn color="error" v-if="!scheduler" @click="disconnectScheduler"
      >실시간 모니터링 종료</v-btn
    >
    <v-btn
      color="primary"
      style="margin-left:10px"
      @click="checkConnectScheduler"
      >실시간 모니터링 상태 확인</v-btn
    >
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
      testString: "",
      scheduler: true
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
    // 실시간 모니터링 (나중에 삭제 예정)
    connectScheduler() {
      console.log("---스케줄러 연결 중---");
      axios
        .get(SERVER.URL + SERVER.ROUTES.scheduleStart)
        .then((res) => {
          if (res.data.result == "success") {
            alert("실시간 모니터링이 시작 되었습니다 !!");
            this.scheduler = false;
          } else if (res.data.result == "fail") {
            alert(
              "실시간 모니터링 연결에 문제가 생겼습니다... 관리자에게 연락 부탁드립니다..."
            );
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkConnectScheduler() {
      console.log("---스케줄러 연결 확인 중---");
      axios
        .get(SERVER.URL + SERVER.ROUTES.scheduleStatus)
        .then((res) => {
          if (res.data.result == "running") {
            alert("현재 실시간 모니터링이 시작된 상태 입니다 !!");
          } else if (res.data.result == "end") {
            alert("현재 실시간 모니터링이 종료된 상태 입니다.");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    disconnectScheduler() {
      console.log("---스케줄러 연결 해제 중---");

      axios
        .get(SERVER.URL + SERVER.ROUTES.scheduleStop)
        .then((res) => {
          if (res.data.result == "success") {
            alert("실시간 모니터링이 성공적으로 종료 되었습니다 !!");
            this.scheduler = true;
          } else if (res.data.result == "fail") {
            alert(
              "실시간 모니터링 종료에 문제가 생겼습니다... 관리자에게 연락 부탁드립니다..."
            );
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },

    setSettingsOracleDB() {
      axios
        .post(SERVER.URL + SERVER.ROUTES.setSettingsOracleDB, this.oracleData)
        .then((res) => {
          if (res.data.result === "success") {
            alert("저장에 성공하였습니다.");
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
