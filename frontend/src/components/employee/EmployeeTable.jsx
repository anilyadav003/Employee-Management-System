import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  Tooltip,
  Typography,
} from "@mui/material";

import {
  Visibility,
  Edit,
  Delete,
} from "@mui/icons-material";

function EmployeeTable({
  employees,
  onView,
  onEdit,
  onDelete,
}) {
  return (
    <TableContainer
      component={Paper}
      elevation={3}
      sx={{
        borderRadius: 3,
        overflowX: "auto",
      }}
    >
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>
              <strong>Employee Code</strong>
            </TableCell>

            <TableCell>
              <strong>Name</strong>
            </TableCell>

            <TableCell>
              <strong>Department</strong>
            </TableCell>

            <TableCell>
              <strong>Designation</strong>
            </TableCell>

            <TableCell>
              <strong>Salary</strong>
            </TableCell>

            <TableCell align="center">
              <strong>Actions</strong>
            </TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {employees.length === 0 ? (
            <TableRow>
              <TableCell
                colSpan={6}
                align="center"
              >
                <Typography
                  color="text.secondary"
                  sx={{ py: 4 }}
                >
                  No employees found.
                </Typography>
              </TableCell>
            </TableRow>
          ) : (
            employees.map((employee) => (
              <TableRow
                key={employee.id}
                hover
              >
                <TableCell>
                  {employee.employeeCode}
                </TableCell>

                <TableCell>
                  {employee.firstName}{" "}
                  {employee.lastName}
                </TableCell>

                <TableCell>
                  {employee.departmentName || "-"}
                </TableCell>

                <TableCell>
                  {employee.designation}
                </TableCell>

                <TableCell>
                  ₹
                  {Number(
                    employee.salary || 0
                  ).toLocaleString("en-IN")}
                </TableCell>

                <TableCell align="center">
                  <Tooltip title="View Employee">
                    <IconButton
                      color="info"
                      onClick={() =>
                        onView(employee)
                      }
                    >
                      <Visibility />
                    </IconButton>
                  </Tooltip>

                  <Tooltip title="Edit Employee">
                    <IconButton
                      color="primary"
                      onClick={() =>
                        onEdit(employee)
                      }
                    >
                      <Edit />
                    </IconButton>
                  </Tooltip>

                  <Tooltip title="Delete Employee">
                    <IconButton
                      color="error"
                      onClick={() =>
                        onDelete(employee)
                      }
                    >
                      <Delete />
                    </IconButton>
                  </Tooltip>
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default EmployeeTable;