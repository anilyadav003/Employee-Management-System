import { useEffect, useState } from "react";

import {
  Box,
  Typography,
  Alert,
  Snackbar,
  CircularProgress,
} from "@mui/material";

import LeaveToolbar from "../../components/leave/LeaveToolbar";
import LeaveTable from "../../components/leave/LeaveTable";
import LeaveDialog from "../../components/leave/LeaveDialog";

import {
  fetchLeaves,
  fetchLeavesByEmployee,
  addLeave,
  editLeave,
  removeLeave,
} from "../../services/leaveService";

import axiosClient from "../../api/axiosClient";

function LeavePage() {
  const [leaves, setLeaves] = useState([]);

  const [employees, setEmployees] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [employeeId, setEmployeeId] = useState("");

  const [dialogOpen, setDialogOpen] = useState(false);

  const [selectedLeave, setSelectedLeave] = useState(null);

  const [successMessage, setSuccessMessage] = useState("");

  /*
   * ============================================================
   * LOAD EMPLOYEES
   * ============================================================
   */

  const loadEmployees = async () => {
    try {
      const response = await axiosClient.get("/employees");

      const data = Array.isArray(response.data)
        ? response.data
        : [];

      setEmployees(data);
    } catch (err) {
      console.error("Failed to load employees:", err);

      throw err;
    }
  };

  /*
   * ============================================================
   * LOAD LEAVES
   * ============================================================
   */

  const loadLeaves = async () => {
    try {
      setLoading(true);
      setError("");

      let data;

      if (employeeId) {
        data = await fetchLeavesByEmployee(
          Number(employeeId)
        );
      } else {
        data = await fetchLeaves();
      }

      setLeaves(
        Array.isArray(data)
          ? data
          : []
      );
    } catch (err) {
      console.error(
        "Failed to load leaves:",
        err
      );

      setError(
        err.response?.data?.message ||
          "Failed to load leave records."
      );
    } finally {
      setLoading(false);
    }
  };

  /*
   * ============================================================
   * INITIAL LOAD
   * ============================================================
   */

  useEffect(() => {
    const initialize = async () => {
      try {
        setLoading(true);
        setError("");

        await loadEmployees();
        await loadLeaves();
      } catch (err) {
        console.error(
          "Failed to initialize leave page:",
          err
        );

        setError(
          err.response?.data?.message ||
            "Failed to load leave data."
        );

        setLoading(false);
      }
    };

    initialize();
  }, []);

  /*
   * ============================================================
   * FILTER
   * ============================================================
   */

  useEffect(() => {
    if (employeeId !== "") {
      loadLeaves();
    }
  }, [employeeId]);

  const handleEmployeeChange = (value) => {
    setEmployeeId(value);
  };

  /*
   * ============================================================
   * ADD LEAVE
   * ============================================================
   */

  const handleAdd = () => {
    setSelectedLeave(null);
    setDialogOpen(true);
  };

  /*
   * ============================================================
   * EDIT LEAVE
   * ============================================================
   */

  const handleEdit = (leave) => {
    setSelectedLeave(leave);
    setDialogOpen(true);
  };

  /*
   * ============================================================
   * CLOSE DIALOG
   * ============================================================
   */

  const handleCloseDialog = () => {
    setDialogOpen(false);
    setSelectedLeave(null);
  };

  /*
   * ============================================================
   * CREATE / UPDATE
   * ============================================================
   */

  const handleSubmit = async (data) => {
    try {
      if (selectedLeave) {
        await editLeave(
          selectedLeave.id,
          data
        );

        setSuccessMessage(
          "Leave updated successfully."
        );
      } else {
        await addLeave(data);

        setSuccessMessage(
          "Leave application submitted successfully."
        );
      }

      handleCloseDialog();

      await loadLeaves();
    } catch (err) {
      console.error(
        "Failed to save leave:",
        err
      );

      setError(
        err.response?.data?.message ||
          "Failed to save leave."
      );

      throw err;
    }
  };

  /*
   * ============================================================
   * DELETE
   * ============================================================
   */

  const handleDelete = async (leave) => {
    const employee =
      employees.find(
        (item) =>
          Number(item.id) ===
          Number(leave.employeeId)
      );

    const employeeName =
      employee
        ? `${employee.firstName || ""} ${
            employee.lastName || ""
          }`.trim()
        : leave.employee?.firstName
          ? `${leave.employee.firstName || ""} ${
              leave.employee.lastName || ""
            }`.trim()
          : leave.employeeName ||
            leave.employee?.employeeCode ||
            "this employee";

    const confirmed = window.confirm(
      `Are you sure you want to delete the leave record for ${employeeName}?`
    );

    if (!confirmed) {
      return;
    }

    try {
      await removeLeave(leave.id);

      setSuccessMessage(
        "Leave deleted successfully."
      );

      await loadLeaves();
    } catch (err) {
      console.error(
        "Failed to delete leave:",
        err
      );

      setError(
        err.response?.data?.message ||
          "Failed to delete leave."
      );
    }
  };

  /*
   * ============================================================
   * RENDER
   * ============================================================
   */

  return (
    <Box
      sx={{
        flex: 1,
        p: {
          xs: 2,
          md: 4,
        },
        bgcolor: "#F5F7FA",
        minHeight: "100%",
      }}
    >
      <Typography
        variant="h4"
        fontWeight="bold"
        sx={{ mb: 1 }}
      >
        Leave Management
      </Typography>

      <Typography
        color="text.secondary"
        sx={{ mb: 4 }}
      >
        Apply and manage employee leave records.
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

      <LeaveToolbar
        employeeId={employeeId}
        employees={employees}
        onEmployeeChange={
          handleEmployeeChange
        }
        onApplyLeave={handleAdd}
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
        <LeaveTable
          leaves={leaves}
          employees={employees}
          loading={loading}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      )}

      <LeaveDialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        leave={selectedLeave}
        employees={employees}
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

export default LeavePage;