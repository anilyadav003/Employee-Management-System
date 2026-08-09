import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
} from "@mui/material";

import UserForm from "./UserForm";

function UserDialog({
  open,
  onClose,
  onSubmit,
  user,
}) {
  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth="sm"
    >
      <DialogTitle>
        {user ? "Edit User" : "Add User"}
      </DialogTitle>

      <DialogContent>
        <UserForm
          user={user}
          onSubmit={onSubmit}
        />
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose}>
          Cancel
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default UserDialog;