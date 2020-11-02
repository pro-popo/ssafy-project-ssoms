<template>
  <div>
    <h2 class="mb-3">Member</h2>
    <div class="member-searchbar">
      <span class="input-group-text mdi mdi-magnify"></span>
      <v-text-field label="검색할 유저명을 입력해주세요."></v-text-field>
    </div>
    <div class="tb_wrap">
      <div class="tb_box">
        <table class="tb table-bordered">
          <tr class="fixed_top">
            <th class="cell1" scope="col">관리자</th>
            <th class="cell2" scope="col">이름</th>
            <th class="cell3" scope="col">사번</th>
            <th class="cell4" scope="col">이메일</th>
            <th class="cell5" scope="col">연락처</th>
          </tr>
          <tr
            class="table-cell"
            v-for="member in memberList"
            :key="member.employeeId"
          >
            <th v-if="member.admin === true" class="cell1" scope="row">
              O
            </th>
            <th v-if="member.admin === false" class="cell1" scope="row">
              X
            </th>
            <td class="cell2">{{ member.name }}</td>
            <td class="cell3">{{ member.employeeId }}</td>
            <td class="cell4">{{ member.email }}</td>
            <td class="cell5">{{ member.phoneNumber }}</td>
          </tr>
        </table>
      </div>
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
      memberList: []
    };
  },
  methods: {
    getAllUser() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.allUser)
        .then((res) => {
          this.memberList = res.data.map.userList;
          console.log(this.memberList);
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
  width: 900px;
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
  width: 20%;
}
.cell4,
.cell5 {
  width: 30%;
}

th.cell1,
td.cell2,
td.cell3,
td.cell4,
td.cell5 {
  padding-left: 10px;
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
