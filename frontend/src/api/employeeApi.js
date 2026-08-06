import axiosClient from "./axiosClient";

export const getAllEmployeesApi = () => {
  return axiosClient.get("/employees");
};

export const getEmployeeByIdApi = (id) => {
  return axiosClient.get(`/employees/${id}`);
};

export const createEmployeeApi = (employee) => {
  return axiosClient.post("/employees", employee);
};

export const updateEmployeeApi = (id, employee) => {
  return axiosClient.put(`/employees/${id}`, employee);
};

export const deleteEmployeeApi = (id) => {
  return axiosClient.delete(`/employees/${id}`);
};