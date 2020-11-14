<template>
  <v-card>
    <v-card-title>
      <span class="headline">PDF 저장 - {{ title }}</span>
    </v-card-title>
    <v-card-text>
      <h3>출력 항목 선택</h3>
      <div v-if="Type=1"></div>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-select
              v-model="toPdf"
              :items="elems"
              chips
              label="Chips"
              multiple
              outlined
            ></v-select>
          </v-col>
          <v-col cols="12" sm="6" md="4">
            <v-text-field label="Legal first name*" required></v-text-field>
          </v-col>
          <v-col cols="12" sm="6" md="4">
            <v-text-field
              label="Legal middle name"
              hint="example of helper text only on focus"
            ></v-text-field>
          </v-col>
          <v-col cols="12" sm="6" md="4">
            <v-text-field
              label="Legal last name*"
              hint="example of persistent helper text"
              persistent-hint
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field label="Email*" required></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field
              label="Password*"
              type="password"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" sm="6">
            <v-select
              :items="['0-17', '18-29', '30-54', '54+']"
              label="Age*"
              required
            ></v-select>
          </v-col>
          <v-col cols="12" sm="6">
            <v-autocomplete
              :items="[
                'Skiing',
                'Ice hockey',
                'Soccer',
                'Basketball',
                'Hockey',
                'Reading',
                'Writing',
                'Coding',
                'Basejump'
              ]"
              label="Interests"
              multiple
            ></v-autocomplete>
          </v-col>
        </v-row>
      </v-container>
      <small>*indicates required field</small>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="$emit('killModal')">
        닫기
      </v-btn>
      <v-btn color="blue darken-1" text @click="$emit('killModal')">
        저장
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "ReportModal",
  data() {
    return {
      title: "",
      toPdf: [],
      titles_kor: ["상세", "전체", "스키마별"],
      elems: ["전체", "cpu"]
    };
  },
  props: {
    Type: Number
  },
  mounted() {
    this.title = this.titles_kor[this.Type];
  }
};
</script>

<style>
</style>