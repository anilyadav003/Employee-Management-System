import {
  AppBar,
  Toolbar,
  Typography,
  Box,
  Avatar,
  Button,
  Chip,
} from "@mui/material";

import { useLocation, useNavigate } from "react-router-dom";

import ROUTES from "../../constants/routes";

import {
  clearAuthData,
  getRole,
  getUsername,
} from "../../utils/tokenStorage";

const pageTitles = {
  [ROUTES.DASHBOARD]: "Dashboard",
  [ROUTES.USERS]: "User Management",
  [ROUTES.EMPLOYEES]: "Employee Management",
  [ROUTES.DEPARTMENTS]: "Department Management",
  [ROUTES.ATTENDANCE]: "Attendance",
  [ROUTES.LEAVE]: "Leave Management",
  [ROUTES.PAYROLL]: "Payroll",
  [ROUTES.REPORTS]: "Reports",
  [ROUTES.SETTINGS]: "Settings",
  [ROUTES.PROFILE]: "Profile",
};

function Topbar() {
  const location = useLocation();
  const navigate = useNavigate();

  const username = getUsername() || "User";
  const role = getRole() || "";

  const pageTitle =
    pageTitles[location.pathname] || "Dashboard";

  const avatarLetter = username
    .charAt(0)
    .toUpperCase();

  const handleLogout = () => {
    clearAuthData();

    navigate(ROUTES.LOGIN, {
      replace: true,
    });
  };

  return (
    <AppBar
      position="static"
      elevation={1}
      sx={{
        bgcolor: "white",
        color: "#111827",
      }}
    >
      <Toolbar
        sx={{
          minHeight: 64,
        }}
      >
        <Typography
          variant="h6"
          fontWeight="bold"
        >
          {pageTitle}
        </Typography>

        <Box sx={{ flexGrow: 1 }} />

        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            gap: 1.5,
          }}
        >
          <Box
            sx={{
              display: {
                xs: "none",
                sm: "block",
              },
              textAlign: "right",
            }}
          >
            <Typography
              variant="body2"
              fontWeight={600}
            >
              Welcome, {username}
            </Typography>

            {role && (
              <Chip
                label={role}
                size="small"
                sx={{
                  mt: 0.25,
                  height: 20,
                  fontSize: 11,
                }}
              />
            )}
          </Box>

          <Avatar>
            {avatarLetter}
          </Avatar>

          <Button
            variant="outlined"
            color="inherit"
            onClick={handleLogout}
            sx={{
              ml: 1,
              textTransform: "none",
            }}
          >
            Logout
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}

export default Topbar;