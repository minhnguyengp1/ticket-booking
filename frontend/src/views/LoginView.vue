<template>
  <div class="container">
    <div class="login-container">
      <!-- Header with Title and Icon -->
      <div class="header">
        <h1 class="title">Ticket Booking</h1>
<!--        <span class="icon">🎫</span>-->
      </div>

      <!-- Form for Login/Register -->
      <div class="form-container">
        <div class="form-group">
          <label for="email">Email</label>
          <input
              v-model="email"
              type="email"
              id="email"
              placeholder="Email"
              class="input"
          />
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input
              v-model="password"
              type="password"
              id="password"
              placeholder="Password"
              class="input"
          />
        </div>

        <!-- Button to submit form -->
        <button
            :disabled="isLoadingAuth"
            @click="onAuthenticate"
            class="submit-button"
        >
          {{ authMode }}
          <span v-if="isLoadingAuth" class="loading">Loading...</span>
        </button>
      </div>

      <!-- Divider -->
      <div class="divider"></div>

      <!-- Toggle Between Login/Register -->
      <div class="toggle-container">
        <p @click="onToggleAuthMode" class="toggle-text">
          {{ authMode === 'login' ? 'Register new account' : 'Login to account' }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      authMode: 'login',
      email: '',
      password: '',
    };
  },
  computed: {
    isLoadingAuth() {
      return this.$store.getters['auth/isLoadingAuth']; // Use getter to check loading status
    },
  },
  methods: {
    async onAuthenticate() {
      try {
        // Gọi action authenticate từ Vuex và đợi kết quả
        await this.$store.dispatch('auth/authenticate', {
          authMode: this.authMode,
          email: this.email,
          password: this.password,
        });

        // Nếu đăng nhập thành công, điều hướng về trang chính (hoặc trang mong muốn)
        this.$router.push('/');
      } catch (error) {
        // Xử lý lỗi nếu đăng nhập thất bại (Thông báo cho người dùng nếu cần)
        console.error('Authentication failed', error);
      }
    },

    onToggleAuthMode() {
      this.authMode = this.authMode === 'login' ? 'register' : 'login';
    },
  },
  created() {
    // Kiểm tra trạng thái đăng nhập khi component được tạo
    this.$store.dispatch('auth/checkLoginStatus');
  },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f4f4f4;
}

.login-container {
  width: 100%;
  max-width: 400px;
  padding: 20px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.header {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.title {
  font-size: 30px;
  font-weight: bold;
  margin-bottom: 20px;
}

.form-container {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  font-size: 14px;
  color: gray;
}

.input {
  width: 100%;
  padding: 14px;
  margin-top: 5px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.submit-button {
  width: 100%;
  padding: 14px;
  background-color: black;
  color: white;
  font-size: 18px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:disabled {
  background-color: gray;
}

.loading {
  font-size: 12px;
  margin-left: 10px;
}

.divider {
  margin: 20px 0;
  border-bottom: 1px solid #ddd;
}

.toggle-container {
  margin-top: 10px;
}

.toggle-text {
  font-size: 16px;
  color: #007bff;
  cursor: pointer;
  text-decoration: underline;
}

.toggle-text:hover {
  color: #0056b3;
}
</style>
