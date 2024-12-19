<template>
  <div class="container">
    <h2 class="title">Create New Event</h2>

    <div class="form-group">
      <label for="name" class="label">Name</label>
      <input
          v-model="name"
          id="name"
          type="text"
          placeholder="Event name"
          class="input"
      />
    </div>

    <div class="form-group">
      <label for="location" class="label">Location</label>
      <input
          v-model="location"
          id="location"
          type="text"
          placeholder="Event location"
          class="input"
      />
    </div>

    <div class="form-group">
      <label for="date" class="label">Date</label>
      <input
          id="date"
          type="datetime-local"
          v-model="date"
          class="input"
      />
    </div>

    <button
        :disabled="isSubmitting"
        @click="onSubmit"
        class="btn"
    >
      Save
    </button>
  </div>
</template>

<script>
import { eventService } from '@/services/events';

export default {
  name: "EventCreate",
  data() {
    return {
      name: '',
      location: '',
      date: '',
      isSubmitting: false,
    };
  },
  methods: {
    async onSubmit() {
      try {
        this.isSubmitting = true;
        await eventService.createOne(this.name, this.location, this.date);
        this.$router.push('/events');
      } catch (error) {
        alert('Failed to create event');
      } finally {
        this.isSubmitting = false;
      }
    },
  },
};
</script>

<style scoped>
.container {
  margin: 20px;
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.label {
  font-size: 14px;
  color: gray;
  margin-left: 10px;
}

.input {
  padding: 14px;
  height: 48px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-left: 10px;
}

.btn {
  padding: 12px;
  background-color: #4CAF50;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  margin-top: auto;
}

.btn:disabled {
  background-color: #ddd;
  cursor: not-allowed;
}
</style>
