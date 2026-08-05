import { Box, Grid } from "@mui/material";

import BrandingPanel from "../../components/auth/BrandingPanel";
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
          }}
        >
          <BrandingPanel />
        </Grid>

        {/* Right Side */}
        <Grid
          size={{ xs: 12, md: 6 }}
          display="flex"
          justifyContent="center"
          alignItems="center"
          sx={{
            bgcolor: "background.default",
            p: 4,
          }}
        >
          <Box
            sx={{
              width: "100%",
              maxWidth: 500,
            }}
          >
            <LoginForm />
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
}

export default LoginPage;