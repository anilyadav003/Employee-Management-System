import { useState } from "react";

import {
  Grid,
  TextField,
  Button,
} from "@mui/material";

function EmployeeForm({
  onSubmit,
}) {
  const [employee, setEmployee] = useState({
    firstName: "",
    lastName: "",
    employeeCode: "",
    designation: "",
    salary: "",
    departmentId: "",
    userId: "",
    dateOfJoining: "",
  });

  const handleChange = (event) => {
    setEmployee({
      ...employee,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit(employee);
  };

  return (
    <form onSubmit={handleSubmit}>
      <Grid container spacing={2} sx={{ mt: 1 }}>
        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            label="First Name"
            name="firstName"
            value={employee.firstName}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            label="Last Name"
            name="lastName"
            value={employee.lastName}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            label="Employee Code"
            name="employeeCode"
            value={employee.employeeCode}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            label="Designation"
            name="designation"
            value={employee.designation}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
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
            sx={{
              mt: 2,
              py: 1.5,
              borderRadius: 2,
            }}
          >
            Save Employee
          </Button>
        </Grid>
      </Grid>
    </form>
  );
}

export default EmployeeForm;