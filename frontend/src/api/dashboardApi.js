import axiosClient from "./axiosClient";

export const getDashboardApi = () => {
  return axiosClient.get("/dashboard");
};