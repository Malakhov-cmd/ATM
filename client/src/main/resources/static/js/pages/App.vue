<template>
  <div class="main-component" id="app">
    <Login/>
  </div>
</template>

<script>
import Login from "../components/login/Login.vue";

export default {
  name: "App",
  components: {Login}
}
</script>

<style scoped>
.main-component {
  min-height: 100vh;
  min-width: 100vw;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
</style>