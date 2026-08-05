import dashboardApi from "../api/dashboardApi";

export const getDashboard = async () => {

    const response = await dashboardApi();

    return response.data;

};