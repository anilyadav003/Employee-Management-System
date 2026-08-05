import {
  AppBar,
  Toolbar,
  Typography,
  Box,
  Avatar,
} from "@mui/material";

function Topbar() {
  return (
    <AppBar
      position="static"
      elevation={1}
      sx={{
        bgcolor: "white",
        color: "#111827",
      }}
    >
      <Toolbar>
        <Typography
          variant="h6"
          fontWeight="bold"
        >
          Dashboard
        </Typography>

        <Box sx={{ flexGrow: 1 }} />

        <Typography sx={{ mr: 2 }}>
          Welcome, Anil
        </Typography>

        <Avatar>A</Avatar>
      </Toolbar>
    </AppBar>
  );
}

export default Topbar;