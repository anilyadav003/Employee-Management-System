import { useEffect, useState } from "react";

import {
  Box,
  Typography,
  CircularProgress,
  Alert,
} from "@mui/material";

import EmployeeTable from "../../components/employee/EmployeeTable";
import EmployeeToolbar from "../../components/employee/EmployeeToolbar";
import EmployeeDialog from "../../components/employee/EmployeeDialog";

import {
  getAllEmployees,
  createEmployee,
} from "../../services/employeeService";

import { toast } from "react-toastify";

function EmployeePage() {
  const [employees, setEmployees] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [search, setSearch] = useState("");

  const [dialogOpen, setDialogOpen] = useState(false);

  useEffect(() => {
    fetchEmployees();
  }, []);

  const fetchEmployees = async () => {
    try {
      setLoading(true);

      const data = await getAllEmployees();

      setEmployees(data);
    } catch (err) {
      console.error(err);

      setError("Failed to load employees.");
    } finally {
      setLoading(false);
    }
  };

  const handleCreateEmployee = async (employee) => {
    try {
      await createEmployee(employee);

      toast.success("Employee created successfully.");

      setDialogOpen(false);

      fetchEmployees();
    } catch (err) {
      console.error(err);

      toast.error(
        err.response?.data?.message ||
          "Failed to create employee."
      );
    }
  };

  const filteredEmployees = employees.filter((employee) => {
    const keyword = search.toLowerCase();

    return (
      employee.firstName
        .toLowerCase()
        .includes(keyword) ||
      employee.lastName
        .toLowerCase()
        .includes(keyword) ||
      employee.employeeCode
        .toLowerCase()
        .includes(keyword) ||
      employee.departmentName
        .toLowerCase()
        .includes(keyword)
    );
  });

  return (
    <Box sx={{ p: 4 }}>
      <Typography
        variant="h4"
        fontWeight="bold"
        sx={{ mb: 3 }}
      >
        Employee Management
      </Typography>

      <EmployeeToolbar
        search={search}
        setSearch={setSearch}
        onAddClick={() => setDialogOpen(true)}
      />

      {loading && (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            mt: 5,
          }}
        >
          <CircularProgress />
        </Box>
      )}

      {!loading && error && (
        <Alert severity="error">
          {error}
        </Alert>
      )}

      {!loading && !error && (
        <EmployeeTable
          employees={filteredEmployees}
        />
      )}

      <EmployeeDialog
        open={dialogOpen}
        onClose={() => setDialogOpen(false)}
        onSubmit={handleCreateEmployee}
      />
    </Box>
  );
}

export default EmployeePage;