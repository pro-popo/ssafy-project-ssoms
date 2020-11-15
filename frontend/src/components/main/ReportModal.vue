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
              multiple
              outlined
            ></v-select>
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
      <v-btn color="blue darken-1" text @click="makebtn">
        저장
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

export default {
  name: "ReportModal",
  data() {
    return {
      title: "",
      toPdf: [],
      titles_kor: ["상세", "전체", "스키마별"],
      elems: [
          {text: "Oracle DB Status", value: "oracleStatus"},
          {text: "Schema Status", value: "allSchemaStatics"},
          {text: "Top Query", value: "allSchemaTopQuery"},
      ]
    };
  },
  props: {
    Type: Number
  },
  methods: {
      makebtn(){
          this.toPdf.forEach(element => {
              if (element == "allSchemaTopQuery"){
                this.makePDF(document.getElementById('allSchemaTopQueryTable').firstElementChild.firstElementChild, element)
              }else{
                this.makePDF(document.getElementById(element), element)
              }
          });
      },
      makePDF(ele, name) {
			window.html2canvas = html2canvas
			let pdf = new jsPDF('p', 'mm', 'a4')
            let canvas = pdf.canvas
            const pageWidth = 210 //캔버스 너비 mm
			canvas.width = pageWidth
			let width = ele.offsetWidth // 셀렉트한 요소의 px 너비
			let height = ele.offsetHeight // 셀렉트한 요소의 px 높이
            let imgHeight = pageWidth * height/width // 이미지 높이값 px to mm 변환
            
			if(!ele){
				console.warn( ' is not exist.')
				return false
			}
            html2canvas(ele, {}).then(canvas => {
                let position = 0
                const imgData = canvas.toDataURL('image/png')
                pdf.addImage(imgData, 'png', 0, position, pageWidth, imgHeight, undefined, 'slow')
                let date = new Date();
                pdf.save( date.getFullYear()+"_"+date.getMonth()+"_"+date.getDay()+"_"+date.getHours()+"_"+date.getMinutes()+"_"+name+".pdf")
            }).catch(err => {
                console.log(err)
            });
            this.$emit('killModal')
		},
  },
  mounted() {
    this.title = this.titles_kor[this.Type];
  }
};
</script>

<style>
</style>