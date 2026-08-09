import {
  getDashboardApi,
} from "../api/dashboardApi";

export const getDashboard = async () => {
  const response = await getDashboardApi();

  return response.data;
};