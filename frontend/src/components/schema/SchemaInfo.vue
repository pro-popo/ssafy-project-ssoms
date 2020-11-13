<template>
  
</template>

<script>
import { mapGetters } from "vuex";
export default {
    name: "SchemaInfo",
    data() {
        
    },
    methods: {
        
    },
    computed: {
        ...mapGetters("Schema", ["getPastTimeData"]),
        ...mapGetters("Schema", ["SelectedSchema"])
    }
}
</script>

<style>

</style>