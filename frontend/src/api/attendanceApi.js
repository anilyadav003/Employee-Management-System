import axiosClient from "./axiosClient";

export const getAttendance = async () => {
  const response = await axiosClient.get("/attendance");

  return response.data;
};

export const getAttendanceById = async (id) => {
  const response = await axiosClient.get(
    `/attendance/${id}`
  );

  return response.data;
};

export const getAttendanceByEmployee = async (
  employeeId
) => {
  const response = await axiosClient.get(
    `/attendance/employee/${employeeId}`
  );

  return response.data;
};

export const getAttendanceByDate = async (
  date
) => {
  const response = await axiosClient.get(
    `/attendance/date/${date}`
  );

  return response.data;
};

export const createAttendance = async (
  attendance
) => {
  const response = await axiosClient.post(
    "/attendance",
    attendance
  );

  return response.data;
};

export const updateAttendance = async (
  id,
  attendance
) => {
  const response = await axiosClient.put(
    `/attendance/${id}`,
    attendance
  );

  return response.data;
};

export const deleteAttendance = async (id) => {
  const response = await axiosClient.delete(
    `/attendance/${id}`
  );

  return response.data;
};