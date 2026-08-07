import { useState } from "react";

import {
  Grid,
  TextField,
  Button,
} from "@mui/material";

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

function EmployeeForm({ onSubmit }) {
  const [employee, setEmployee] = useState(initialState);

  const [loading, setLoading] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setEmployee((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);

      await onSubmit({
        ...employee,
        salary: Number(employee.salary),
        departmentId: Number(employee.departmentId),
        userId: Number(employee.userId),
      });

      setEmployee(initialState);
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
        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="First Name"
            name="firstName"
            value={employee.firstName}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="Last Name"
            name="lastName"
            value={employee.lastName}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="Employee Code"
            name="employeeCode"
            value={employee.employeeCode}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="Designation"
            name="designation"
            value={employee.designation}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            type="number"
            label="Salary"
            name="salary"
            value={employee.salary}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            type="number"
            label="Department ID"
            name="departmentId"
            value={employee.departmentId}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            type="number"
            label="User ID"
            name="userId"
            value={employee.userId}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            type="date"
            label="Joining Date"
            name="dateOfJoining"
            value={employee.dateOfJoining}
            onChange={handleChange}
            slotProps={{
              inputLabel: {
                shrink: true,
              },
            }}
          />
        </Grid>

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
            {loading ? "Saving..." : "Save Employee"}
          </Button>
        </Grid>
      </Grid>
    </form>
  );
}

export default EmployeeForm;