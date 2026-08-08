import axiosClient from "./axiosClient";

export const getLeaves = async () => {
  const response = await axiosClient.get("/leaves");

  return response.data;
};

export const getLeaveById = async (id) => {
  const response = await axiosClient.get(
    `/leaves/${id}`
  );

  return response.data;
};

export const getLeavesByEmployee = async (
  employeeId
) => {
  const response = await axiosClient.get(
    `/leaves/employee/${employeeId}`
  );

  return response.data;
};

export const createLeave = async (leave) => {
  const response = await axiosClient.post(
    "/leaves",
    leave
  );

  return response.data;
};

export const updateLeave = async (
  id,
  leave
) => {
  const response = await axiosClient.put(
    `/leaves/${id}`,
    leave
  );

  return response.data;
};

export const deleteLeave = async (id) => {
  const response = await axiosClient.delete(
    `/leaves/${id}`
  );

  return response.data;
};