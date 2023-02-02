<template>
  
  <div id="app">
    <button @click="enterChannel">
      채널 입력
    </button>

    <div v-for="(item, idx) in channelList" :key="`o-${idx}`">
      <h3>채널 id: {{ item.id }}</h3>
    </div>

    유저 이름:
    <input v-model="userId" type="text" />
    트레이너 이름:
    <input v-model="trainerId" type="text" />
    내용: <input v-model="message" type="text" @keyup="sendMessage" />
    <div v-for="(item, idx) in recvList" :key="idx">
      <h3>유저이름: {{ item.userId }}</h3>
      <h3>트레이너이름: {{ item.trainerId }}</h3>
      <h3>내용: {{ item.content }}</h3>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "App",
  data() {
    return {
      userId: "",
      trainerId: "",
      message: "",
      recvList: [],
      channelList : [],
      channelId: "",
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    // this.channelId = prompt("입장하려는 채널번호를 입력하세요");
    this.connect_to_channelList();
  },
  methods: {
    enterChannel(){
      this.channelId = prompt("입장하려는 채널번호를 입력하세요");
      this.connect_to_room();
    },
    disconnectChannelListSocket(){
      this.stompClient.disconnect();
    },
    sendMessage(e) {
      if (e.keyCode === 13 && this.userName !== "" && this.message !== "") {
        this.send();
        this.message = "";
      }
    },
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          channelId: this.channelId,
          userId: this.userId,
          trainerId: this.trainerId,
          content: this.message,
        };
        this.stompClient.send("/chat/receive", JSON.stringify(msg), {});
      }
    },
    connect_to_room() {
      const serverURL = "http://localhost:8005";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send" + "/" + this.channelId, (res) => {
            console.log("구독으로 받은 메시지 입니다.", res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    connect_to_channelList() {
      const serverURL = "http://localhost:8005";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓(채널 목록) 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓(채널 목록) 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send/roomList", (res) => {
            console.log("(채널 목록)구독으로 받은 메시지 입니다.", res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.channelList.push(JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓(채널 목록) 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
};
</script>
