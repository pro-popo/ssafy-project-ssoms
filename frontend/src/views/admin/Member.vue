<template>
  <div>
    <h2 class="mb-3">Member</h2>
    <!-- <div class="member-searchbar">
      <span class="input-group-text mdi mdi-magnify"></span>
      <v-text-field label="검색할 유저명을 입력해주세요."></v-text-field>
    </div> -->
    <div style="display:flex">
      <v-simple-table
        fixed-header
        height="400px"
        color="red"
        style="background:transparent;width:75%; margin-right:20px"
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
              v-for="member in memberList"
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
                {{ member.visit.time[member.visit.time.length - 1] }}
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
      <transition name="fade">
        <v-list
          v-if="visitTarget"
          dense
          elevation="3"
          style="border-radius:5px"
        >
          <v-subheader>{{ visitTarget.info.name }} 님의 접속 이력</v-subheader>
          <v-divider></v-divider>
          <v-list-item
            class="visit-list"
            v-for="(time, i) in visitTarget.visit.time.slice().reverse()"
            :key="i"
          >
            <v-list-item-content>
              <v-list-item-title v-text="time"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item>
            <small>
              최근 10건의 접속 기록이 노출됩니다.
            </small>
          </v-list-item>
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
      memberList: []
    };
  },
  methods: {
    visitView(member) {
      this.visitTarget = member;
    },
    getAllUser() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.allUser)
        .then((res) => {
          console.log(res.data.map.userList);
          this.memberList = res.data.map.userList;
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
  display: flex;
  line-height: 65px;
  width: 500px;
  margin-bottom: 25px;
}
thead th {
  background: #333333 !important;
  color: white !important;
}
td {
  border-bottom: 1px solid #d0d0d0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
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
/* tr:last-child {
  border-bottom: 0;
}
th,
td {
  height: 35px;
  font: 500 15px "spoqa han sans";
}

.tb_wrap {
  position: relative;
  padding-top: 40px;
}
.tb_box {
  max-height: 500px;
  overflow-y: scroll;
  overflow-x: hidden;
  border-bottom: 1px solid #dedede;
}
.tb {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
}
.cell1,
.cell2 {
  width: 10%;
}
.cell3 {
  width: 15%;
}
.cell4,
.cell5,
.cell6 {
  width: 20%;
}

.sub_fixed_top {
  display: inline-table;
  width: calc(100% - 17px);
  background: #eef7ff;
  text-align: center;
}

.fixed_top {
  display: inline-table;
  position: absolute;
  top: 0;
  width: calc(100% - 17px);
  background: #eef7ff;
  text-align: center;
}
.fixed_top th {
  border-top: 1px solid #dedede;
  border-bottom: 1px solid #dedede;
}

.table-cell {
  text-align: center;
} */
</style>
