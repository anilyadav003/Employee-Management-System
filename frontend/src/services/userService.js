import {
  getAllUsersApi,
  getUserByIdApi,
  createUserApi,
  updateUserApi,
  deleteUserApi,
} from "../api/userApi";

export const getAllUsers = async () => {
  const response = await getAllUsersApi();
  return response.data;
};

export const getUserById = async (id) => {
  const response = await getUserByIdApi(id);
  return response.data;
};

export const createUser = async (user) => {
  const response = await createUserApi(user);
  return response.data;
};

export const updateUser = async (id, user) => {
  const response = await updateUserApi(id, user);
  return response.data;
};

export const deleteUser = async (id) => {
  await deleteUserApi(id);
};