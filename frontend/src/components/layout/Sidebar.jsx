import { Link, useLocation } from "react-router-dom";

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

import ROUTES from "../../constants/routes";

const menuItems = [
  {
    text: "Dashboard",
    icon: <Dashboard />,
    path: ROUTES.DASHBOARD,
  },
  {
    text: "Employees",
    icon: <People />,
    path: ROUTES.EMPLOYEES,
  },
  {
    text: "Departments",
    icon: <Business />,
    path: ROUTES.DEPARTMENTS,
  },
  {
    text: "Attendance",
    icon: <EventAvailable />,
    path: "#",
  },
  {
    text: "Leave",
    icon: <BeachAccess />,
    path: "#",
  },
  {
    text: "Payroll",
    icon: <Payments />,
    path: "#",
  },
  {
    text: "Reports",
    icon: <Assessment />,
    path: "#",
  },
  {
    text: "Settings",
    icon: <Settings />,
    path: "#",
  },
];

function Sidebar() {
  const location = useLocation();

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
            component={Link}
            to={item.path}
            selected={location.pathname === item.path}
            sx={{
              color: "white",
              mx: 1,
              mb: 1,
              borderRadius: 2,

              "&.Mui-selected": {
                bgcolor: "#2563EB",
              },

              "&.Mui-selected:hover": {
                bgcolor: "#1D4ED8",
              },

              "&:hover": {
                bgcolor: "#1E293B",
              },
            }}
          >
            <ListItemIcon
              sx={{
                color: "white",
              }}
            >
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