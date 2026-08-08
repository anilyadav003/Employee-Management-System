import {
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton,
} from "@mui/material";

import CloseIcon from "@mui/icons-material/Close";

import AttendanceForm from "./AttendanceForm";

function AttendanceDialog({
  open,
  onClose,
  attendance,
  employees,
  onSubmit,
}) {
  const isEdit = Boolean(attendance);

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
        {isEdit
          ? "Edit Attendance"
          : "Mark Attendance"}

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
        <AttendanceForm
          attendance={attendance}
          employees={employees}
          onSubmit={handleSubmit}
          submitLabel={
            isEdit
              ? "Update Attendance"
              : "Mark Attendance"
          }
        />
      </DialogContent>
    </Dialog>
  );
}

export default AttendanceDialog;