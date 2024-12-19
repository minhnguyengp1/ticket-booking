import axios from 'axios';

const url = "http://localhost:8080";  // URL của server, đảm bảo là URL chính xác

const Api = axios.create({
    baseURL: url,
});

// Interceptor để thêm Authorization header
Api.interceptors.request.use(async (config) => {
    const token = localStorage.getItem("token"); // Lấy token từ localStorage

    if (token) {
        config.headers["Authorization"] = `Bearer ${token}`; // Thêm Authorization header
    }

    return config;
}, (error) => {
    return Promise.reject(error); // Xử lý lỗi request
});

// Interceptor để xử lý phản hồi
Api.interceptors.response.use(
    (response) => response.data, // Trả về dữ liệu từ response
    (error) => Promise.reject(error) // Trả về lỗi nếu có
);

export { Api };
