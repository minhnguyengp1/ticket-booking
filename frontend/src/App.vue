<template>
  <div id="app">
    <router-view></router-view>  <!-- Đây là nơi các route được render -->
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    this.$store.dispatch('auth/checkLoginStatus'); // Khởi động checkLoginStatus
  }
};
</script>

<style>
#app {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}
</style>
