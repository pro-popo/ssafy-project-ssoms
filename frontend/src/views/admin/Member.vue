<template>
  <div>
    <h2 class="mb-3">Member</h2>
    <div class="member-searchbar">
      <span class="input-group-text mdi mdi-magnify"></span>
      <v-text-field label="검색할 유저명을 입력해주세요."></v-text-field>
    </div>
    <v-container>
      <v-row>
        <v-col cols="8">
          <div class="tb_wrap">
            <div class="tb_box">
              <table class="tb table-bordered">
                <tr class="fixed_top">
                  <th class="cell1" scope="col">관리자</th>
                  <th class="cell2" scope="col">이름</th>
                  <th class="cell3" scope="col">사번</th>
                  <th class="cell4" scope="col">이메일</th>
                  <th class="cell5" scope="col">연락처</th>
                  <th class="cell6" scope="col">접속이력</th>
                </tr>
                <tr
                  class="table-cell"
                  v-for="member in memberList"
                  :key="member.info.employeeId"
                >
                  <th
                    v-if="member.info.admin === true"
                    class="cell1"
                    scope="row"
                  >
                    O
                  </th>
                  <th
                    v-if="member.info.admin === false"
                    class="cell1"
                    scope="row"
                  >
                    X
                  </th>
                  <td class="cell2">{{ member.info.name }}</td>
                  <td class="cell3">{{ member.info.employeeId }}</td>
                  <td class="cell4">{{ member.email }}</td>
                  <td class="cell5">{{ member.info.phoneNumber }}</td>
                  <td class="cell6">
                    {{ member.visit.time[member.visit.time.length - 1] }}
                    <v-btn small color="primary" text @click="visitView(member)"
                      >조회</v-btn
                    >
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </v-col>
        <v-col cols="4">
          <v-list v-if="visitTarget">
            <v-subheader
              >{{ visitTarget.info.name }} 님의 접속 이력</v-subheader
            >
            <v-list-item
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
        </v-col>
      </v-row>
    </v-container>
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
    },
  },
  created() {
    this.getAllUser();
  },
};
</script>

<style>
.member-searchbar {
  display: flex;
  line-height: 65px;
  width: 500px;
  margin-bottom: 25px;
}
tr:last-child {
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
  /* padding-left: 10px; */
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
}
</style>
