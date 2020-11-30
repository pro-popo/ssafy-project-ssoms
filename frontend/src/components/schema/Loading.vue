<template>
  <div class="v-spinner ma-10;" v-show="loading" align="center">
    <v-container style="height: 350px; width: 600px">
      <v-row class="fill-height" align-content="center" justify="center">
        <v-col class="subtitle-1 text-center" cols="12">
          <h2
            style="color: var(--font-sub2-color)"
            class="animate__animated animate__pulse loading"
          >
            Loading...
          </h2>
        </v-col>
        <v-col cols="6">
          <v-progress-linear
            color="deep-purple accent-4"
            indeterminate
            rounded
            height="6"
          ></v-progress-linear>
          <p style="margin-top: 10px; color: var(--font-sub-color)">
            please wait a moment.
          </p>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  name: "Loading",
  props: {
    loading: {
      type: Boolean,
      default: true,
    },
    color: {
      type: String,
      default: "#BDBDBD",
    },
    size: {
      type: String,
      default: "120px",
    },
    margin: {
      type: String,
      default: "2px",
    },
    radius: {
      type: String,
      default: "100%",
    },
  },
  data() {
    return {
      spinnerStyle: {
        height: this.size,
        width: this.size,
        borderRadius: this.radius,
      },
    };
  },
  computed: {
    moonSize() {
      return parseFloat(this.size) / 7;
    },
    spinnerMoonStyle() {
      return {
        height: this.moonSize + "px",
        width: this.moonSize + "px",
        borderRadius: this.radius,
      };
    },
    animationStyle2() {
      return {
        top: parseFloat(this.size) / 2 - this.moonSize / 2 + "px",
        backgroundColor: this.color,
      };
    },
    animationStyle3() {
      return {
        border: this.moonSize + "px solid " + this.color,
      };
    },
  },
};
</script>

<style>
.v-moon {
  margin: auto;
}

.v-spinner .v-moon1 {
  -webkit-animation: v-moonStretchDelay 0.6s 0s infinite linear;
  animation: v-moonStretchDelay 0.6s 0s infinite linear;
  -webkit-animation-fill-mode: forwards;
  animation-fill-mode: forwards;
  position: relative;
}

.v-spinner .v-moon2 {
  -webkit-animation: v-moonStretchDelay 0.6s 0s infinite linear;
  animation: v-moonStretchDelay 0.6s 0s infinite linear;
  -webkit-animation-fill-mode: forwards;
  animation-fill-mode: forwards;
  opacity: 0.8;
  position: absolute;
}

.v-spinner .v-moon3 {
  opacity: 0.1;
}
.loading {
  --animate-duration: 1.5s;
  animation-iteration-count: infinite;
}

@-webkit-keyframes v-moonStretchDelay {
  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}

@keyframes v-moonStretchDelay {
  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}
</style>
