<template>
  <div>
    <!-- Tab navigation bar -->
    <div class="tabs">
      <router-link
          v-for="tab in filteredTabs"
          :key="tab.name"
          :to="tab.href"
          :class="['tab-item', { active: $route.path === tab.href }]">
<!--        <TabBarIcon :name="tab.icon" :color="tabColor($route.path === tab.href)" />-->
        <span :style="{ color: tabColor($route.path === tab.href) }">{{ tab.displayName }}</span>
      </router-link>
    </div>

    <!-- Tab content with padding to avoid overlap -->
    <div class="content">
      <router-view />
    </div>
  </div>
</template>

<script>
// import TabBarIcon from '@/components/navigation/TabBarIcon';

import {mapGetters} from "vuex";

export default {
  name: 'TabLayout',
  // components: {
  //   TabBarIcon
  // },
  computed: {
    // Lấy thông tin người dùng từ Vuex
    ...mapGetters('auth', ['user', 'isLoggedIn']),  // Kết nối với Vuex getters
    userRole() {
      console.log("ROLE: " + this.user.role)
      return this.user?.role || null;
    },
    // Lọc các tab hiển thị theo vai trò của người dùng
    filteredTabs() {
      console.log('User:', JSON.stringify(this.user)); // In ra user ở đây
      if (!this.user) return [];  // Nếu user chưa đăng nhập, không hiển thị tab

      const tabs = [
        {
          showFor: ["ATTENDEE", "MANAGER"],
          name: '(events)',
          displayName: 'Events',
          icon: 'calendar',
          href: '/events',
          options: {
            headerShown: false
          }
        },
        {
          showFor: ["ATTENDEE"],
          name: '(tickets)',
          displayName: 'My Tickets',
          icon: 'ticket',
          href: '/tickets',
          options: {
            headerShown: false
          }
        },
        {
          showFor: ["MANAGER"],
          name: 'scan-ticket',
          displayName: 'Scan Ticket',
          icon: 'scan',
          href: '/scan-ticket',
          options: {
            headerShown: true
          }
        },
        {
          showFor: ["ATTENDEE", "MANAGER"],
          name: 'settings',
          displayName: 'Settings',
          icon: 'cog',
          href: '/settings',
          options: {
            headerShown: true
          }
        }
      ];

      // Lọc các tab dựa trên vai trò của người dùng
      return tabs.filter(tab => tab.showFor.includes(this.user?.role));
    }
  },
  methods: {
    // Hàm xác định màu tab nếu nó đang được chọn
    tabColor(isActive) {
      return isActive ? 'black' : 'gray';
    }
  }
};
</script>

<style scoped>
.tabs {
  display: flex;
  justify-content: space-around;
  background-color: #f0f0f0;
  padding: 10px;
  position: fixed; /* Cố định vị trí */
  top: 0; /* Nằm ở đầu trang */
  left: 0;
  width: 100%; /* Chiều rộng đầy đủ */
  z-index: 1000; /* Đảm bảo nằm trên các phần tử khác */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Thêm bóng cho đẹp */
}

.tab-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-decoration: none;
}

.tab-item.active {
  color: black;
}

.tab-item span {
  font-size: 12px;
}

.tab-item svg {
  font-size: 24px;
}

.content {
  padding-top: 10px; /* Điều chỉnh để tránh bị thanh tabs che */
}
</style>
