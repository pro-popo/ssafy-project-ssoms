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
          <button @click="setSettingsSchema(schema.userID)">삭제</button>
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
      @click="setSettingsSchema(1)"
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
    setSettingsSchema(type) {
      // var checkDuplicate = 0;
      // for (var i = 0; i < this.schemaList.length; i++) {
      //   if (this.schemaList[i].userID === this.userID) {
      //     checkDuplicate = 1;
      //   }
      // }
      // if (checkDuplicate === 0) {
      if (type === 1) {
        this.schemaList.push({ userID: this.userID });
      } else {
        const itemToFind = this.schemaList.find(function(item) {
          return item.userID === type;
        });
        const idx = this.schemaList.indexOf(itemToFind);
        if (idx > -1) {
          this.schemaList.splice(idx, 1);
        }
      }
      axios
        .post(SERVER.URL + SERVER.ROUTES.setSettingsSchema, this.schemaList)
        .then((res) => {
          console.log(res.data.result);
          if (res.data.result === "saveSuccess") {
            console.log("check");
            console.log("2", this.checkSettingsSchema());
          }
          this.userID = "";
          if (type === 1) {
            alert("저장하였습니다.");
          } else {
            alert("삭제하였습니다.");
          }
        })
        .catch(() => console.log("실패"));
      // } else {
      //   this.userID = "";
      //   alert("중복된 값이 있습니다.");
      // }
    },
    getSettingsSchema() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getSettingsSchema)
        .then((res) => {
          this.schemaList = res.data.map.schema;
        })
        .catch((err) => console.log(err));
    },
    checkSettingsSchema() {
      axios
        .post(SERVER.URL + SERVER.ROUTES.checkSettingsSchema, {
          userID: this.userID
        })
        .then((res) => {
          console.log("1", res.data.result);
          if (res.data.result === "duplicate") {
            console.log("테스트");
            return "duplicate";
          } else if (res.data.result === "notExist") {
            return "notExist";
          } else if (res.data.result === "success") {
            return "success";
          } else {
            return "success";
          }
        });
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
