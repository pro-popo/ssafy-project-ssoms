<template>
  <div>
    <h2 class="mb-3">Schema 설정</h2>
    <div class="schema-searchbar">
      <span class="mdi mdi-magnify"></span>
      <v-text-field label="검색할 스키마명을 입력해주세요."></v-text-field>
    </div>
    <div class="schema-box">
      <div
        class="schema-list-admin"
        v-for="schema in schemaList"
        :key="schema.userID"
      >
        <ol>
          {{
            schema.userID
          }}
          <button @click="deleteSchema(schema.userID)">삭제</button>
        </ol>
        <hr />
      </div>
    </div>
    <div class="mt-7">
      <span style="font-size:25px; color:gray;">Schema |</span>
    </div>
    <div class="setting-schema-data">
      <span class="setting-schemaID-font">ID</span>
      <v-text-field
        label="스키마ID를 입력해주세요."
        v-model="userID"
      ></v-text-field>
    </div>
    <v-btn
      color="primary"
      class="setting-schema-save-button"
      @click="saveSchema"
    >
      저장
    </v-btn>
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
      userID: ""
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
            alert("중복된 스키마 ID 입니다.");
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
.setting-schema-data {
  width: 700px;
  display: flex;
  line-height: 65px;
}
.schema-searchbar {
  display: flex;
  width: 500px;
  line-height: 65px;
  margin-bottom: 25px;
}
.schema-list-admin {
  font-size: 15px;
}
.schema-list-admin ol {
  padding-top: 7px;
  margin-bottom: 7px;
}
.schema-list-admin > ol > button {
  float: right;
  margin-right: 10px;
}
.schema-box {
  overflow: scroll;
  overflow-x: hidden;
  width: 800px;
  height: 200px;
  border: 2px solid gray;
}
.schema-box hr {
  margin: 0px;
}
.setting-schemaID-font {
  font-size: 20px;
  margin-right: 20px;
}
.setting-schemaPassword-font {
  font-size: 20px;
  margin-right: 73px;
}
.setting-schema-save-button {
  margin-top: 10px;
  margin-right: 50px;
  padding: 15px 20px !important;
  font-size: 15px !important;
  float: right;
}
</style>
