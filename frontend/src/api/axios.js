import axios from "axios";
import { API_BASE } from "../config";

// Create Axios instance with base URL
const api = axios.create({
  baseURL: API_BASE
});

// Request interceptor: attach token if it exists
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor (optional: handle global errors)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401 || error.response.status === 403) {
      // Token expired or invalid -> redirect to login
      localStorage.removeItem("token");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default api;

