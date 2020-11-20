<template>
  <div style="display:flex;">
    <div class="admin-schema-container">
      <h2 class="mb-3" style="color:var(--font-main-color)">Schema Setting</h2>
      <div class="schema-searchbar">
        <div
          style="width:400px; height:100%; padding-top:25px; margin-bottom:-10px"
        ></div>
        <div style="height:100%; margin-bottom:-8px">
          <v-dialog v-model="dialog" persistent max-width="500">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                style="margin-left:auto; margin-right:10px; margin-bottom:20px;"
                fab
                small
                elevation="2"
                color="var(--font-sub2-color)"
                v-bind="attrs"
                v-on="on"
                dark
                @click="getAllSchemaList"
                id="add-schema-btn"
                ><v-icon v-if="!isCheckAdd">mdi-plus</v-icon>
                <v-icon v-if="isCheckAdd">mdi-close</v-icon></v-btn
              >
            </template>
            <v-card>
              <v-list
                dense
                elevation="3"
                style="border-radius:5px;width:500;height: 100%;"
              >
                <v-card-text>
                  <div style="display:flex; justify-content:space-between">
                    <h3>스키마 목록</h3>
                    <v-btn
                      style="margin-top:-5px"
                      small
                      icon
                      @click="[(dialog = false), (isCheckAdd = false)]"
                      ><v-icon>mdi-close</v-icon></v-btn
                    >
                  </div>
                  <div>
                    <small style="color:var(--font-sub-color)">
                      모든 스키마의 목록입니다.
                    </small>
                    <div class="add-schema-form">
                      <v-text-field
                        append-outer-icon="mdi-magnify"
                        label="검색할 스키마ID를 입력해주세요."
                        v-model="findSchemaName"
                        @keyup="findSchema"
                      ></v-text-field>
                    </div>
                  </div>

                  <v-divider style="margin-top: 10px"></v-divider>
                  <v-simple-table class="all-schema-list-item">
                    <template v-slot:default>
                      <tbody>
                        <tr v-if="findSchemaList.length == 0">
                          <td class="text-center" colspan="2">
                            조회된 Schema가 없습니다.
                          </td>
                        </tr>
                        <tr v-for="(schema, i) in findSchemaList" :key="i">
                          <td style="width: 100%">{{ schema }}</td>
                          <td>
                            <v-btn
                              color="grey"
                              outlined
                              fab
                              x-small
                              class="setting-schema-save-button"
                              @click="saveSchema(schema)"
                            >
                              <v-icon>mdi-plus</v-icon>
                            </v-btn>
                          </td>
                        </tr>
                      </tbody>
                    </template>
                  </v-simple-table>
                  <v-divider></v-divider>
                </v-card-text>
              </v-list>
            </v-card>
          </v-dialog>
        </div>
      </div>
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
            <tbody
              class="admin-schema-table-tbody"
              style="color:var(--font-sub2-color)"
            >
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
  </div>
</template>

<script>
import axios from "axios";
import SERVER from "@/api/spring.js";

export default {
  name: "Schema",
  data() {
    return {
      dialog: false,
      schemaList: [],
      allSchemaList: [],
      userID: "",
      isCheckAdd: false,
      findSchemaName: "",
      findSchemaList: []
    };
  },
  // watch: {
  //   findSchemaList: function() {
  //     this.findSchemaList = this.allSchemaList;
  //   }
  // },
  methods: {
    findSchema() {
      this.findSchemaList = [];
      if (this.findSchemaName == "") this.findSchemaList = this.allSchemaList;
      else {
        this.allSchemaList.forEach((schema) => {
          if (
            schema.toLowerCase().includes(this.findSchemaName.toLowerCase()) ||
            schema.toUpperCase().includes(this.findSchemaName.toUpperCase())
          ) {
            this.findSchemaList.push(schema);
          }
        });
      }
    },
    deleteSchema(schemaId) {
      var deleteConfirm = confirm("정말 삭제하시겠습니까?");
      if (deleteConfirm) {
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
      }
    },
    getSettingsSchema() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getSettingsSchema)
        .then((res) => {
          this.schemaList = res.data.map.schema;
        })
        .catch((err) => console.log(err));
    },
    saveSchema(schema) {
      if (this.schemaList.length > 7) {
        alert("최대 8개의 Schema까지 등록가능합니다.");
        return;
      }
      // if (this.userID === "") {
      //   alert("Schema ID를 입력해주세요.");
      //   return;
      // }
      this.userID = schema;
      // 중복 및 존재여부 확인
      axios
        .post(SERVER.URL + SERVER.ROUTES.checkSettingsSchema, {
          userID: this.userID
        })
        .then((res) => {
          console.log("1", res.data.result);
          if (res.data.result === "duplicate") {
            alert("이미 추가된 스키마 ID 입니다.");
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
    },
    getAllSchemaList() {
      axios
        .get(SERVER.URL + SERVER.ROUTES.getAllSchemaList)
        .then((res) => {
          this.allSchemaList = res.data.map.schemaList;
          this.findSchemaList = res.data.map.schemaList;
          this.isCheckAdd = !this.isCheckAdd;
          this.findSchemaName = "";
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
.all-schema-list-item {
  overflow-y: scroll;
  height: 403px;
}

.admin-schema-table-tbody td {
  border-bottom: 1px solid #d0d0d0;
}
.admin-schema-container {
  max-width: 800px;
  width: 100%;
}
.schema-searchbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
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
.admin-schema-container tr:hover {
  background: rgb(219, 219, 219) !important;
}
.admin-schema-container table {
  border-left: 1px solid #d0d0d0 !important;
  border-right: 1px solid #d0d0d0 !important;
}
.schema-searchbar .v-input__slot {
  background: white !important;
  color: var(--font-sub2-color) !important;
}
.schema-searchbar .v-input__slot * {
  color: var(--font-sub2-color) !important;
}
</style>
