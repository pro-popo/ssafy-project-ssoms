<template>
  <v-card>
    <v-card-title>
      <span class="headline">PDF 저장</span>
    </v-card-title>
    <v-card-text>
      <h3>출력 항목 선택</h3>
      <v-container>
        <v-row>
          <small>*현재 화면을 기준으로 캡쳐됩니다.</small>
        </v-row>
        <v-row>
          <v-col cols="12">
            <v-select
              v-model="toPdf[0]"
              :items="elems[0]"
              chips
              multiple
              v-if="$route.name == 'RealTimeMonitoring'"
              outlined
            ></v-select>
            <v-select
              v-model="toPdf[1]"
              :items="elems[1]"
              chips
              multiple
              v-if="$route.name == 'SchemaMonitoring'"
              outlined
            ></v-select>
          </v-col>
        </v-row>
      </v-container>
      <small>*indicates required field</small>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="$emit('kill-modal')">
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
      toPdf: [[], []],
      elems: [
                [
                    {text: "Oracle DB Status", value: "oracleStatus"},
                    {text: "Schema Status", value: "allSchemaStatics"},
                    {text: "Top Query", value: "allSchemaTopQuery"}
                ],
                [
                    {text: "Schema Status", value:"pastMonitering"},
                    {text: "Top Query", value:"SchemaQuerys"}
                ]
            ],
    };
  },
  methods: {
      makebtn(){
          if(this.$route.name == 'RealTimeMonitoring'){
              this.toPdf[0].forEach(element => {
                if (element == "allSchemaTopQuery"){
                    this.makePDF([document.getElementById('allSchemaTopQueryTable').firstElementChild.firstElementChild], element)
                }else{
                    this.makePDF([document.getElementById(element)], element)
                }
              });
          }else if(this.$route.name == 'SchemaMonitoring'){
              this.toPdf[1].forEach(element => {
              if(element == "pastMonitering"){
                this.makePDF([document.getElementById('SchemaWhole'), document.getElementById('SchemaDetail')], element)
              }else if(element == "SchemaQuerys"){
                this.makePDF([document.getElementById('SchemaQuerys').firstElementChild.firstElementChild], element)
              }
            });
          }
          
      },
      makePDF(ele, name) {
			window.html2canvas = html2canvas
            let pdf = new jsPDF('p', 'mm', 'a4');
            
			if(!ele)
				return false
            let idx = 0;
            let beforeheight = 0;
            ele.forEach(element => {
                let canvas = pdf.canvas
                const pageWidth = 210 //캔버스 너비 mm
                canvas.width = pageWidth
                let width = element.offsetWidth // 셀렉트한 요소의 px 너비
                let height = element.offsetHeight // 셀렉트한 요소의 px 높이
                let imgHeight = pageWidth * height/width // 이미지 높이값 px to mm 변환
                html2canvas(element).then(canvas => {
                    let imgData = canvas.toDataURL('image/png');
                    pdf.addImage(imgData, 'png', 0, beforeheight, pageWidth, imgHeight, 'image'+String(idx), 'SLOW');
                    if(idx == ele.length - 1){
                        let date = new Date();
                        pdf.save( date.getFullYear()+"_"+date.getMonth()+"_"+date.getDay()+"_"+date.getHours()+"_"+date.getMinutes()+"_"+name+".pdf")
                    }
                    idx++;
                    beforeheight += imgHeight;
                }).catch(err => {
                    console.log(err)
                });
          });
            this.$emit('kill-modal')
		},
  },
  mounted() {
    
  }
};
</script>

<style>
</style>