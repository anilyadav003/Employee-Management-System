import authApi from "./authApi";

const dashboardApi = () => {

    return authApi.get("/dashboard");

};

export default dashboardApi;