<template>
  <div>
    <!-- Header, sidebar hoặc các thành phần chung của ứng dụng -->
    <div class="header">
      <h1>Ticket Booking</h1>
    </div>

    <!-- Nội dung của các trang con -->
    <router-view></router-view>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: {
    ...mapGetters({
      isLoggedIn: 'auth/isLoggedIn',
    }),
  },
  watch: {
    // Theo dõi trạng thái đăng nhập và chuyển hướng nếu chưa đăng nhập
    isLoggedIn(newVal) {
      if (!newVal) {
        this.$router.push('/login'); // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
      }
    },
  },
  created() {
    // Kiểm tra trạng thái đăng nhập khi tạo component
    this.$store.dispatch('auth/checkLoginStatus');
  },
};
</script>

<style scoped>
.header {
  text-align: center;
  background-color: #333;
  padding: 10px;
  color: white;
}
</style>
