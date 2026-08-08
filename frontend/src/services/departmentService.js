import {
  getDepartments,
  getDepartmentById,
  createDepartment,
  updateDepartment,
  deleteDepartment,
} from "../api/departmentApi";

export const fetchDepartments = async () => {
  return await getDepartments();
};

export const fetchDepartmentById = async (id) => {
  return await getDepartmentById(id);
};

export const addDepartment = async (department) => {
  return await createDepartment(department);
};

export const editDepartment = async (
  id,
  department
) => {
  return await updateDepartment(id, department);
};

export const removeDepartment = async (id) => {
  return await deleteDepartment(id);
};