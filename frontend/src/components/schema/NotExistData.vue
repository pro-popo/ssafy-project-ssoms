<template>
  <v-container style="height: 350px; width: 600px;">
    <v-row class="fill-height" align-content="center" justify="center">
      <v-col class="subtitle-1 text-center" cols="12">
        <div class="data-notexist">
          <img src="@/assets/sad.png" alt="sad" width="150px" />
          <h2 style="color:var(--font-sub2-color); margin-top:15px">
            SORRY
          </h2>
          <h3 style="color:var(--font-sub-color)">
            Data does not exist.
          </h3>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "NotExistData"
};
</script>

<style></style>
