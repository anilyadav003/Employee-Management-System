import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  Typography,
  Tooltip,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

function DepartmentTable({
  departments,
  onEdit,
  onDelete,
}) {
  return (
    <TableContainer
      component={Paper}
      elevation={1}
      sx={{
        borderRadius: 2,
        overflow: "hidden",
      }}
    >
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>
              <strong>ID</strong>
            </TableCell>

            <TableCell>
              <strong>Code</strong>
            </TableCell>

            <TableCell>
              <strong>Name</strong>
            </TableCell>

            <TableCell>
              <strong>Description</strong>
            </TableCell>

            <TableCell align="right">
              <strong>Actions</strong>
            </TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {departments.length === 0 ? (
            <TableRow>
              <TableCell
                colSpan={5}
                align="center"
                sx={{ py: 5 }}
              >
                <Typography
                  color="text.secondary"
                >
                  No departments found.
                </Typography>
              </TableCell>
            </TableRow>
          ) : (
            departments.map((department) => (
              <TableRow
                key={department.id}
                hover
              >
                <TableCell>
                  {department.id}
                </TableCell>

                <TableCell>
                  {department.code}
                </TableCell>

                <TableCell>
                  {department.name}
                </TableCell>

                <TableCell>
                  {department.description || "-"}
                </TableCell>

                <TableCell align="right">
                  <Tooltip title="Edit">
                    <IconButton
                      color="primary"
                      onClick={() =>
                        onEdit(department)
                      }
                    >
                      <EditIcon />
                    </IconButton>
                  </Tooltip>

                  <Tooltip title="Delete">
                    <IconButton
                      color="error"
                      onClick={() =>
                        onDelete(department)
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

export default DepartmentTable;