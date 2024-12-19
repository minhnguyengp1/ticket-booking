<template>
  <div class="container">
    <!-- Nếu dữ liệu đang tải -->
    <div v-if="isLoading" class="loading">Loading...</div>

    <!-- Nếu không có dữ liệu -->
    <div v-else-if="!ticket" class="error">
      <p>Ticket not found</p>
      <button @click="goBack">Go Back</button>
    </div>

    <!-- Chi tiết vé -->
    <div v-else class="ticket-details">
      <div class="ticket-info">
        <h1 class="event-name">{{ ticket.event.name }}</h1>
        <h2 class="event-location">{{ ticket.event.location }}</h2>
        <p class="event-date">{{ new Date(ticket.event.date).toLocaleString() }}</p>
      </div>

      <!-- QR Code -->
      <img
          v-if="qrcode"
          :src="'data:image/png;base64,' + qrcode"
          alt="QR Code"
          class="qrcode"
      />
    </div>
  </div>
</template>

<script>
import { ticketService } from "@/services/tickets";
import {mapGetters} from "vuex";

export default {
  name: "TicketDetails",
  data() {
    return {
      ticket: null,
      qrcode: null,
      isLoading: false,
    };
  },
  computed: {
    ...mapGetters('auth', ['user', 'isLoggedIn']),
  },
  methods: {
    async fetchTicket() {
      try {
        this.isLoading = true;
        const { id } = this.$route.params; // Lấy ticketId từ route params
        const userId = this.user?.id; // Lấy userId từ store hoặc từ nơi bạn lưu thông tin người dùng

        const response = await ticketService.getOne(id, userId);

        this.ticket = response.ticket;
        this.qrcode = response.qrcode;
      } catch (error) {
        this.ticket = null;
      } finally {
        this.isLoading = false;
      }
    },
    goBack() {
      this.$router.back();
    },
  },
  created() {
    this.fetchTicket();
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px;
  max-width: 600px;
  margin: 60px auto 40px; /* Add top margin to push the container down */
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.loading,
.error {
  font-size: 18px;
  color: gray;
  text-align: center;
}

.ticket-details {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.ticket-info {
  text-align: center;
}

.event-name {
  font-size: 36px;
  font-weight: bold;
}

.event-location {
  font-size: 24px;
  margin: 10px 0;
  color: #666;
}

.event-date {
  font-size: 16px;
  color: gray;
}

.qrcode {
  width: 300px;
  height: 300px;
  border-radius: 10px;
}
</style>
