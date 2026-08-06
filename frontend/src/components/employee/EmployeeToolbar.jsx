import { Box, Button, TextField, InputAdornment } from "@mui/material";
import { Search, Add } from "@mui/icons-material";

function EmployeeToolbar({
  search,
  setSearch,
  onAddClick,
}) {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        mb: 3,
        gap: 2,
        flexWrap: "wrap",
      }}
    >
      <TextField
        placeholder="Search Employee..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        size="small"
        sx={{
          width: 350,
          bgcolor: "white",
        }}
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <Search />
            </InputAdornment>
          ),
        }}
      />

      <Button
        variant="contained"
        startIcon={<Add />}
        onClick={onAddClick}
        sx={{
          borderRadius: 2,
          textTransform: "none",
          px: 3,
          height: 40,
        }}
      >
        Add Employee
      </Button>
    </Box>
  );
}

export default EmployeeToolbar;