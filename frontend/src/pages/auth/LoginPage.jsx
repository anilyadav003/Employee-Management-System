import { Box, Grid } from "@mui/material";
import LoginForm from "../../components/auth/LoginForm";

function LoginPage() {
  return (
    <Box
      sx={{
        minHeight: "100vh",
        bgcolor: "background.default",
      }}
    >
      <Grid container sx={{ minHeight: "100vh" }}>
        {/* Left Side */}
        <Grid
          size={{ xs: 0, md: 6 }}
          sx={{
            display: { xs: "none", md: "flex" },
            alignItems: "center",
            justifyContent: "center",
            bgcolor: "primary.main",
            color: "white",
          }}
        >
          <Box>
            <h1>Employee Management System</h1>
            <p>Enterprise Workforce Management</p>
          </Box>
        </Grid>

        {/* Right Side */}
        <Grid
          size={{ xs: 12, md: 6 }}
          display="flex"
          justifyContent="center"
          alignItems="center"
        >
          <LoginForm />
        </Grid>
      </Grid>
    </Box>
  );
}

export default LoginPage;