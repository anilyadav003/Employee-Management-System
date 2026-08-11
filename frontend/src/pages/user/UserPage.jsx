import { useEffect, useState } from "react";

import {
  Box,
  Typography,
  CircularProgress,
  Alert,
} from "@mui/material";

import UserTable from "../../components/user/UserTable";
import UserToolbar from "../../components/user/UserToolbar";
import UserDialog from "../../components/user/UserDialog";
import DeleteUserDialog from "../../components/user/DeleteUserDialog";

import {
  getAllUsers,
  createUser,
  updateUser,
  deleteUser,
} from "../../services/userService";

import { toast } from "react-toastify";

function UserPage() {
  const [users, setUsers] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [search, setSearch] = useState("");

  const [dialogOpen, setDialogOpen] = useState(false);

  const [deleteDialogOpen, setDeleteDialogOpen] =
    useState(false);

  const [selectedUser, setSelectedUser] = useState(null);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      setLoading(true);

      const data = await getAllUsers();

      setUsers(data);

      setError("");
    } catch (err) {
      console.error(err);

      setError("Failed to load users.");
    } finally {
      setLoading(false);
    }
  };

  const handleCreateUser = async (user) => {
    try {
      await createUser(user);

      toast.success("User created successfully.");

      setDialogOpen(false);

      fetchUsers();
    } catch (err) {
      console.error(err);

      toast.error(
        err.response?.data?.message ||
          "Failed to create user."
      );
    }
  };

  const handleUpdateUser = async (user) => {
    try {
      await updateUser(selectedUser.id, user);

      toast.success("User updated successfully.");

      setDialogOpen(false);

      setSelectedUser(null);

      fetchUsers();
    } catch (err) {
      console.error(err);

      toast.error(
        err.response?.data?.message ||
          "Failed to update user."
      );
    }
  };

  const handleDeleteUser = async () => {
    try {
      await deleteUser(selectedUser.id);

      toast.success("User deleted successfully.");

      setDeleteDialogOpen(false);

      setSelectedUser(null);

      fetchUsers();
    } catch (err) {
      console.error(err);

      toast.error(
        err.response?.data?.message ||
          "Failed to delete user."
      );
    }
  };

  const filteredUsers = users.filter((user) => {
    const keyword = search.toLowerCase();

    return (
      user.username
        .toLowerCase()
        .includes(keyword) ||
      user.email
        .toLowerCase()
        .includes(keyword) ||
      user.role
        .toLowerCase()
        .includes(keyword)
    );
  });

  return (
    <Box sx={{ p: 4 }}>
      <Typography
        variant="h4"
        fontWeight="bold"
        sx={{ mb: 3 }}
      >
        User Management
      </Typography>

      <UserToolbar
        search={search}
        setSearch={setSearch}
        onAddClick={() => {
          setSelectedUser(null);
          setDialogOpen(true);
        }}
      />

      {loading && (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            mt: 5,
          }}
        >
          <CircularProgress />
        </Box>
      )}

      {!loading && error && (
        <Alert severity="error">
          {error}
        </Alert>
      )}

      {!loading && !error && (
        <UserTable
          users={filteredUsers}
          onEdit={(user) => {
            setSelectedUser(user);
            setDialogOpen(true);
          }}
          onDelete={(user) => {
            setSelectedUser(user);
            setDeleteDialogOpen(true);
          }}
        />
      )}

      <UserDialog
        open={dialogOpen}
        onClose={() => {
          setDialogOpen(false);
          setSelectedUser(null);
        }}
        user={selectedUser}
        onSubmit={
          selectedUser
            ? handleUpdateUser
            : handleCreateUser
        }
      />

      <DeleteUserDialog
        open={deleteDialogOpen}
        onClose={() => {
          setDeleteDialogOpen(false);
          setSelectedUser(null);
        }}
        user={selectedUser}
        onConfirm={handleDeleteUser}
      />
    </Box>
  );
}

export default UserPage;