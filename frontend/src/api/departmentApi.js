import axiosClient from "./axiosClient";

export const getDepartments = async () => {
  const response = await axiosClient.get("/departments");
  return response.data;
};

export const getDepartmentById = async (id) => {
  const response = await axiosClient.get(`/departments/${id}`);
  return response.data;
};

export const createDepartment = async (department) => {
  const response = await axiosClient.post(
    "/departments",
    department
  );

  return response.data;
};

export const updateDepartment = async (
  id,
  department
) => {
  const response = await axiosClient.put(
    `/departments/${id}`,
    department
  );

  return response.data;
};

export const deleteDepartment = async (id) => {
  const response = await axiosClient.delete(
    `/departments/${id}`
  );

  return response.data;
};