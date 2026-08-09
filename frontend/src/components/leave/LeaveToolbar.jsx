import {
  Box,
  Button,
  TextField,
  MenuItem,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";

function LeaveToolbar({
  employeeId,
  employees,
  onEmployeeChange,
  onApplyLeave,
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
        select
        size="small"
        label="Filter by Employee"
        value={employeeId}
        onChange={(event) =>
          onEmployeeChange(event.target.value)
        }
        sx={{
          minWidth: 240,
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
            {employee.employeeCode
              ? `(${employee.employeeCode})`
              : ""}
          </MenuItem>
        ))}
      </TextField>

      <Button
        variant="contained"
        startIcon={<AddIcon />}
        onClick={onApplyLeave}
        sx={{
          ml: "auto",
        }}
      >
        Apply Leave
      </Button>
    </Box>
  );
}

export default LeaveToolbar;