import axiosClient from "./axiosClient";

export const getAllUsersApi = () => {
  return axiosClient.get("/users");
};

export const getUserByIdApi = (id) => {
  return axiosClient.get(`/users/${id}`);
};

export const createUserApi = (user) => {
  return axiosClient.post("/users", user);
};

export const updateUserApi = (id, user) => {
  return axiosClient.put(`/users/${id}`, user);
};

export const deleteUserApi = (id) => {
  return axiosClient.delete(`/users/${id}`);
};