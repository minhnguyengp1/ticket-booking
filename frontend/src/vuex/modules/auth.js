import { userService } from '@/services/user';  // Import userService

export default {
    namespaced: true,
    state: {
        isLoggedIn: false,
        isLoadingAuth: false,
        user: null,
    },
    mutations: {
        setIsLoggedIn(state, status) {
            state.isLoggedIn = status;
        },
        setUser(state, user) {
            state.user = user;
        },
        setLoadingAuth(state, status) {
            state.isLoadingAuth = status;
        },
    },
    actions: {
        async authenticate({ commit }, { authMode, email, password }) {
            commit('setLoadingAuth', true);

            try {
                const response = await userService[authMode](email, password); // Gọi userService.login hoặc userService.register

                if (response) {
                    commit('setIsLoggedIn', true);
                    commit('setUser', response.user);

                    // Lưu token và user vào localStorage
                    localStorage.setItem('token', response.token);
                    localStorage.setItem('user', JSON.stringify(response.user));
                }
            } catch (error) {
                console.error('Authentication failed', error);
                commit('setIsLoggedIn', false);
            } finally {
                commit('setLoadingAuth', false);
            }
        },

        async logout({ commit }) {
            commit('setIsLoggedIn', false);
            commit('setUser', null);

            // Xóa token và user khỏi localStorage
            localStorage.removeItem('token');
            localStorage.removeItem('user');
        },

        checkLoginStatus({ commit }) {
            const token = localStorage.getItem('token');
            const user = localStorage.getItem('user');

            if (token && user) {
                commit('setIsLoggedIn', true);
                commit('setUser', JSON.parse(user));
            } else {
                commit('setIsLoggedIn', false);
            }
        },
    },
    getters: {
        isLoggedIn(state) {
            return state.isLoggedIn;
        },
        user(state) {
            return state.user;
        },
        isLoadingAuth(state) {
            return state.isLoadingAuth;
        },
    },
};
