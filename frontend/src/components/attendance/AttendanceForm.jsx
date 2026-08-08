import { useEffect, useState } from "react";

import {
  Grid,
  TextField,
  Button,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  FormHelperText,
} from "@mui/material";

const initialState = {
  employeeId: "",
  attendanceDate: "",
  checkInTime: "",
  checkOutTime: "",
  status: "",
};

const attendanceStatuses = [
  {
    value: "PRESENT",
    label: "Present",
  },
  {
    value: "ABSENT",
    label: "Absent",
  },
  {
    value: "HALF_DAY",
    label: "Half Day",
  },
  {
    value: "LEAVE",
    label: "Leave",
  },
];

function AttendanceForm({
  attendance,
  employees,
  onSubmit,
  submitLabel = "Save Attendance",
}) {
  const [formData, setFormData] =
    useState(initialState);

  const [loading, setLoading] =
    useState(false);

  useEffect(() => {
    if (attendance) {
      setFormData({
        employeeId:
          attendance.employeeId ?? "",

        attendanceDate:
          attendance.attendanceDate || "",

        checkInTime:
          attendance.checkInTime || "",

        checkOutTime:
          attendance.checkOutTime || "",

        status:
          attendance.status || "",
      });
    } else {
      setFormData({
        ...initialState,
      });
    }
  }, [attendance]);

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

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);

      await onSubmit({
        employeeId:
          Number(formData.employeeId),

        attendanceDate:
          formData.attendanceDate,

        checkInTime:
          formData.checkInTime || null,

        checkOutTime:
          formData.checkOutTime || null,

        status:
          formData.status,
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <Grid
        container
        spacing={2}
        sx={{ mt: 1 }}
      >

        {/* EMPLOYEE */}

        <Grid size={{ xs: 12 }}>
          <FormControl
            fullWidth
            required
          >
            <InputLabel>
              Employee
            </InputLabel>

            <Select
              name="employeeId"
              value={formData.employeeId}
              label="Employee"
              onChange={handleChange}
            >
              <MenuItem value="">
                <em>
                  Select Employee
                </em>
              </MenuItem>

              {employees.map(
                (employee) => (
                  <MenuItem
                    key={employee.id}
                    value={employee.id}
                  >
                    {employee.firstName}{" "}
                    {employee.lastName}
                    {" - "}
                    {employee.employeeCode}
                  </MenuItem>
                )
              )}
            </Select>

            <FormHelperText>
              Select the employee.
            </FormHelperText>
          </FormControl>
        </Grid>

        {/* DATE */}

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            type="date"
            label="Attendance Date"
            name="attendanceDate"
            value={
              formData.attendanceDate
            }
            onChange={handleChange}
            slotProps={{
              inputLabel: {
                shrink: true,
              },
            }}
          />
        </Grid>

        {/* STATUS */}

        <Grid size={{ xs: 12, md: 6 }}>
          <FormControl
            fullWidth
            required
          >
            <InputLabel>
              Status
            </InputLabel>

            <Select
              name="status"
              value={formData.status}
              label="Status"
              onChange={handleChange}
            >
              <MenuItem value="">
                <em>
                  Select Status
                </em>
              </MenuItem>

              {attendanceStatuses.map(
                (status) => (
                  <MenuItem
                    key={status.value}
                    value={status.value}
                  >
                    {status.label}
                  </MenuItem>
                )
              )}
            </Select>
          </FormControl>
        </Grid>

        {/* CHECK IN */}

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            type="time"
            label="Check In"
            name="checkInTime"
            value={
              formData.checkInTime
            }
            onChange={handleChange}
            required
            slotProps={{
              inputLabel: {
                shrink: true,
              },
            }}
          />
        </Grid>

        {/* CHECK OUT */}

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            type="time"
            label="Check Out"
            name="checkOutTime"
            value={
              formData.checkOutTime
            }
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
              mt: 1,
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

export default AttendanceForm;