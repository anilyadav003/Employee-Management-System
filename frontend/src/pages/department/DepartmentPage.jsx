import { useEffect, useMemo, useState } from "react";

import {
  Box,
  Typography,
  Alert,
  Snackbar,
  CircularProgress,
} from "@mui/material";

import DepartmentToolbar from "../../components/department/DepartmentToolbar";
import DepartmentTable from "../../components/department/DepartmentTable";
import DepartmentDialog from "../../components/department/DepartmentDialog";

import {
  fetchDepartments,
  addDepartment,
  editDepartment,
  removeDepartment,
} from "../../services/departmentService";

function DepartmentPage() {
  const [departments, setDepartments] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [search, setSearch] =
    useState("");

  const [dialogOpen, setDialogOpen] =
    useState(false);

  const [selectedDepartment, setSelectedDepartment] =
    useState(null);

  const [successMessage, setSuccessMessage] =
    useState("");

  const loadDepartments = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await fetchDepartments();

      setDepartments(
        Array.isArray(data) ? data : []
      );
    } catch (err) {
      console.error(
        "Failed to load departments:",
        err
      );

      setError(
        err.response?.data?.message ||
          "Failed to load departments."
      );
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDepartments();
  }, []);

  const filteredDepartments = useMemo(() => {
    const searchValue =
      search.trim().toLowerCase();

    if (!searchValue) {
      return departments;
    }

    return departments.filter(
      (department) =>
        department.name
          ?.toLowerCase()
          .includes(searchValue) ||
        department.code
          ?.toLowerCase()
          .includes(searchValue) ||
        department.description
          ?.toLowerCase()
          .includes(searchValue)
    );
  }, [departments, search]);

  const handleAdd = () => {
    setSelectedDepartment(null);
    setDialogOpen(true);
  };

  const handleEdit = (department) => {
    setSelectedDepartment(department);
    setDialogOpen(true);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
    setSelectedDepartment(null);
  };

  const handleSubmit = async (data) => {
    try {
      if (selectedDepartment) {
        await editDepartment(
          selectedDepartment.id,
          data
        );

        setSuccessMessage(
          "Department updated successfully."
        );
      } else {
        await addDepartment(data);

        setSuccessMessage(
          "Department created successfully."
        );
      }

      handleCloseDialog();

      await loadDepartments();
    } catch (err) {
      console.error(
        "Failed to save department:",
        err
      );

      throw err;
    }
  };

  const handleDelete = async (department) => {
    const confirmed = window.confirm(
      `Are you sure you want to delete "${department.name}"?`
    );

    if (!confirmed) {
      return;
    }

    try {
      await removeDepartment(
        department.id
      );

      setSuccessMessage(
        "Department deleted successfully."
      );

      await loadDepartments();
    } catch (err) {
      console.error(
        "Failed to delete department:",
        err
      );

      setError(
        err.response?.data?.message ||
          "Failed to delete department."
      );
    }
  };

  return (
    <Box
      sx={{
        flex: 1,
        p: 4,
        bgcolor: "#F5F7FA",
        minHeight: "100%",
      }}
    >
      <Typography
        variant="h4"
        fontWeight="bold"
        sx={{ mb: 1 }}
      >
        Department Management
      </Typography>

      <Typography
        color="text.secondary"
        sx={{ mb: 4 }}
      >
        Manage departments in the Employee
        Management System.
      </Typography>

      {error && (
        <Alert
          severity="error"
          sx={{ mb: 3 }}
          onClose={() => setError("")}
        >
          {error}
        </Alert>
      )}

      <DepartmentToolbar
        search={search}
        onSearchChange={setSearch}
        onAdd={handleAdd}
      />

      {loading ? (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            py: 8,
          }}
        >
          <CircularProgress />
        </Box>
      ) : (
        <DepartmentTable
          departments={filteredDepartments}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      )}

      <DepartmentDialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        department={selectedDepartment}
        onSubmit={handleSubmit}
      />

      <Snackbar
        open={Boolean(successMessage)}
        autoHideDuration={3000}
        onClose={() =>
          setSuccessMessage("")
        }
        message={successMessage}
      />
    </Box>
  );
}

export default DepartmentPage;