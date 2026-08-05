const TOKEN_KEY = "ems_access_token";
const USERNAME_KEY = "ems_username";
const ROLE_KEY = "ems_role";

export const saveAuthData = (data) => {
  localStorage.setItem(TOKEN_KEY, data.token);
  localStorage.setItem(USERNAME_KEY, data.username);
  localStorage.setItem(ROLE_KEY, data.role);
};

export const getToken = () => {
  return localStorage.getItem(TOKEN_KEY);
};

export const getUsername = () => {
  return localStorage.getItem(USERNAME_KEY);
};

export const getRole = () => {
  return localStorage.getItem(ROLE_KEY);
};

export const clearAuthData = () => {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USERNAME_KEY);
  localStorage.removeItem(ROLE_KEY);
};