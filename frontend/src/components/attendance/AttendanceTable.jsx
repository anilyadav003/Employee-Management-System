import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
  IconButton,
  Tooltip,
  Typography,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const getStatusColor = (status) => {
  switch (status) {
    case "PRESENT":
      return "success";

    case "ABSENT":
      return "error";

    case "HALF_DAY":
      return "warning";

    case "LEAVE":
      return "info";

    default:
      return "default";
  }
};

const formatStatus = (status) => {
  if (!status) return "-";

  return status
    .replace("_", " ")
    .toLowerCase()
    .replace(/\b\w/g, (char) =>
      char.toUpperCase()
    );
};

function AttendanceTable({
  attendance,
  onEdit,
  onDelete,
}) {
  return (
    <TableContainer
      component={Paper}
      sx={{
        borderRadius: 2,
        boxShadow: 2,
      }}
    >
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>
              <strong>Employee</strong>
            </TableCell>

            <TableCell>
              <strong>Employee Code</strong>
            </TableCell>

            <TableCell>
              <strong>Date</strong>
            </TableCell>

            <TableCell>
              <strong>Check In</strong>
            </TableCell>

            <TableCell>
              <strong>Check Out</strong>
            </TableCell>

            <TableCell>
              <strong>Status</strong>
            </TableCell>

            <TableCell>
              <strong>Working Hours</strong>
            </TableCell>

            <TableCell align="center">
              <strong>Actions</strong>
            </TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {attendance.length === 0 ? (
            <TableRow>
              <TableCell
                colSpan={8}
                align="center"
              >
                <Typography
                  sx={{ py: 3 }}
                  color="text.secondary"
                >
                  No attendance records
                  found.
                </Typography>
              </TableCell>
            </TableRow>
          ) : (
            attendance.map((record) => (
              <TableRow
                key={record.id}
                hover
              >
                <TableCell>
                  {record.employeeName ||
                    "-"}
                </TableCell>

                <TableCell>
                  {record.employeeCode ||
                    "-"}
                </TableCell>

                <TableCell>
                  {record.attendanceDate ||
                    "-"}
                </TableCell>

                <TableCell>
                  {record.checkInTime ||
                    "-"}
                </TableCell>

                <TableCell>
                  {record.checkOutTime ||
                    "-"}
                </TableCell>

                <TableCell>
                  <Chip
                    label={formatStatus(
                      record.status
                    )}
                    color={getStatusColor(
                      record.status
                    )}
                    size="small"
                  />
                </TableCell>

                <TableCell>
                  {record.workingHours != null
                    ? `${record.workingHours} hrs`
                    : "-"}
                </TableCell>

                <TableCell align="center">
                  <Tooltip title="Edit">
                    <IconButton
                      color="primary"
                      onClick={() =>
                        onEdit(record)
                      }
                    >
                      <EditIcon />
                    </IconButton>
                  </Tooltip>

                  <Tooltip title="Delete">
                    <IconButton
                      color="error"
                      onClick={() =>
                        onDelete(record)
                      }
                    >
                      <DeleteIcon />
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

export default AttendanceTable;