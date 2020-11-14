<template>
  <div class="member-container">
    <h2 class="mb-3">Member</h2>
    <div style="display:flex">
      <div style="width:75%;">
        회원 관리 및 접속 이력에 대한 조회가 가능합니다.
        <div class="member-searchbar">
          <v-text-field
            solo
            style="border-radius:20px"
            append-icon="mdi-magnify"
            label="검색할 회원명을 입력해주세요."
            v-model="findMemberName"
            @keyup="findMember"
          ></v-text-field>
        </div>
        <v-simple-table
          fixed-header
          height="100%"
          color="red"
          style="background:transparent; margin-right:40px"
        >
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">이름</th>
                <th class="text-left">사번</th>
                <th class="text-left">이메일</th>
                <th class="text-left">연락처</th>
                <th class="text-left">권한</th>
                <th class="text-left">접속이력</th>
              </tr>
            </thead>
            <tbody style="color:var(--font-sub2-color)">
              <tr
                v-for="member in findMemberList"
                :key="member.info.employeeId"
                @click="visitView(member)"
                style="cursor: pointer;"
              >
                <td class="cell1">{{ member.info.name }}</td>
                <td class="cell2">{{ member.info.employeeId }}</td>
                <td class="cell3">
                  <v-icon size="18" style="margin-right:5px">mdi-email </v-icon>
                  {{ member.email }}
                </td>
                <td class="cell4">
                  <v-icon size="18" style="margin-right:5px">mdi-phone</v-icon
                  >{{ member.info.phoneNumber }}
                </td>

                <td class="cell5" scope="row">
                  <div class="rule-admin" v-if="member.info.admin === true">
                    ADMIN
                  </div>
                  <div class="rule-user" v-if="member.info.admin === false">
                    USER
                  </div>
                </td>

                <td class="cell6">
                  <v-icon size="18" style="margin-right:5px">mdi-clock</v-icon
                  >{{ member.visit.time[member.visit.time.length - 1] }}
                </td>
                <!-- <td style="border-bottom:1px solid #d0d0d0;">
                <v-icon
                  style="margin-bottom:3px; "
                  size="22"
                  color="var(--font-sub2-color)"
                  >mdi-database</v-icon
                >
                {{ schema.userID }}
              </td>
              <td
                class="text-right"
                style="border-bottom:1px solid #d0d0d0;"
              ></td> -->
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </div>
      <transition name="fade">
        <v-list
          v-if="visitTarget"
          dense
          elevation="3"
          style="border-radius:5px;width:25%;height: 100%;"
        >
          <v-card-text>
            <div style="display:flex; justify-content:space-between">
              <h3>{{ visitTarget.info.name }} 님의 접속 이력</h3>
              <v-btn
                style="margin-top:-5px"
                small
                icon
                @click="visitTarget = false"
                ><v-icon>mdi-close</v-icon></v-btn
              >
            </div>
            <small style="color:var(--font-sub-color)">
              최근 10건의 접속 기록이 노출됩니다.
            </small>
            <v-divider style="margin-top: 10px"></v-divider>

            <v-timeline align-top dense>
              <v-timeline-item
                color="#7209b7"
                small
                v-for="(time, i) in visitTarget.visit.time.slice().reverse()"
                :key="i"
              >
                <v-row class="pt-1">
                  <div>{{ time }}</div>
                </v-row>
              </v-timeline-item>
            </v-timeline>
            <v-divider></v-divider>
          </v-card-text>
        </v-list>
      </transition>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring.js";

export default {
  name: "Member",
  data() {
    return {
      visitTarget: null,
      memberList: [],
      findMemberList: [],
      findMemberName: ""
    };
  },

  methods: {
    findMember() {
      this.findMemberList = [];
      if (this.findMemberName == "") this.findMemberList = this.memberList;
      else {
        this.memberList.forEach((member) => {
          if (member.info.name.includes(this.findMemberName)) {
            this.findMemberList.push(member);
          }
        });
      }
    },
    visitView(member) {
      this.visitTarget = member;
    },
    getAllUser() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.allUser)
        .then((res) => {
          this.memberList = res.data.map.userList;
          this.findMemberList = this.memberList;
        })
        .catch((err) => console.log(err));
    }
  },
  created() {
    this.getAllUser();
  }
};
</script>

<style>
.member-searchbar {
  width: 400px;
  margin-top: 25px;
}
thead th {
  background: #333333 !important;
  color: white !important;
}
td {
  border-bottom: 1px solid #d0d0d0;
}

.member-container .fade-enter-active,
.member-container .fade-leave-active {
  transition: opacity 0.5s;
}
.member-container .fade-enter,
.member-container .fade-leave-to {
  opacity: 0;
}

.visit-list :hover {
  background: rgb(214, 214, 214);
}
.rule-admin {
  background: rgb(116, 35, 209);
  color: white;
  width: 57px;
  height: 25px;
  border-radius: 12px;
  padding-left: 9px;
  padding-top: 4px;
  font-size: 12px;
}
.rule-user {
  background: rgb(74, 166, 241);
  color: white;
  width: 57px;
  height: 25px;
  border-radius: 12px;
  padding-left: 14px;
  padding-top: 3px;
  font-size: 12px;
}
</style>
