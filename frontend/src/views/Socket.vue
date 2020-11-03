<template>
  <div id="app">
    <!-- <div
      v-for="(item, idx) in recvList"
      :key="idx"
    >
      <h3>{{ item }}</h3>
    </div> -->
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import SERVER from "@/api/spring.js";
import axios from "axios";

export default {
  name: "Socket",
  data() {
    return {
      userName: "",
      message: "",
      recvList: []
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect();
    axios
      .get(SERVER.URL + "/Socket/test")
      .then((res) => {
        console.log(res);
      })
      .catch((err) => console.log(err));
  },
  methods: {
    connect() {
      const serverURL = "http://localhost:8080/ssoms/stomp";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          //  this.stompClient.subscribe("/sendData", res => {
          //    console.log('구독으로 받은 메시지 입니다.', res.body);
          //    // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
          //    this.recvList.push(JSON.parse(res.body))
          //  });

          // subscribe message
          this.stompClient.subscribe("/sendData/schedulerM", (res) => {
            this.recvList.push(JSON.parse(res.body));
            console.log("===============================");
            console.log(this.recvList[0].AllSchemaStastics);
            console.log("===============================");
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    }
  }
};
</script>
