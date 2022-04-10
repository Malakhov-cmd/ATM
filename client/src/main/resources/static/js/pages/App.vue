<template>
  <div class="main-component" id="app">
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: "App",
  mounted() {
    location.href = "/#/login"
  }
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