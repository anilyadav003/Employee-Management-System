import {
  Dialog,
  DialogTitle,
  DialogContent,
  IconButton,
} from "@mui/material";

import CloseIcon from "@mui/icons-material/Close";

import DepartmentForm from "./DepartmentForm";

function DepartmentDialog({
  open,
  onClose,
  department,
  onSubmit,
}) {
  const isEditing = Boolean(department);

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
      <DialogTitle
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          fontWeight: "bold",
        }}
      >
        {isEditing
          ? "Edit Department"
          : "Add Department"}

        <IconButton onClick={onClose}>
          <CloseIcon />
        </IconButton>
      </DialogTitle>

      <DialogContent>
        <DepartmentForm
          department={department}
          onSubmit={handleSubmit}
          submitLabel={
            isEditing
              ? "Update Department"
              : "Save Department"
          }
        />
      </DialogContent>
    </Dialog>
  );
}

export default DepartmentDialog;