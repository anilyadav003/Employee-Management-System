import { useEffect, useState } from "react";

import {
  Grid,
  TextField,
  Button,
  MenuItem,
  CircularProgress,
  Alert,
  FormControl,
  InputLabel,
  Select,
  FormHelperText,
} from "@mui/material";

import axiosClient from "../../api/axiosClient";

const initialState = {
  firstName: "",
  lastName: "",
  employeeCode: "",
  designation: "",
  salary: "",
  departmentId: "",
  userId: "",
  dateOfJoining: "",
};

function EmployeeForm({
  employee,
  onSubmit,
  submitLabel = "Save Employee",
}) {
  const [formData, setFormData] =
    useState(initialState);

  const [departments, setDepartments] =
    useState([]);

  const [users, setUsers] =
    useState([]);

  const [assignedUserIds, setAssignedUserIds] =
    useState([]);

  const [loadingData, setLoadingData] =
    useState(true);

  const [loading, setLoading] =
    useState(false);

  const [loadError, setLoadError] =
    useState("");

  /*
   * ============================================================
   * LOAD DEPARTMENTS, USERS AND EXISTING EMPLOYEES
   * ============================================================
   */
  useEffect(() => {
    const loadFormData = async () => {
      try {
        setLoadingData(true);
        setLoadError("");

        const [
          departmentsResponse,
          usersResponse,
          employeesResponse,
        ] = await Promise.all([
          axiosClient.get("/departments"),
          axiosClient.get("/users"),
          axiosClient.get("/employees"),
        ]);

        const departmentData =
          Array.isArray(departmentsResponse.data)
            ? departmentsResponse.data
            : [];

        const userData =
          Array.isArray(usersResponse.data)
            ? usersResponse.data
            : [];

        const employeeData =
          Array.isArray(employeesResponse.data)
            ? employeesResponse.data
            : [];

        setDepartments(departmentData);
        setUsers(userData);

        /*
         * Find users already linked to employees.
         *
         * We don't want the same user to be assigned
         * to two different employees because Employee.user
         * is a one-to-one relationship in the backend.
         */
        const assignedIds = employeeData
          .filter(
            (item) =>
              item.userId !== null &&
              item.userId !== undefined
          )
          .map((item) => Number(item.userId));

        setAssignedUserIds(assignedIds);
      } catch (error) {
        console.error(
          "Failed to load employee form data:",
          error
        );

        setLoadError(
          error.response?.data?.message ||
            "Failed to load departments and users."
        );
      } finally {
        setLoadingData(false);
      }
    };

    loadFormData();
  }, []);

  /*
   * ============================================================
   * LOAD EMPLOYEE DATA WHEN EDITING
   * ============================================================
   */
  useEffect(() => {
    if (employee) {
      setFormData({
        firstName:
          employee.firstName || "",

        lastName:
          employee.lastName || "",

        employeeCode:
          employee.employeeCode || "",

        designation:
          employee.designation || "",

        salary:
          employee.salary ?? "",

        departmentId:
          employee.departmentId ?? "",

        userId:
          employee.userId ?? "",

        dateOfJoining:
          employee.dateOfJoining || "",
      });
    } else {
      setFormData({
        ...initialState,
      });
    }
  }, [employee]);

  /*
   * ============================================================
   * HANDLE FIELD CHANGE
   * ============================================================
   */
  const handleChange = (event) => {
    const {
      name,
      value,
    } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  /*
   * ============================================================
   * SUBMIT FORM
   * ============================================================
   */
  const handleSubmit = async (event) => {
    event.preventDefault();

    /*
     * Basic frontend validation
     */
    if (!formData.departmentId) {
      return;
    }

    if (!formData.userId) {
      return;
    }

    try {
      setLoading(true);

      await onSubmit({
        firstName:
          formData.firstName.trim(),

        lastName:
          formData.lastName.trim(),

        employeeCode:
          formData.employeeCode.trim(),

        designation:
          formData.designation.trim(),

        salary:
          Number(formData.salary),

        departmentId:
          Number(formData.departmentId),

        userId:
          Number(formData.userId),

        dateOfJoining:
          formData.dateOfJoining,
      });
    } finally {
      setLoading(false);
    }
  };

  /*
   * ============================================================
   * USERS AVAILABLE FOR SELECTION
   *
   * When editing an employee, that employee's current user
   * must remain available.
   * ============================================================
   */
  const availableUsers = users.filter((user) => {
    const userId = Number(user.id);

    const currentEmployeeUserId =
      employee?.userId !== undefined &&
      employee?.userId !== null
        ? Number(employee.userId)
        : null;

    /*
     * Keep the currently assigned user available
     * when editing.
     */
    if (
      currentEmployeeUserId !== null &&
      userId === currentEmployeeUserId
    ) {
      return true;
    }

    /*
     * Otherwise only show users who are not already
     * assigned to another employee.
     */
    return !assignedUserIds.includes(userId);
  });

  /*
   * ============================================================
   * LOADING STATE
   * ============================================================
   */
  if (loadingData) {
    return (
      <Grid
        container
        justifyContent="center"
        sx={{ py: 5 }}
      >
        <CircularProgress />
      </Grid>
    );
  }

  /*
   * ============================================================
   * ERROR STATE
   * ============================================================
   */
  if (loadError) {
    return (
      <Alert
        severity="error"
        sx={{ mt: 2 }}
      >
        {loadError}
      </Alert>
    );
  }

  return (
    <form onSubmit={handleSubmit}>
      <Grid
        container
        spacing={2}
        sx={{ mt: 1 }}
      >

        {/* FIRST NAME */}
        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="First Name"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
          />
        </Grid>

        {/* LAST NAME */}
        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="Last Name"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
          />
        </Grid>

        {/* EMPLOYEE CODE */}
        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="Employee Code"
            name="employeeCode"
            value={formData.employeeCode}
            onChange={handleChange}
            placeholder="EMP001"
          />
        </Grid>

        {/* DESIGNATION */}
        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="Designation"
            name="designation"
            value={formData.designation}
            onChange={handleChange}
            placeholder="Software Engineer"
          />
        </Grid>

        {/* SALARY */}
        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            type="number"
            label="Salary"
            name="salary"
            value={formData.salary}
            onChange={handleChange}
            inputProps={{
              min: 1,
            }}
          />
        </Grid>

        {/* DEPARTMENT */}
        <Grid size={{ xs: 12, md: 6 }}>
          <FormControl
            fullWidth
            required
          >
            <InputLabel>
              Department
            </InputLabel>

            <Select
              name="departmentId"
              value={formData.departmentId}
              label="Department"
              onChange={handleChange}
            >
              <MenuItem value="">
                <em>
                  Select Department
                </em>
              </MenuItem>

              {departments.map(
                (department) => (
                  <MenuItem
                    key={department.id}
                    value={department.id}
                  >
                    {department.name}
                    {department.code
                      ? ` (${department.code})`
                      : ""}
                  </MenuItem>
                )
              )}
            </Select>

            <FormHelperText>
              Select the employee's department.
            </FormHelperText>
          </FormControl>
        </Grid>

        {/* USER */}
        <Grid size={{ xs: 12, md: 6 }}>
          <FormControl
            fullWidth
            required
          >
            <InputLabel>
              User
            </InputLabel>

            <Select
              name="userId"
              value={formData.userId}
              label="User"
              onChange={handleChange}
            >
              <MenuItem value="">
                <em>
                  Select User
                </em>
              </MenuItem>

              {availableUsers.map(
                (user) => (
                  <MenuItem
                    key={user.id}
                    value={user.id}
                  >
                    {user.username}
                    {user.email
                      ? ` - ${user.email}`
                      : ""}
                  </MenuItem>
                )
              )}
            </Select>

            <FormHelperText>
              Select the system user associated
              with this employee.
            </FormHelperText>
          </FormControl>
        </Grid>

        {/* JOINING DATE */}
        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            type="date"
            label="Joining Date"
            name="dateOfJoining"
            value={formData.dateOfJoining}
            onChange={handleChange}
            slotProps={{
              inputLabel: {
                shrink: true,
              },
            }}
          />
        </Grid>

        {/* SUBMIT */}
        <Grid size={{ xs: 12 }}>
          <Button
            fullWidth
            type="submit"
            variant="contained"
            disabled={loading}
            sx={{
              mt: 2,
              py: 1.5,
              borderRadius: 2,
            }}
          >
            {loading
              ? "Saving..."
              : submitLabel}
          </Button>
        </Grid>

      </Grid>
    </form>
  );
}

export default EmployeeForm;