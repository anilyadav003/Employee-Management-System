import axios from "axios";

import {
  getToken,
  clearAuthData,
} from "../utils/tokenStorage";

const API_BASE_URL = import.meta.env.DEV
  ? "http://localhost:8081/api/v1"
  : "/api/v1";

const axiosClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

axiosClient.interceptors.request.use(
  (config) => {
    const token = getToken();

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error.response?.status;

    if (status === 401 && getToken()) {
      clearAuthData();

      if (window.location.pathname !== "/") {
        window.location.href = "/";
      }
    }

    return Promise.reject(error);
  }
);

export default axiosClient;