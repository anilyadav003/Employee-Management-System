import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Box,
  Typography,
  Divider,
  Grid,
} from "@mui/material";

import EmployeeForm from "./EmployeeForm";

function EmployeeDetails({ employee }) {
  const fields = [
    ["Employee Code", employee?.employeeCode],
    [
      "Name",
      `${employee?.firstName || ""} ${
        employee?.lastName || ""
      }`,
    ],
    ["Department", employee?.departmentName],
    ["Designation", employee?.designation],
    ["Username", employee?.username],
    ["Date of Joining", employee?.dateOfJoining],
    [
      "Salary",
      employee?.salary != null
        ? `₹${Number(
            employee.salary
          ).toLocaleString("en-IN")}`
        : "-",
    ],
  ];

  return (
    <Grid container spacing={2} sx={{ mt: 0.5 }}>
      {fields.map(([label, value]) => (
        <Grid
          size={{ xs: 12, sm: 6 }}
          key={label}
        >
          <Typography
            variant="caption"
            color="text.secondary"
          >
            {label}
          </Typography>

          <Typography
            fontWeight={600}
            sx={{ mt: 0.5 }}
          >
            {value || "-"}
          </Typography>
        </Grid>
      ))}
    </Grid>
  );
}

function EmployeeDialog({
  open,
  onClose,
  onSubmit,
  employee,
  mode = "create",
}) {
  const isView = mode === "view";
  const isEdit = mode === "edit";

  const title = isView
    ? "Employee Details"
    : isEdit
      ? "Edit Employee"
      : "Add Employee";

  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth="md"
    >
      <DialogTitle>
        {title}
      </DialogTitle>

      <DialogContent dividers>
        {isView ? (
          <EmployeeDetails
            employee={employee}
          />
        ) : (
          <EmployeeForm
            employee={employee}
            onSubmit={onSubmit}
            submitLabel={
              isEdit
                ? "Update Employee"
                : "Save Employee"
            }
          />
        )}
      </DialogContent>

      {isView && (
        <DialogActions>
          <Button
            variant="outlined"
            onClick={onClose}
          >
            Close
          </Button>
        </DialogActions>
      )}

      {!isView && (
        <DialogActions>
          <Button
            variant="outlined"
            onClick={onClose}
          >
            Cancel
          </Button>
        </DialogActions>
      )}
    </Dialog>
  );
}

export default EmployeeDialog;