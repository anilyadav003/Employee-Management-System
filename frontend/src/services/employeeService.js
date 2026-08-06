import {
  getAllEmployeesApi,
  getEmployeeByIdApi,
  createEmployeeApi,
  updateEmployeeApi,
  deleteEmployeeApi,
} from "../api/employeeApi";

export const getAllEmployees = async () => {
  const response = await getAllEmployeesApi();
  return response.data;
};

export const getEmployeeById = async (id) => {
  const response = await getEmployeeByIdApi(id);
  return response.data;
};

export const createEmployee = async (employee) => {
  const response = await createEmployeeApi(employee);
  return response.data;
};

export const updateEmployee = async (id, employee) => {
  const response = await updateEmployeeApi(id, employee);
  return response.data;
};

export const deleteEmployee = async (id) => {
  await deleteEmployeeApi(id);
};