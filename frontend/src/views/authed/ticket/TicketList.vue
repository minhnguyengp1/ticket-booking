<template>
  <div class="container">
    <!-- Header Section -->
    <div class="header">
      <h2 class="title">{{ tickets ? tickets.length : 0 }} Tickets</h2>
    </div>

    <!-- Ticket List -->
    <div v-if="isLoading" class="loading">Loading...</div>

    <div v-else>
      <div v-for="ticket in tickets" :key="ticket.id" class="ticket-card">
        <div
            :class="['ticket', { 'ticket-entered': ticket.entered }]"
            @click="goToTicketPage(ticket.id)"
        >
          <!-- Ticket Details Section -->
          <div class="ticket-info">
            <div class="ticket-event">
              <span class="event-name">{{ ticket.event.name }}</span>
              <span class="event-location">{{ ticket.event.location }}</span>
            </div>
            <div class="event-date">{{ new Date(ticket.event.date).toLocaleString() }}</div>
          </div>

          <!-- Divider -->
          <div class="divider"></div>

          <!-- Ticket Status Section -->
          <div class="ticket-status">
            <span>{{ ticket.entered ? "Used" : "Available" }}</span>
            <div v-if="ticket.entered" class="used-at">
              <small>{{ new Date(ticket.updatedAt).toLocaleString() }}</small>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ticketService } from "@/services/tickets";
import { mapGetters } from "vuex";

export default {
  name: "TicketList",
  data() {
    return {
      tickets: [],
      isLoading: false
    };
  },
  computed: {
    ...mapGetters('auth', ['user', 'isLoggedIn']),
  },
  methods: {
    async fetchTickets() {
      try {
        this.isLoading = true;
        const userId = this.user?.id;

        const response = await ticketService.getAll(userId);

        console.log("response: " + JSON.stringify(response))
        this.tickets = response.data;
      } catch (error) {
        alert("Error", "Failed to fetch tickets");
      } finally {
        this.isLoading = false;
      }
    },
    goToTicketPage(id) {
      this.$router.push(`/tickets/${id}`);
    }
  },
  created() {
    this.fetchTickets();
  }
};
</script>

<style scoped>
/* Container for tickets */
.container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* Header */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.title {
  font-size: 24px;
  font-weight: bold;
}

/* Loading state */
.loading {
  font-size: 18px;
  color: gray;
  text-align: center;
  margin-top: 20px;
}

/* Ticket Card */
.ticket-card {
  background-color: white;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  cursor: pointer;
}

.ticket-card .ticket-entered {
  opacity: 0.5;
  cursor: not-allowed;
}

/* Ticket Details */
.ticket-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.ticket-event {
  display: flex;
  flex-direction: column;
}

.event-name {
  font-size: 18px;
  font-weight: bold;
}

.event-location {
  font-size: 14px;
  color: gray;
}

.event-date {
  font-size: 12px;
  color: gray;
}

/* Divider */
.divider {
  height: 1px;
  background-color: lightgray;
  margin: 10px 0;
}

/* Ticket Status */
.ticket-status {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.ticket-status span {
  font-size: 16px;
  font-weight: bold;
}

.used-at {
  font-size: 12px;
  color: gray;
}
</style>
