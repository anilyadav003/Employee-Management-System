import {
  getAttendance,
  getAttendanceById,
  getAttendanceByEmployee,
  getAttendanceByDate,
  createAttendance,
  updateAttendance,
  deleteAttendance,
} from "../api/attendanceApi";

export const fetchAttendance = async () => {
  return await getAttendance();
};

export const fetchAttendanceById = async (
  id
) => {
  return await getAttendanceById(id);
};

export const fetchAttendanceByEmployee = async (
  employeeId
) => {
  return await getAttendanceByEmployee(
    employeeId
  );
};

export const fetchAttendanceByDate = async (
  date
) => {
  return await getAttendanceByDate(date);
};

export const addAttendance = async (
  attendance
) => {
  return await createAttendance(attendance);
};

export const editAttendance = async (
  id,
  attendance
) => {
  return await updateAttendance(
    id,
    attendance
  );
};

export const removeAttendance = async (
  id
) => {
  return await deleteAttendance(id);
};