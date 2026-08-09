const TOKEN_KEY = "ems_access_token";
const USERNAME_KEY = "ems_username";
const ROLE_KEY = "ems_role";

export const saveAuthData = (data) => {
  if (!data?.token) {
    throw new Error("Authentication token is missing.");
  }

  localStorage.setItem(TOKEN_KEY, data.token);

  if (data.username) {
    localStorage.setItem(USERNAME_KEY, data.username);
  }

  if (data.role) {
    localStorage.setItem(ROLE_KEY, data.role);
  }
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

export const isAuthenticated = () => {
  return Boolean(getToken());
};

export const clearAuthData = () => {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USERNAME_KEY);
  localStorage.removeItem(ROLE_KEY);

  // Remove the old keys that were used during debugging.
  localStorage.removeItem("token");
  localStorage.removeItem("username");
  localStorage.removeItem("role");
};