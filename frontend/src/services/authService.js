import { loginApi } from "../api/authApi";

export const login = async (credentials) => {
  const response = await loginApi(credentials);
  return response.data;
};