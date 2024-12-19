<template>
  <div v-if="eventData" class="container">
    <h2>Edit Event</h2>

    <div class="form-group">
      <label>Name</label>
      <input v-model="eventData.name" placeholder="Name" class="input-field" />
    </div>

    <div class="form-group">
      <label>Location</label>
      <input v-model="eventData.location" placeholder="Location" class="input-field" />
    </div>

    <div class="form-group">
      <label>Date</label>
      <input type="datetime-local" v-model="eventData.date" class="input-field" />
    </div>

    <div class="buttons">
      <button :disabled="isSubmitting" @click="onSubmitChanges" class="btn-save">
        Save Changes
      </button>
      <button @click="onDelete" class="btn-delete">Delete Event</button>
    </div>
  </div>
</template>

<script>
import { eventService } from '@/services/events';
import { useRoute } from 'vue-router';

export default {
  name: "EventDetails",
  data() {
    return {
      eventData: null,
      isSubmitting: false,
    };
  },
  methods: {
    async fetchEvent() {
      const route = useRoute();
      const eventId = route.params.id;
      console.log("eventId: " + eventId)
      try {
        const response = await eventService.getOne(eventId);
        console.log("getOne from eventService: " + JSON.stringify(response))
        this.eventData = response.data;
      } catch (error) {
        this.$router.push('/events');
      }
    },
    async onSubmitChanges() {
      if (!this.eventData) return;
      try {
        this.isSubmitting = true;
        await eventService.updateOne(this.eventData);
        this.$router.push('/events');
      } catch (error) {
        alert('Failed to update event');
      } finally {
        this.isSubmitting = false;
      }
    },
    onDelete() {
      if (confirm('Are you sure you want to delete this event?')) {
        eventService.deleteOne(this.eventData.id);
        this.$router.push('/events');
      }
    },
  },
  created() {
    this.fetchEvent();
  },
};
</script>

<style scoped>
/* Container for event editing */
.container {
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
}

/* Title */
h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

/* Input Fields */
.form-group {
  margin-bottom: 20px;
}

label {
  font-size: 16px;
  color: gray;
  display: block;
  margin-bottom: 8px;
}

.input-field {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.input-field:focus {
  border-color: #4CAF50;
}

/* Buttons */
.buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.btn-save,
.btn-delete {
  padding: 12px;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 48%;
}

.btn-save {
  background-color: #4CAF50;
  color: white;
}

.btn-save:disabled {
  background-color: #ddd;
  cursor: not-allowed;
}

.btn-save:hover {
  background-color: #45a049;
}

.btn-delete {
  background-color: #f44336;
  color: white;
}

.btn-delete:hover {
  background-color: #e53935;
}
</style>