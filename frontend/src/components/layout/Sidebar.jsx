import {
  useLocation,
  useNavigate,
} from "react-router-dom";

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
  Person,
  Business,
  EventAvailable,
  BeachAccess,
} from "@mui/icons-material";

import ROUTES from "../../constants/routes";

const menuItems = [
  {
    text: "Dashboard",
    icon: <Dashboard />,
    path: ROUTES.DASHBOARD,
    enabled: true,
  },

  {
    text: "Users",
    icon: <Person />,
    path: ROUTES.USERS,
    enabled: true,
  },

  {
    text: "Employees",
    icon: <People />,
    path: ROUTES.EMPLOYEES,
    enabled: true,
  },

  {
    text: "Departments",
    icon: <Business />,
    path: ROUTES.DEPARTMENTS,
    enabled: true,
  },

  {
    text: "Attendance",
    icon: <EventAvailable />,
    path: ROUTES.ATTENDANCE,
    enabled: true,
  },

  {
    text: "Leave",
    icon: <BeachAccess />,
    path: ROUTES.LEAVE,
    enabled: true,
  },

];

function Sidebar() {
  const location = useLocation();
  const navigate = useNavigate();

  return (
    <Box
      sx={{
        width: {
          xs: 220,
          md: 260,
        },

        bgcolor: "#0F172A",

        color: "white",

        display: "flex",

        flexDirection: "column",

        minHeight: "100vh",
      }}
    >

      {/* =========================
          APPLICATION LOGO / TITLE
      ========================== */}

      <Typography
        variant="h5"
        fontWeight="bold"
        sx={{
          p: 3,

          textAlign: "center",

          borderBottom:
            "1px solid rgba(255,255,255,0.1)",
        }}
      >
        EMS
      </Typography>


      {/* =========================
          NAVIGATION MENU
      ========================== */}

      <List sx={{ mt: 2 }}>

        {menuItems.map((item) => {

          const selected =
            location.pathname === item.path;

          return (
            <ListItemButton
              key={item.text}

              disabled={!item.enabled}

              onClick={() => {
                if (item.enabled) {
                  navigate(item.path);
                }
              }}

              selected={selected}

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

                "&.Mui-disabled": {
                  color:
                    "rgba(255,255,255,0.55)",

                  opacity: 0.7,
                },
              }}
            >

              <ListItemIcon
                sx={{
                  color: "inherit",

                  minWidth: 42,
                }}
              >
                {item.icon}
              </ListItemIcon>

              <ListItemText
                primary={item.text}
              />

            </ListItemButton>
          );
        })}

      </List>

    </Box>
  );
}

export default Sidebar;