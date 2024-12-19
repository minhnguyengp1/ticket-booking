<template>
  <button
      :disabled="isLoading"
      :class="['button', variant, isLoading ? 'disabled' : '']"
      @click="onPress"
      v-bind="$attrs"
  >
  <span v-if="isLoading" class="loading">Loading...</span>
  <span v-else>
      <slot></slot>  <!-- Use slot to render children -->
    </span>
  </button>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue';

export default defineComponent({
  name: 'Button',
  props: {
    variant: {
      type: String,
      default: 'contained', // Default is 'contained'
    },
    isLoading: {
      type: Boolean,
      default: false, // Default is false
    },
  },
  setup(props, { emit, slots, attrs }) {
    // Handle button click
    const onPress = () => {
      if (!props.isLoading && attrs.onClick) {
        // Kiểm tra xem onClick có tồn tại trong attrs và gọi nó
        const clickHandler = attrs.onClick;
        if (clickHandler && typeof clickHandler === 'function') {
          clickHandler();
        }
      }
    };

    return {
      onPress,
    };
  },
});
</script>

<style scoped>
/* Base styles for the button */
.button {
  padding: 14px;
  border-radius: 50px;
  font-size: 18px;
  text-align: center;
  cursor: pointer;
}

.button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

/* Contained style */
.contained {
  background-color: black;
  color: white;
  border: none;
}

/* Outlined style */
.outlined {
  background-color: transparent;
  color: black;
  border: 1px solid darkgray;
}

/* Ghost style */
.ghost {
  background-color: transparent;
  color: black;
  border: none;
}

/* Loading style */
.loading {
  color: gray;
}
</style>
