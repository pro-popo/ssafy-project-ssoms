<template>
  <div class="realtime-setting">
    <v-btn color="success" outlined v-if="scheduler" @click="connectScheduler"
      >실시간 모니터링 시작하기</v-btn
    >

    <v-btn color="error" outlined v-if="!scheduler" @click="disconnectScheduler"
      >실시간 모니터링 끝내기</v-btn
    >
    <v-btn
      color="primary"
      style="margin-top: 10px"
      outlined
      @click="checkConnectScheduler"
      >실시간 모니터링 상태 확인</v-btn
    >
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring.js";
export default {
  data() {
    return {
      scheduler: true,
    };
  },
  created() {
    axios
      .get(SERVER.URL + SERVER.ROUTES.scheduleStatus)
      .then((res) => {
        if (res.data.result == "running") {
          this.scheduler = false;
        } else if (res.data.result == "end") {
          this.scheduler = true;
        }
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    // 실시간 모니터링 (나중에 삭제 예정)
    connectScheduler() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.scheduleStart)
        .then((res) => {
          if (res.data.result == "success") {
            alert("실시간 모니터링이 시작 되었습니다.");
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
      axios
        .get(SERVER.URL + SERVER.ROUTES.scheduleStatus)
        .then((res) => {
          if (res.data.result == "running") {
            alert("현재 실시간 모니터링이 시작된 상태 입니다.");
          } else if (res.data.result == "end") {
            alert("현재 실시간 모니터링이 종료된 상태 입니다.");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    disconnectScheduler() {
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
  },
};
</script>

<style>
.realtime-setting {
  display: flex;
  flex-direction: column;
}
.realtime-setting .v-btn {
  font-size: 12px;
  font-weight: bold;
}
</style>
