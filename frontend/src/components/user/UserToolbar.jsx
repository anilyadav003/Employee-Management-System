import {
  Stack,
  TextField,
  Button,
  InputAdornment,
} from "@mui/material";

import {
  Search,
  Add,
} from "@mui/icons-material";

function UserToolbar({
  search,
  setSearch,
  onAddClick,
}) {
  return (
    <Stack
      direction="row"
      justifyContent="space-between"
      alignItems="center"
      spacing={2}
      sx={{ mb: 3 }}
    >
      <TextField
        placeholder="Search users..."
        value={search}
        onChange={(event) =>
          setSearch(event.target.value)
        }
        sx={{
          width: 350,
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
      >
        Add User
      </Button>
    </Stack>
  );
}

export default UserToolbar;