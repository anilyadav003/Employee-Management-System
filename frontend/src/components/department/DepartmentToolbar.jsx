import {
  Box,
  TextField,
  Button,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";

function DepartmentToolbar({
  search,
  onSearchChange,
  onAdd,
}) {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        gap: 2,
        mb: 3,
      }}
    >
      <TextField
        size="small"
        placeholder="Search departments..."
        value={search}
        onChange={(event) =>
          onSearchChange(event.target.value)
        }
        sx={{
          width: {
            xs: "100%",
            sm: 380,
          },
        }}
      />

      <Button
        variant="contained"
        startIcon={<AddIcon />}
        onClick={onAdd}
        sx={{
          minWidth: 170,
          py: 1.2,
        }}
      >
        Add Department
      </Button>
    </Box>
  );
}

export default DepartmentToolbar;