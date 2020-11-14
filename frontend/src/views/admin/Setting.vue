<template>
  <div class="setting-container">
    <div class="setting-menu" style="width:15%;">
      <v-btn
        small
        fab
        dark
        @click="model = 0"
        :elevation="model == 0 ? '3' : '0'"
        color="var(--main-color)"
      >
        <v-icon size="40">
          mdi-numeric-1
        </v-icon>
      </v-btn>
      <hr :class="model != 0 ? 'setting-menu-click' : 'setting-menu-unclick'" />
      <v-btn
        small
        fab
        dark
        @click="model = 1"
        :elevation="model == 1 ? '3' : '0'"
        :color="model != 0 ? 'var(--main-color)' : 'grey'"
      >
        <v-icon size="40">
          mdi-numeric-2
        </v-icon>
      </v-btn>
      <hr
        :class="
          model != 0 && model != 1
            ? 'setting-menu-click'
            : 'setting-menu-unclick'
        "
      />
      <v-btn
        small
        fab
        dark
        :elevation="model == 2 ? '3' : '0'"
        :color="model == 2 || model == 3 ? 'var(--main-color)' : 'grey'"
      >
        <v-icon size="40">
          mdi-numeric-3
        </v-icon>
      </v-btn>
      <hr :class="model == 3 ? 'setting-menu-click' : 'setting-menu-unclick'" />
      <v-btn
        small
        fab
        dark
        :elevation="model == 3 ? '3' : '0'"
        :color="model == 3 ? 'var(--main-color)' : 'grey'"
      >
        <v-icon size="40">
          mdi-numeric-4
        </v-icon>
      </v-btn>
    </div>
    <div style="width:85%;">
      <v-carousel
        v-model="model"
        hide-delimiters
        :show-arrows="false"
        vertical
        width="100%"
        height="100%"
      >
        <v-carousel-item class="setting-carousel">
          <v-sheet color="transparent" height="100%" tile class="setting-sheet">
            <v-row class="fill-height" align="center">
              <div style="display:flex; width:100%; ">
                <div style="width:70%; color:var(--font-sub2-color)">
                  <h3 class="setting-sheet-step">Step 1</h3>
                  <h2 style="color:var(--font-main-color)">
                    관리자님, SSOMS를 방문해주셔서 감사합니다!
                  </h2>
                  <p style="font-size:18px; margin-top:10px">
                    현 화면은 관리자만 접근 가능한 페이지로, <br />Oracle
                    Database와 Member를 관리하실 수 있습니다.
                  </p>
                  <p style="margin-top:40px">
                    먼저, SSOMS 서비스 이용을 위해
                    <b>Oracle 연동</b>과 <b>Schema 설정</b>을 진행해주세요.
                  </p>
                  <v-btn
                    style="margin-top:15px;"
                    @click="model = 1"
                    color="black"
                    dark
                  >
                    <span style="color:white">NEXT</span>
                    <v-icon style="margin-left:10px;color:white" size="20"
                      >mdi-arrow-right</v-icon
                    ></v-btn
                  >
                </div>
                <div style="width:30%; ">
                  <img src="@/assets/SSOMS.png" alt="ssoms.png" />
                </div>
              </div>
            </v-row>
          </v-sheet>
        </v-carousel-item>

        <v-carousel-item class="setting-carousel">
          <v-sheet color="transparent" height="100%" tile class="setting-sheet">
            <v-row class="fill-height" align="center">
              <div
                style="display:flex; align-items:center; justify-content:center; width:100%;"
              >
                <div style="width:55%">
                  <h3 class="setting-sheet-step">Step 2</h3>
                  <OracleDB @oracle-save="oracleSave" />
                </div>
                <div
                  align="center"
                  style="width:45%; color:var(--font-sub2-color); padding:0px 10px 0px 90px"
                >
                  <v-icon
                    size="150"
                    color="var(--font-sub2-color)"
                    style="margin-bottom:20px"
                    >mdi-database-cog</v-icon
                  >
                  <div>
                    <h2 style="color:var(--font-main-color)">
                      SSOMS와 ORACLE DB 연결
                    </h2>
                    <h3 style="margin-top:20px; word-break:keep-all">
                      DB 모니터링을 위해 필수적으로 입력해야 하는 정보로 반드시
                      정확하게 기입해주세요!
                    </h3>
                    <p
                      style="margin-top:25px; font-size:16px; word-break:keep-all"
                    >
                      연결 테스트 후, 저장을 진행하시면 다음 페이지로
                      넘어갑니다.
                    </p>
                  </div>
                </div>
              </div>
            </v-row>
          </v-sheet>
        </v-carousel-item>
        <v-carousel-item>
          <v-sheet color="transparent" height="100%" tile class="setting-sheet">
            <v-row class="fill-height" align="center">
              <div
                style="display:flex; align-items:center; justify-content:center; width:100%;"
              >
                <div style="width:55%">
                  <h3 class="setting-sheet-step">Step 3</h3>
                  <Schema />
                </div>
                <div
                  align="center"
                  style="width:45%; color:var(--font-sub2-color); padding:0px 10px 0px 90px"
                >
                  <v-icon
                    size="170"
                    color="var(--font-sub2-color)"
                    style="margin-bottom:20px"
                    >mdi-monitor-dashboard</v-icon
                  >
                  <div>
                    <h2 style="color:var(--font-main-color)">
                      SCHEMA 설정
                    </h2>
                    <h3 style="margin-top:10px; word-break:keep-all">
                      모니터링하고자 하는 SCHEMA ID를 추가해주세요.
                    </h3>
                    <p
                      style="margin-top:25px; font-size:16px; word-break:keep-all"
                    >
                      반드시 하나 이상의 SCHEMA ID를 추가하신 후에 페이지로
                      넘어가 주세요.
                    </p>
                    <v-btn
                      style="margin-top:20px;"
                      @click="model = 3"
                      color="black"
                      dark
                    >
                      <span style="color:white">NEXT</span>
                      <v-icon style="margin-left:10px;color:white" size="20"
                        >mdi-arrow-right</v-icon
                      ></v-btn
                    >
                  </div>
                </div>
              </div>
            </v-row>
          </v-sheet>
        </v-carousel-item>
        <v-carousel-item class="setting-carousel">
          <v-sheet color="transparent" height="100%" tile class="setting-sheet">
            <v-row class="fill-height" align="center">
              <div style="diplay:flex; width:100%; ">
                <div style="width:65%; color:var(--font-sub2-color)">
                  <h3 class="setting-sheet-step">Step 4</h3>
                  <h2 style="color:var(--font-main-color)">
                    수고하셨습니다!
                  </h2>
                  <p style="font-size:18px; margin-top:10px">
                    모든 정보 입력을 완료하셨습니다.
                  </p>
                  <p style="margin-top:50px">
                    마지막으로, USER 계정으로 회원가입 및 로그인 진행 후 SSOMS를
                    이용해주세요.
                  </p>
                  <v-btn
                    style="margin-top:15px;"
                    @click="logout"
                    color="black"
                    dark
                  >
                    <span style="color:white">Login</span>
                    <v-icon style="margin-left:10px;color:white" size="20"
                      >mdi-arrow-right</v-icon
                    ></v-btn
                  >
                </div>
              </div>
            </v-row>
          </v-sheet>
        </v-carousel-item>
      </v-carousel>
    </div>
  </div>
</template>

<script>
import OracleDB from "@/views/admin/OracleDB";
import Schema from "@/views/admin/Schema";
import { mapActions } from "vuex";
export default {
  data() {
    return {
      colors: "transparent",
      model: 0
    };
  },
  components: {
    OracleDB,
    Schema
  },
  methods: {
    ...mapActions("Account", ["logout"]),
    oracleSave() {
      console.log("this.model dur>?Sd,AMf;sda,fgsdafgasdgf");
      this.model = 2;
    }
  }
};
</script>

<style>
.setting-container {
  width: 100%;
  height: 100%;
  display: flex;
  align-content: center;
}
.setting-menu {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  margin: 100px 0;
  margin-left: -40px;
}
.setting-sheet {
  padding-left: 50px;
}
/* .setting-sheet * {
  color: var(--font-main-color);
} */
.setting-sheet-step {
  color: var(--font-sub-color);
  margin-bottom: 15px;
}

.setting-menu-click {
  border: 2.5px solid rgb(60, 133, 216);
  background: var(--main-sub-color);
  height: 100%;
}
.setting-menu-unclick {
  border: 2.5px solid var(--font-sub-color);
  background: var(--font-sub-color);
  height: 100%;
}
.click-model {
  color: red;
  background: turquoise;
}

.setting-carousel .v-input * {
  color: var(--font-sub2-color) !important;
}
.setting-carousel input::placeholder {
  color: var(--font-sub2-color) !important;
}
.setting-carousel .v-input__slot:before {
  border-color: var(--font-sub-color) !important;
}
.setting-carousel .v-input__slot::after {
  border-color: #1976d2 !important;
}
</style>
