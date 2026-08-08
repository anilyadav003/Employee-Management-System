import { useEffect, useMemo, useState } from "react";

import {
  Box,
  Typography,
  Alert,
  Snackbar,
  CircularProgress,
} from "@mui/material";

import AttendanceToolbar from "../../components/attendance/AttendanceToolbar";
import AttendanceTable from "../../components/attendance/AttendanceTable";
import AttendanceDialog from "../../components/attendance/AttendanceDialog";

import {
  fetchAttendance,
  fetchAttendanceByDate,
  fetchAttendanceByEmployee,
  addAttendance,
  editAttendance,
  removeAttendance,
} from "../../services/attendanceService";

import axiosClient from "../../api/axiosClient";

function AttendancePage() {
  const [attendance, setAttendance] = useState([]);

  const [employees, setEmployees] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [date, setDate] = useState("");

  const [employeeId, setEmployeeId] = useState("");

  const [dialogOpen, setDialogOpen] = useState(false);

  const [selectedAttendance, setSelectedAttendance] =
    useState(null);

  const [successMessage, setSuccessMessage] =
    useState("");

  /*
   * ============================================================
   * LOAD EMPLOYEES
   * ============================================================
   */

  const loadEmployees = async () => {
    try {
      const response = await axiosClient.get(
        "/employees"
      );

      const data = Array.isArray(response.data)
        ? response.data
        : [];

      setEmployees(data);
    } catch (err) {
      console.error(
        "Failed to load employees:",
        err
      );

      throw err;
    }
  };

  /*
   * ============================================================
   * LOAD ATTENDANCE
   * ============================================================
   */

  const loadAttendance = async () => {
    try {
      setLoading(true);
      setError("");

      let data;

      if (date) {
        data = await fetchAttendanceByDate(date);
      } else if (employeeId) {
        data =
          await fetchAttendanceByEmployee(
            Number(employeeId)
          );
      } else {
        data = await fetchAttendance();
      }

      setAttendance(
        Array.isArray(data) ? data : []
      );
    } catch (err) {
      console.error(
        "Failed to load attendance:",
        err
      );

      setError(
        err.response?.data?.message ||
          "Failed to load attendance."
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

        await loadAttendance();
      } catch (err) {
        console.error(
          "Failed to initialize attendance page:",
          err
        );

        setError(
          err.response?.data?.message ||
            "Failed to load attendance data."
        );

        setLoading(false);
      }
    };

    initialize();
  }, []);

  /*
   * ============================================================
   * RELOAD ATTENDANCE WHEN FILTER CHANGES
   * ============================================================
   */

  useEffect(() => {
    if (
      date !== "" ||
      employeeId !== ""
    ) {
      loadAttendance();
    }
  }, [date, employeeId]);

  /*
   * ============================================================
   * FILTER CHANGE
   * ============================================================
   */

  const handleDateChange = (value) => {
    setDate(value);

    /*
     * The backend provides separate filters for date
     * and employee. We keep the filters mutually exclusive
     * so we don't accidentally call the wrong endpoint.
     */
    if (value) {
      setEmployeeId("");
    }
  };

  const handleEmployeeChange = (value) => {
    setEmployeeId(value);

    if (value) {
      setDate("");
    }
  };

  /*
   * ============================================================
   * ADD ATTENDANCE
   * ============================================================
   */

  const handleAdd = () => {
    setSelectedAttendance(null);
    setDialogOpen(true);
  };

  /*
   * ============================================================
   * EDIT ATTENDANCE
   * ============================================================
   */

  const handleEdit = (record) => {
    setSelectedAttendance(record);
    setDialogOpen(true);
  };

  /*
   * ============================================================
   * CLOSE DIALOG
   * ============================================================
   */

  const handleCloseDialog = () => {
    setDialogOpen(false);
    setSelectedAttendance(null);
  };

  /*
   * ============================================================
   * CREATE / UPDATE
   * ============================================================
   */

  const handleSubmit = async (data) => {
    try {
      if (selectedAttendance) {
        await editAttendance(
          selectedAttendance.id,
          data
        );

        setSuccessMessage(
          "Attendance updated successfully."
        );
      } else {
        await addAttendance(data);

        setSuccessMessage(
          "Attendance marked successfully."
        );
      }

      handleCloseDialog();

      await loadAttendance();
    } catch (err) {
      console.error(
        "Failed to save attendance:",
        err
      );

      setError(
        err.response?.data?.message ||
          "Failed to save attendance."
      );

      throw err;
    }
  };

  /*
   * ============================================================
   * DELETE
   * ============================================================
   */

  const handleDelete = async (record) => {
    const employeeName =
      record.employeeName ||
      record.employeeCode ||
      "this employee";

    const confirmed = window.confirm(
      `Are you sure you want to delete the attendance record for ${employeeName} on ${record.attendanceDate}?`
    );

    if (!confirmed) {
      return;
    }

    try {
      await removeAttendance(
        record.id
      );

      setSuccessMessage(
        "Attendance deleted successfully."
      );

      await loadAttendance();
    } catch (err) {
      console.error(
        "Failed to delete attendance:",
        err
      );

      setError(
        err.response?.data?.message ||
          "Failed to delete attendance."
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
        Attendance Management
      </Typography>

      <Typography
        color="text.secondary"
        sx={{ mb: 4 }}
      >
        Track and manage employee attendance.
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

      <AttendanceToolbar
        date={date}
        employeeId={employeeId}
        employees={employees}
        onDateChange={handleDateChange}
        onEmployeeChange={
          handleEmployeeChange
        }
        onMarkAttendance={handleAdd}
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
        <AttendanceTable
          attendance={attendance}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      )}

      <AttendanceDialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        attendance={selectedAttendance}
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

export default AttendancePage;