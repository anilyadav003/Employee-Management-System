import {
  Box,
  Button,
  TextField,
  MenuItem,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";

function AttendanceToolbar({
  date,
  employeeId,
  employees,
  onDateChange,
  onEmployeeChange,
  onMarkAttendance,
}) {
  return (
    <Box
      sx={{
        display: "flex",
        gap: 2,
        flexWrap: "wrap",
        alignItems: "center",
        mb: 3,
      }}
    >
      <TextField
        type="date"
        label="Filter by Date"
        value={date}
        onChange={(event) =>
          onDateChange(
            event.target.value
          )
        }
        slotProps={{
          inputLabel: {
            shrink: true,
          },
        }}
        size="small"
      />

      <TextField
        select
        label="Filter by Employee"
        value={employeeId}
        onChange={(event) =>
          onEmployeeChange(
            event.target.value
          )
        }
        size="small"
        sx={{
          minWidth: 220,
        }}
      >
        <MenuItem value="">
          All Employees
        </MenuItem>

        {employees.map((employee) => (
          <MenuItem
            key={employee.id}
            value={employee.id}
          >
            {employee.firstName}{" "}
            {employee.lastName}{" "}
            ({employee.employeeCode})
          </MenuItem>
        ))}
      </TextField>

      <Button
        variant="contained"
        startIcon={<AddIcon />}
        onClick={onMarkAttendance}
        sx={{
          ml: "auto",
        }}
      >
        Mark Attendance
      </Button>
    </Box>
  );
}

export default AttendanceToolbar;