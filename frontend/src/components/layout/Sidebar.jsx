import {
  Box,
  Typography,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from "@mui/material";

import {
  Dashboard,
  People,
  Business,
  EventAvailable,
  BeachAccess,
  Payments,
  Assessment,
  Settings,
} from "@mui/icons-material";

const menuItems = [
  { text: "Dashboard", icon: <Dashboard /> },
  { text: "Employees", icon: <People /> },
  { text: "Departments", icon: <Business /> },
  { text: "Attendance", icon: <EventAvailable /> },
  { text: "Leave", icon: <BeachAccess /> },
  { text: "Payroll", icon: <Payments /> },
  { text: "Reports", icon: <Assessment /> },
  { text: "Settings", icon: <Settings /> },
];

function Sidebar() {
  return (
    <Box
      sx={{
        width: 260,
        bgcolor: "#0F172A",
        color: "white",
        display: "flex",
        flexDirection: "column",
      }}
    >
      <Typography
        variant="h5"
        fontWeight="bold"
        sx={{
          p: 3,
          textAlign: "center",
          borderBottom: "1px solid rgba(255,255,255,0.1)",
        }}
      >
        EMS
      </Typography>

      <List sx={{ mt: 2 }}>
        {menuItems.map((item) => (
          <ListItemButton
            key={item.text}
            sx={{
              color: "white",
              mx: 1,
              borderRadius: 2,
              mb: 1,
              "&:hover": {
                bgcolor: "#1E293B",
              },
            }}
          >
            <ListItemIcon sx={{ color: "white" }}>
              {item.icon}
            </ListItemIcon>

            <ListItemText primary={item.text} />
          </ListItemButton>
        ))}
      </List>
    </Box>
  );
}

export default Sidebar;