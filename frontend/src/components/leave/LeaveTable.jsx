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
  Box,
} from "@mui/material";

import {
  Edit,
  Delete,
} from "@mui/icons-material";

function LeaveTable({
  leaves = [],
  employees = [],
  loading = false,
  onEdit,
  onDelete,
}) {
  /*
   * ============================================================
   * LOADING STATE
   * ============================================================
   */

  if (loading) {
    return (
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          py: 6,
        }}
      >
        <Typography color="text.secondary">
          Loading leave records...
        </Typography>
      </Box>
    );
  }

  /*
   * ============================================================
   * EMPTY STATE
   * ============================================================
   */

  if (leaves.length === 0) {
    return (
      <Paper
        elevation={2}
        sx={{
          borderRadius: 3,
          p: 5,
          textAlign: "center",
        }}
      >
        <Typography
          color="text.secondary"
          fontSize="1rem"
        >
          No leave records found.
        </Typography>
      </Paper>
    );
  }

  /*
   * ============================================================
   * EMPLOYEE RESOLVER
   * ============================================================
   */

  const getEmployee = (leave) => {
    /*
     * First try employeeId from the leave record.
     */

    if (leave.employeeId !== undefined) {
      const employee = employees.find(
        (item) =>
          Number(item.id) ===
          Number(leave.employeeId)
      );

      if (employee) {
        return employee;
      }
    }

    /*
     * Fallback if backend returns nested employee object.
     */

    if (leave.employee) {
      return leave.employee;
    }

    return null;
  };

  /*
   * ============================================================
   * EMPLOYEE NAME
   * ============================================================
   */

  const getEmployeeName = (leave) => {
    const employee = getEmployee(leave);

    if (employee) {
      const name =
        `${employee.firstName || ""} ${
          employee.lastName || ""
        }`.trim();

      if (name) {
        return name;
      }

      if (employee.employeeName) {
        return employee.employeeName;
      }
    }

    /*
     * Additional fallback for flat API responses.
     */

    if (leave.employeeName) {
      return leave.employeeName;
    }

    if (
      leave.firstName ||
      leave.lastName
    ) {
      return `${leave.firstName || ""} ${
        leave.lastName || ""
      }`.trim();
    }

    return "-";
  };

  /*
   * ============================================================
   * EMPLOYEE CODE
   * ============================================================
   */

  const getEmployeeCode = (leave) => {
    const employee = getEmployee(leave);

    if (employee?.employeeCode) {
      return employee.employeeCode;
    }

    if (leave.employeeCode) {
      return leave.employeeCode;
    }

    return "-";
  };

  /*
   * ============================================================
   * DATE FORMATTER
   * ============================================================
   */

  const formatDate = (date) => {
    if (!date) {
      return "-";
    }

    const parsedDate = new Date(
      `${date}T00:00:00`
    );

    if (Number.isNaN(parsedDate.getTime())) {
      return date;
    }

    return parsedDate.toLocaleDateString(
      "en-IN",
      {
        day: "2-digit",
        month: "short",
        year: "numeric",
      }
    );
  };

  /*
   * ============================================================
   * STATUS CHIP
   * ============================================================
   */

  const getStatusColor = (status) => {
    switch (
      String(status || "").toUpperCase()
    ) {
      case "APPROVED":
        return "success";

      case "REJECTED":
        return "error";

      case "PENDING":
        return "warning";

      default:
        return "default";
    }
  };

  /*
   * ============================================================
   * RENDER
   * ============================================================
   */

  return (
    <TableContainer
      component={Paper}
      elevation={2}
      sx={{
        borderRadius: 3,
        overflowX: "auto",
      }}
    >
      <Table
        sx={{
          minWidth: 1050,
        }}
      >
        <TableHead>
          <TableRow
            sx={{
              bgcolor: "#F8FAFC",
            }}
          >
            <TableCell
              sx={{
                minWidth: 180,
                py: 2,
                px: 2.5,
                fontWeight: "bold",
                whiteSpace: "nowrap",
              }}
            >
              Employee
            </TableCell>

            <TableCell
              sx={{
                minWidth: 150,
                py: 2,
                px: 2.5,
                fontWeight: "bold",
                whiteSpace: "nowrap",
              }}
            >
              Employee Code
            </TableCell>

            <TableCell
              sx={{
                minWidth: 140,
                py: 2,
                px: 2.5,
                fontWeight: "bold",
                whiteSpace: "nowrap",
              }}
            >
              Start Date
            </TableCell>

            <TableCell
              sx={{
                minWidth: 140,
                py: 2,
                px: 2.5,
                fontWeight: "bold",
                whiteSpace: "nowrap",
              }}
            >
              End Date
            </TableCell>

            <TableCell
              sx={{
                minWidth: 220,
                py: 2,
                px: 2.5,
                fontWeight: "bold",
              }}
            >
              Reason
            </TableCell>

            <TableCell
              sx={{
                minWidth: 130,
                py: 2,
                px: 2.5,
                fontWeight: "bold",
                whiteSpace: "nowrap",
              }}
            >
              Status
            </TableCell>

            <TableCell
              align="center"
              sx={{
                minWidth: 150,
                py: 2,
                px: 2.5,
                fontWeight: "bold",
                whiteSpace: "nowrap",
              }}
            >
              Actions
            </TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {leaves.map((leave) => (
            <TableRow
              key={leave.id}
              hover
              sx={{
                "&:last-child td": {
                  borderBottom: 0,
                },
              }}
            >
              {/* Employee */}
              <TableCell
                sx={{
                  py: 2.2,
                  px: 2.5,
                }}
              >
                <Typography
                  fontWeight={600}
                  noWrap
                >
                  {getEmployeeName(leave)}
                </Typography>
              </TableCell>

              {/* Employee Code */}
              <TableCell
                sx={{
                  py: 2.2,
                  px: 2.5,
                }}
              >
                <Typography
                  color="text.secondary"
                  fontWeight={500}
                  noWrap
                >
                  {getEmployeeCode(leave)}
                </Typography>
              </TableCell>

              {/* Start Date */}
              <TableCell
                sx={{
                  py: 2.2,
                  px: 2.5,
                  whiteSpace: "nowrap",
                }}
              >
                {formatDate(leave.startDate)}
              </TableCell>

              {/* End Date */}
              <TableCell
                sx={{
                  py: 2.2,
                  px: 2.5,
                  whiteSpace: "nowrap",
                }}
              >
                {formatDate(leave.endDate)}
              </TableCell>

              {/* Reason */}
              <TableCell
                sx={{
                  py: 2.2,
                  px: 2.5,
                  maxWidth: 260,
                }}
              >
                <Typography
                  sx={{
                    overflow: "hidden",
                    textOverflow: "ellipsis",
                    whiteSpace: "nowrap",
                  }}
                  title={leave.reason || ""}
                >
                  {leave.reason || "-"}
                </Typography>
              </TableCell>

              {/* Status */}
              <TableCell
                sx={{
                  py: 2.2,
                  px: 2.5,
                }}
              >
                <Chip
                  label={
                    leave.status || "-"
                  }
                  size="small"
                  color={getStatusColor(
                    leave.status
                  )}
                  variant="outlined"
                  sx={{
                    fontWeight: 600,
                  }}
                />
              </TableCell>

              {/* Actions */}
              <TableCell
                align="center"
                sx={{
                  py: 2.2,
                  px: 2.5,
                }}
              >
                <Box
                  sx={{
                    display: "flex",
                    justifyContent:
                      "center",
                    alignItems: "center",
                    gap: 0.5,
                  }}
                >
                  {onEdit && (
                    <Tooltip title="Edit Leave">
                      <IconButton
                        color="primary"
                        size="small"
                        onClick={() =>
                          onEdit(leave)
                        }
                      >
                        <Edit />
                      </IconButton>
                    </Tooltip>
                  )}

                  {onDelete && (
                    <Tooltip title="Delete Leave">
                      <IconButton
                        color="error"
                        size="small"
                        onClick={() =>
                          onDelete(leave)
                        }
                      >
                        <Delete />
                      </IconButton>
                    </Tooltip>
                  )}
                </Box>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default LeaveTable;