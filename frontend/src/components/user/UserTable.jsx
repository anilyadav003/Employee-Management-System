import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  Chip,
  Tooltip,
} from "@mui/material";

import {
  Edit,
  Delete,
} from "@mui/icons-material";

function UserTable({
  users,
  onEdit,
  onDelete,
}) {
  return (
    <TableContainer
      component={Paper}
      elevation={3}
      sx={{
        borderRadius: 3,
        mt: 3,
      }}
    >
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>
              <strong>Username</strong>
            </TableCell>

            <TableCell>
              <strong>Email</strong>
            </TableCell>

            <TableCell>
              <strong>Role</strong>
            </TableCell>

            <TableCell>
              <strong>Status</strong>
            </TableCell>

            <TableCell align="center">
              <strong>Actions</strong>
            </TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {users.length === 0 ? (
            <TableRow>
              <TableCell
                colSpan={5}
                align="center"
              >
                No users found.
              </TableCell>
            </TableRow>
          ) : (
            users.map((user) => (
              <TableRow
                key={user.id}
                hover
              >
                <TableCell>
                  {user.username}
                </TableCell>

                <TableCell>
                  {user.email}
                </TableCell>

                <TableCell>
                  <Chip
                    label={user.role}
                    color="primary"
                    size="small"
                  />
                </TableCell>

                <TableCell>
                  <Chip
                    label={
                      user.enabled
                        ? "Enabled"
                        : "Disabled"
                    }
                    color={
                      user.enabled
                        ? "success"
                        : "error"
                    }
                    size="small"
                  />
                </TableCell>

                <TableCell align="center">
                  <Tooltip title="Edit User">
                    <IconButton
                      color="primary"
                      onClick={() => onEdit(user)}
                    >
                      <Edit />
                    </IconButton>
                  </Tooltip>

                  <Tooltip title="Delete User">
                    <IconButton
                      color="error"
                      onClick={() => onDelete(user)}
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

export default UserTable;