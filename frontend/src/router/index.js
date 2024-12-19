import {createRouter, createWebHistory} from 'vue-router';
import LoginView from "@/views/LoginView.vue";
import EventList from "@/views/authed/event/EventList.vue";
import EventDetails from "@/views/authed/event/EventDetails.vue";
import EventCreate from "@/views/authed/event/EventCreate.vue";
import TabLayout from "@/views/authed/TabLayout.vue";
import TicketList from "@/views/authed/ticket/TicketList.vue";
import TicketDetails from "@/views/authed/ticket/TicketDetails.vue";
import ScanTicketView from "@/views/authed/scan-ticket/ScanTicketView.vue";

const routes = [
    {
        path: '/login',
        name: 'login',
        component: LoginView,
    },
    {
        path: '/',
        component: TabLayout,
        children: [
            {
                path: '/events',
                name: 'events',
                component: EventList,
                meta: {requiresAuth: true},
            },
            {
                path: '/events/:id',
                name: 'event-detail',
                component: EventDetails,
                meta: {requiresAuth: true},
            },
            {
                path: '/events/create',
                name: 'event-create',
                component: EventCreate,
                meta: {requiresAuth: true},
            },
            {
                path: 'tickets',
                name: 'tickets',
                component: TicketList,
            },
            {
                path: 'tickets/:id',
                name: 'ticket-details',
                component: TicketDetails,
            },
            {
                path: 'scan-ticket',
                name: 'scan-ticket-view',
                component: ScanTicketView,
            },
        ]
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

// // Thêm navigation guard để bảo vệ các route yêu cầu đăng nhập
// router.beforeEach((to, from, next) => {
//     if (to.meta.requiresAuth && !store.getters['auth/isLoggedIn']) {
//         next('/login'); // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
//     } else {
//         next(); // Tiếp tục nếu đã đăng nhập
//     }
// });

export default router;