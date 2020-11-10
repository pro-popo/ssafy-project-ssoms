<template>
  <v-card elevation="2">
    <v-card-text class="oracle-storage">
      <div class="oracle-title-icon">
        <!-- <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-icon v-bind="attrs" v-on="on" size="23" dark
              >mdi-file-cog-outline</v-icon
            >
          </template>
          <span>File</span>
        </v-tooltip> -->
      </div>
      <div class="oracle-storage-chart">
        <!-- <div style="display:flex; justify-content: space-between;">
          <div>
            <v-icon color="var(--main-sub-color)">mdi-file-cog-outline</v-icon>
            <span class="oracle-status-name"> File</span>
          </div>
        </div> -->
        <div class="storage-title-icon">
          <v-icon class="storage-logo-icon" size="18" dark
            >mdi-file-cog-outline</v-icon
          >
          <span class="storage-status-name"> File</span>
          <v-btn small icon style="margin: -10px -10px 0px auto">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
          <!-- <span style="margin-left:auto;font-size:12px">단위(%)</span> -->
        </div>
        <div style="height:85%">
          <IEcharts :option="option" />
        </div>
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
import IEcharts from "vue-echarts-v3/src/full.js";
import { mapGetters } from "vuex";
export default {
  name: "OracleStorage",
  components: {
    IEcharts
  },
  computed: {
    ...mapGetters("Oracle", [
      "getPhysicalReadsPerSec",
      "getPhysicalWritesPerSec"
    ]),
    ...mapGetters(["getRealTimeList"])
  },
  watch: {
    getPhysicalReadsPerSec: function() {
      this.option.xAxis.data = this.getRealTimeList;
      this.option.series[0].data = this.getPhysicalReadsPerSec;
      this.option.series[1].data = this.getPhysicalWritesPerSec;
    }
  },
  data() {
    return {
      physicalReadsPerSec: 0.0, // (mb / 초)
      physicalWritesPerSec: 0.33, // (mb / 초)

      option: {
        color: ["#81D4FA", "#42A5F5"],
        grid: {
          right: 25,
          left: 30,
          bottom: 20,
          top: 55
        },
        // title: { text: "CPU Time" },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: [],
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          }
          // axisTick: {
          //   show: false
          // }
        },
        yAxis: {
          name: "(md)",
          type: "value",
          axisLine: {
            lineStyle: {
              color: "#ababab"
            }
          },

          axisTick: {
            show: false
          },

          max: function(item) {
            if (item.max < 0.1) return item.max;
            else if (item.max.toFixed(1) < item.max)
              return item.max.toFixed(1) + 0.1;
            else return item.max.toFixed(1);
          },
          splitNumber: 3
        },
        legend: {
          data: ["Physical Reads", "Physical Writes"],
          // icon: "circle",
          // bottom: 0,
          textStyle: {
            fontSize: 10
          }
        },
        tooltip: {
          trigger: "axis"
        },
        series: [
          {
            name: "Physical Reads",
            data: [],
            type: "line",
            showSymbol: false
          },
          {
            name: "Physical Writes",
            data: [],
            type: "line",
            showSymbol: false
          }
        ]
      }
    };
  }
};
</script>

<style>
/* .oracle-storage-chart {
  width: 50%;
  height: 300px;
  border: 1px solid var(--font-sub-color);
  border-radius: 10px;
  padding: 15px 10px;
  margin-bottom: 30px;
  color: var(--font-sub2-color);
} */
.oracle-storage {
  height: 100%;
}
.oracle-storage-chart {
  height: 100%;
  width: 100%;
}
.storage-title-icon {
  display: flex;
  align-items: center;
}

.storage-title-icon :nth-child(2) {
  color: var(--font-sub2-color);
  font-size: 16px;
  font-weight: bold;
}

.storage-logo-icon {
  background: linear-gradient(
    to bottom right,
    var(--main-color),
    var(--main-point-color)
  );
  border-radius: 100px;
  height: 30px;
  width: 30px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
}
</style>
