import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
} from "@mui/material";

import EmployeeForm from "./EmployeeForm";

function EmployeeDialog({
  open,
  onClose,
  onSubmit,
}) {
  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth="md"
    >
      <DialogTitle>
        Add Employee
      </DialogTitle>

      <DialogContent>
        <EmployeeForm
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

export default EmployeeDialog;