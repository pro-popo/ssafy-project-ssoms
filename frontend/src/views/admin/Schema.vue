<template>
  <div style="max-width:800px">
    <h2 class="mb-3" style="color:var(--font-main-color)">Schema Setting</h2>
    <h4 style="color:var(--font-sub2-color); ">
      <v-icon color="var(--font-sub2-color)">mdi-database</v-icon> Schema
    </h4>
    <div class="schema-searchbar">
      <!-- <span class="mdi mdi-magnify"></span>
      <div style="width:50%">
        <v-text-field label="검색할 스키마명을 입력해주세요."></v-text-field>
      </div> -->

      <v-tooltip top>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            style="margin-left:auto; margin-right:10px"
            fab
            small
            elevation="2"
            color="var(--font-sub2-color)"
            v-bind="attrs"
            v-on="on"
            dark
            @click="isCheckAdd = !isCheckAdd"
            id="add-schema-btn"
            ><v-icon v-if="!isCheckAdd">mdi-plus</v-icon>
            <v-icon v-if="isCheckAdd">mdi-close</v-icon></v-btn
          >
        </template>
        <span>Add Schema</span>
      </v-tooltip>
    </div>
    <transition name="slide-fade">
      <v-card class="add-schema" v-if="isCheckAdd" dark>
        <h3>Add Schema</h3>
        <div class="add-schema-form">
          <v-text-field
            prepend-icon="mdi-database-plus"
            label="스키마ID를 입력해주세요."
            v-model="userID"
            @keypress.enter="saveSchema"
          ></v-text-field>
          <v-btn
            style="margin-left:20px"
            color="primary"
            class="setting-schema-save-button"
            @click="saveSchema"
          >
            SAVE
          </v-btn>
        </div>
      </v-card>
    </transition>

    <div>
      <v-simple-table
        fixed-header
        height="290px"
        color="red"
        dark
        style="background:transparent"
      >
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left" style="padding-left:20px">Schema ID</th>
              <th class="text-right" style="padding-right:25px">
                Action
              </th>
            </tr>
          </thead>
          <tbody style="color:var(--font-sub2-color)">
            <tr v-for="schema in schemaList" :key="schema.userID">
              <td style="border-bottom:1px solid #d0d0d0;">
                <v-icon
                  style="margin-bottom:3px; "
                  size="22"
                  color="var(--font-sub2-color)"
                  >mdi-database</v-icon
                >
                {{ schema.userID }}
              </td>
              <td class="text-right" style="border-bottom:1px solid #d0d0d0;">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      small
                      elevation="1"
                      color="error"
                      v-bind="attrs"
                      v-on="on"
                      @click="deleteSchema(schema.userID)"
                      ><v-icon>mdi-delete</v-icon></v-btn
                    >
                  </template>
                  <span>Delete Schema</span>
                </v-tooltip>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring.js";

export default {
  name: "Schema",
  data() {
    return {
      schemaList: [],
      userID: "",
      isCheckAdd: false
    };
  },
  methods: {
    deleteSchema(schemaId) {
      const itemToFind = this.schemaList.find(function(item) {
        return item.userID === schemaId;
      });
      const idx = this.schemaList.indexOf(itemToFind);
      if (idx > -1) {
        this.schemaList.splice(idx, 1);
      }
      axios
        .post(SERVER.URL + SERVER.ROUTES.setSettingsSchema, this.schemaList)
        .then((res) => {
          console.log(res.data.result);
          if (res.data.result === "saveSuccess") {
            alert("스키마가 성공적으로 삭제되었습니다.");
          }
          this.userID = "";
        })
        .catch((err) => console.log(err));
    },
    getSettingsSchema() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getSettingsSchema)
        .then((res) => {
          this.schemaList = res.data.map.schema;
        })
        .catch((err) => console.log(err));
    },
    saveSchema() {
      // 중복 및 존재여부 확인
      axios
        .post(SERVER.URL + SERVER.ROUTES.checkSettingsSchema, {
          userID: this.userID
        })
        .then((res) => {
          console.log("1", res.data.result);
          if (res.data.result === "duplicate") {
            alert("이미 존재하는 스키마 ID 입니다.");
          } else if (res.data.result === "notExist") {
            alert("DB에 존재하지 않는 스키마 ID 입니다.");
          } else if (res.data.result === "success") {
            // 조건 만족 후, 스키마 저장
            this.schemaList.push({ userID: this.userID });
            axios
              .post(
                SERVER.URL + SERVER.ROUTES.setSettingsSchema,
                this.schemaList
              )
              .then((res) => {
                console.log(res.data.result);
                if (res.data.result === "saveSuccess") {
                  alert("스키마를 성공적으로 추가하였습니다.");
                }
                this.userID = "";
              })
              .catch(() => console.log("실패"));
          }
        })
        .catch((err) => console.log(err));
    }
  },
  created() {
    this.getSettingsSchema();
  }
};
</script>

<style>
.schema-searchbar {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.add-schema-form {
  display: flex;
  align-items: center;
}
.add-schema {
  border: 1px solid var(--font-sub-color);
  border-radius: 5px;
  padding: 15px 20px;
  margin: 10px 0px;
  box-shadow: 1 1 1 gray;
}

.setting-schema {
  max-width: 700px;
  border: 1px solid #adadad;
}
#add-schema-btn:hover {
  background: var(--main-color) !important;
}
.slide-fade-enter-active {
  transition: all 0.3s ease;
}
.slide-fade-leave-active {
  transition: all 0.3s ease;
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateY(-20px);
  opacity: 0;
}
tr:hover {
  background: rgb(219, 219, 219) !important;
}
table {
  border-left: 1px solid #d0d0d0 !important;
  border-right: 1px solid #d0d0d0 !important;
}
</style>
