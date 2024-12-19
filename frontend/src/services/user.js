import {Api} from './api'; // Import API instance

async function login(email, password) {
    try {
        // Gọi API đăng nhập và trả về kết quả
        return await Api.post('/auth/login', { email, password });
    } catch (error) {
        console.error("Login failed:", error);
        throw error; // Đảm bảo bắt lỗi nếu đăng nhập thất bại
    }
}

async function register(email, password) {
    try {
        return await Api.post('/auth/register', {email, password}); // Trả về dữ liệu response nếu cần thiết
    } catch (error) {
        console.error("Registration failed:", error);
        throw error;
    }
}

const userService = {
    login,
    register,
};

export {userService};
