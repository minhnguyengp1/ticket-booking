<template>
  <div>
    <div>
      <!-- Kiểm tra trình duyệt và hiển thị component DateTimePicker -->
      <Vue3Datepicker
          v-model="currentDate"
          :min-date="minDate"
          format="yyyy-MM-dd"
          @change="onChange"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import Vue3Datepicker from 'vue3-datepicker';
import 'vue3-datepicker/dist/vue3-datepicker.css';

export default defineComponent({
  name: 'DateTimePicker',
  components: {
    Vue3Datepicker
  },
  props: {
    currentDate: {
      type: Date,
      required: true
    }
  },
  setup(props, { emit }) {
    const minDate = new Date(); // Ngày tối thiểu là ngày hiện tại

    const onChange = (date: Date) => {
      emit('change', date); // Gửi giá trị ngày mới ra ngoài
    };

    return {
      minDate,
      onChange
    };
  }
});
</script>

<style scoped>
/* Bạn có thể thêm các kiểu CSS tùy chỉnh ở đây nếu cần */
</style>
