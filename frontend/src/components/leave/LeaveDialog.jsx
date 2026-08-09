import {
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton,
} from "@mui/material";

import CloseIcon from "@mui/icons-material/Close";

import LeaveForm from "./LeaveForm";

function LeaveDialog({
  open,
  onClose,
  leave,
  employees,
  onSubmit,
}) {
  const isEditing = Boolean(leave);

  const handleSubmit = async (data) => {
    await onSubmit(data);
  };

  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth="sm"
    >
      <DialogTitle>
        {isEditing
          ? "Edit Leave"
          : "Apply for Leave"}

        <IconButton
          onClick={onClose}
          sx={{
            position: "absolute",
            right: 8,
            top: 8,
          }}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>

      <DialogContent>
        <LeaveForm
          initialData={leave}
          employees={employees}
          onSubmit={handleSubmit}
          onCancel={onClose}
        />
      </DialogContent>
    </Dialog>
  );
}

export default LeaveDialog;