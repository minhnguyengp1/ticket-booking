<template>
  <div class="container">
    <!-- Header Section -->
    <div class="header">
      <h2 class="title">{{ events.length }} Events</h2>

      <!-- Create New Event Button -->
      <button v-if="userRole === 'MANAGER'" @click="goToEventCreate" class="create-btn">
        Create New Event
      </button>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="loading">Loading...</div>

    <!-- Events List -->
    <div v-else>
      <div v-for="event in events" :key="event.id" class="event-card">
        <div
            :class="['event', { 'event-entered': event.entered }]"
            @click="goToEventPage(event.id)"
        >
          <!-- Event Details Section -->
          <div class="event-info">
            <div class="event-header">
              <span class="event-name">{{ event.name }}</span>
              <span class="event-location">{{ event.location }}</span>
            </div>
            <div class="event-date">{{ new Date(event.date).toLocaleString() }}</div>
          </div>

          <!-- Divider -->
          <div class="divider"></div>

          <!-- Event Footer -->
          <div class="event-footer">
            <span class="sold-tickets">Sold: {{ event.totalTicketsPurchased }}</span>
            <span class="entered-tickets">Entered: {{ event.totalTicketsEntered }}</span>
          </div>

          <!-- Buy Ticket Button for Attendees -->
          <button v-if="userRole === 'ATTENDEE'" @click="buyTicket(event.id)" class="buy-ticket-btn">
            Buy Ticket
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { eventService } from '@/services/events';
import { ticketService } from '@/services/tickets';
import { mapGetters } from 'vuex';

export default {
  name: "EventList",
  data() {
    return {
      events: [],
      isLoading: false,
    };
  },
  computed: {
    ...mapGetters('auth', ['user', 'isLoggedIn']),
    userRole() {
      return this.user?.role || null;
    },
    userId() {
      return this.user?.id || null;
    },
  },
  methods: {
    async fetchEvents() {
      try {
        this.isLoading = true;
        const response = await eventService.getAll();
        console.log("response: " + JSON.stringify(response))
        this.events = response;
      } catch (error) {
        alert('Failed to fetch events');
      } finally {
        this.isLoading = false;
      }
    },
    goToEventPage(eventId) {
      if (this.userRole === 'MANAGER') {
        this.$router.push(`/events/${eventId}`);
      }
    },
    async buyTicket(eventId) {
      try {
        const userId = this.userId;
        if (!userId) throw new Error("User ID is required");

        await ticketService.createOne(eventId, userId);
        alert('Ticket purchased successfully');
        await this.fetchEvents();
      } catch (error) {
        alert('Failed to buy ticket');
      }
    },
    goToEventCreate() {
      this.$router.push('/events/new');
    },
  },
  created() {
    this.fetchEvents();
  },
};
</script>

<style scoped>
/* Container for events */
.container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* Header Section */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

/* Title */
.title {
  font-size: 24px;
  font-weight: bold;
}

/* Button to create new event (Manager only) */
.create-btn {
  padding: 12px;
  background-color: #4CAF50;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.create-btn:hover {
  background-color: #45a049;
}

/* Loading state */
.loading {
  font-size: 18px;
  color: gray;
  text-align: center;
  margin-top: 20px;
}

/* Event Card */
.event-card {
  background-color: white;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  cursor: pointer;
}

.event-card .event-entered {
  opacity: 0.5;
  cursor: not-allowed;
}

/* Event Details */
.event-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.event-header {
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

/* Event Footer */
.event-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.sold-tickets {
  font-size: 16px;
  font-weight: bold;
  color: gray;
}

.entered-tickets {
  font-size: 16px;
  font-weight: bold;
  color: green;
}

/* Buy Ticket Button */
.buy-ticket-btn {
  padding: 12px;
  background-color: #4CAF50;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 10px;
}

.buy-ticket-btn:disabled {
  background-color: #ddd;
  cursor: not-allowed;
}

.buy-ticket-btn:hover {
  background-color: #45a049;
}
</style>
