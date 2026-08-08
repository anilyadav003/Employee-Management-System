import {
  useEffect,
  useMemo,
  useState,
} from "react";

import {
  Alert,
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Typography,
} from "@mui/material";

import EmployeeTable from "../../components/employee/EmployeeTable";
import EmployeeToolbar from "../../components/employee/EmployeeToolbar";
import EmployeeDialog from "../../components/employee/EmployeeDialog";

import {
  getAllEmployees,
  createEmployee,
  updateEmployee,
  deleteEmployee,
} from "../../services/employeeService";

import { toast } from "react-toastify";

function EmployeePage() {
  const [employees, setEmployees] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [search, setSearch] =
    useState("");

  const [dialogOpen, setDialogOpen] =
    useState(false);

  const [dialogMode, setDialogMode] =
    useState("create");

  const [selectedEmployee, setSelectedEmployee] =
    useState(null);

  const [deleteDialogOpen, setDeleteDialogOpen] =
    useState(false);

  const [deleting, setDeleting] =
    useState(false);

  const fetchEmployees = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAllEmployees();

      setEmployees(
        Array.isArray(data) ? data : []
      );
    } catch (err) {
      console.error(err);

      setError(
        err.response?.data?.message ||
          "Failed to load employees."
      );
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  const handleOpenCreate = () => {
    setSelectedEmployee(null);
    setDialogMode("create");
    setDialogOpen(true);
  };

  const handleView = (employee) => {
    setSelectedEmployee(employee);
    setDialogMode("view");
    setDialogOpen(true);
  };

  const handleEdit = (employee) => {
    setSelectedEmployee(employee);
    setDialogMode("edit");
    setDialogOpen(true);
  };

  const handleCreate = async (employee) => {
    try {
      await createEmployee(employee);

      toast.success(
        "Employee created successfully."
      );

      setDialogOpen(false);
      setSelectedEmployee(null);

      await fetchEmployees();
    } catch (err) {
      console.error(err);

      toast.error(
        err.response?.data?.message ||
          "Failed to create employee."
      );

      throw err;
    }
  };

  const handleUpdate = async (employee) => {
    try {
      await updateEmployee(
        selectedEmployee.id,
        employee
      );

      toast.success(
        "Employee updated successfully."
      );

      setDialogOpen(false);
      setSelectedEmployee(null);

      await fetchEmployees();
    } catch (err) {
      console.error(err);

      toast.error(
        err.response?.data?.message ||
          "Failed to update employee."
      );

      throw err;
    }
  };

  const handleDelete = async () => {
    if (!selectedEmployee) {
      return;
    }

    try {
      setDeleting(true);

      await deleteEmployee(
        selectedEmployee.id
      );

      toast.success(
        "Employee deleted successfully."
      );

      setDeleteDialogOpen(false);
      setSelectedEmployee(null);

      await fetchEmployees();
    } catch (err) {
      console.error(err);

      toast.error(
        err.response?.data?.message ||
          "Failed to delete employee."
      );
    } finally {
      setDeleting(false);
    }
  };

  const filteredEmployees = useMemo(() => {
    const keyword =
      search.trim().toLowerCase();

    if (!keyword) {
      return employees;
    }

    return employees.filter((employee) => {
      const values = [
        employee.firstName,
        employee.lastName,
        employee.employeeCode,
        employee.departmentName,
        employee.designation,
        employee.username,
      ];

      return values.some((value) =>
        String(value || "")
          .toLowerCase()
          .includes(keyword)
      );
    });
  }, [employees, search]);

  return (
    <Box
      sx={{
        p: {
          xs: 2,
          md: 4,
        },
      }}
    >
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
        onAddClick={handleOpenCreate}
      />

      {loading && (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            py: 8,
          }}
        >
          <CircularProgress />
        </Box>
      )}

      {!loading && error && (
        <Alert
          severity="error"
          action={
            <Button
              color="inherit"
              size="small"
              onClick={fetchEmployees}
            >
              Retry
            </Button>
          }
        >
          {error}
        </Alert>
      )}

      {!loading && !error && (
        <EmployeeTable
          employees={filteredEmployees}
          onView={handleView}
          onEdit={handleEdit}
          onDelete={(employee) => {
            setSelectedEmployee(employee);
            setDeleteDialogOpen(true);
          }}
        />
      )}

      <EmployeeDialog
        open={dialogOpen}
        onClose={() => {
          setDialogOpen(false);
          setSelectedEmployee(null);
        }}
        employee={selectedEmployee}
        mode={dialogMode}
        onSubmit={
          dialogMode === "edit"
            ? handleUpdate
            : handleCreate
        }
      />

      <Dialog
        open={deleteDialogOpen}
        onClose={() => {
          if (!deleting) {
            setDeleteDialogOpen(false);
            setSelectedEmployee(null);
          }
        }}
        maxWidth="xs"
        fullWidth
      >
        <DialogTitle>
          Delete Employee
        </DialogTitle>

        <DialogContent>
          <DialogContentText>
            Are you sure you want to delete{" "}
            <strong>
              {selectedEmployee?.firstName}{" "}
              {selectedEmployee?.lastName}
            </strong>
            ?
            <br />
            <br />
            Employee Code:{" "}
            <strong>
              {selectedEmployee?.employeeCode}
            </strong>
            <br />
            <br />
            This action cannot be undone.
          </DialogContentText>
        </DialogContent>

        <DialogActions>
          <Button
            onClick={() => {
              setDeleteDialogOpen(false);
              setSelectedEmployee(null);
            }}
            disabled={deleting}
          >
            Cancel
          </Button>

          <Button
            onClick={handleDelete}
            color="error"
            variant="contained"
            disabled={deleting}
          >
            {deleting
              ? "Deleting..."
              : "Delete"}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}

export default EmployeePage;