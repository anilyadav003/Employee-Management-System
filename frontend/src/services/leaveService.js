import {
  getLeaves,
  getLeaveById,
  getLeavesByEmployee,
  createLeave,
  updateLeave,
  deleteLeave,
} from "../api/leaveApi";

export const fetchLeaves = async () => {
  return await getLeaves();
};

export const fetchLeaveById = async (id) => {
  return await getLeaveById(id);
};

export const fetchLeavesByEmployee = async (
  employeeId
) => {
  return await getLeavesByEmployee(
    employeeId
  );
};

export const addLeave = async (leave) => {
  return await createLeave(leave);
};

export const editLeave = async (
  id,
  leave
) => {
  return await updateLeave(id, leave);
};

export const removeLeave = async (id) => {
  return await deleteLeave(id);
};