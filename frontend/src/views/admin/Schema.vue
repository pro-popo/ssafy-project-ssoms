<template>
  <div>
    <h2 class="mb-3">Schema 설정</h2>
    <div class="schema-searchbar">
      <span class="mdi mdi-magnify"></span>
      <v-text-field label="검색할 스키마명을 입력해주세요."></v-text-field>
    </div>
    <div class="schema-box">
      <div
        class="schema-list"
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
    <!-- <div class="input-group setting-schema-data">
      <p class="setting-schemaPassword-font">Password</p>
      <input
        type="password"
        class="form-control"
        placeholder="스키마 Password를 입력해주세요."
      />
    </div> -->
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
        .then(() => {
          this.userID = "";
          if (type === 1) {
            alert("저장하였습니다.");
          } else {
            alert("삭제하였습니다.");
          }
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
.schema-list {
  font-size: 15px;
}
.schema-list ol {
  padding-top: 7px;
  margin-bottom: 7px;
}
.schema-list > ol > button {
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
