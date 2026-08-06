import { Box, Typography } from "@mui/material";
import { AppLogo } from "../ui";
import Illustration from "../../assets/images/login-illustration.svg";

function BrandingPanel() {
  return (
    <Box
      sx={{
        height: "100%",
        width: "100%",
        background:
          "linear-gradient(135deg,#2563EB 0%,#1D4ED8 55%,#0F172A 100%)",
        color: "white",
        position: "relative",
        overflow: "hidden",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        p: 8,
      }}
    >
      <Box
        sx={{
          position: "absolute",
          width: 220,
          height: 220,
          borderRadius: "50%",
          background: "rgba(255,255,255,0.08)",
          top: -60,
          right: -60,
        }}
      />

      <Box
        sx={{
          position: "absolute",
          width: 300,
          height: 300,
          borderRadius: "50%",
          background: "rgba(255,255,255,0.05)",
          bottom: -120,
          left: -100,
        }}
      />

      <AppLogo />

      <Typography
        variant="h3"
        fontWeight={700}
        sx={{
          textAlign: "center",
          mt: 4,
        }}
      >
        Employee Management System
      </Typography>

      <Typography
        variant="h6"
        sx={{
          textAlign: "center",
          opacity: 0.85,
          mt: 2,
          maxWidth: 430,
        }}
      >
        Enterprise Workforce Platform
      </Typography>

      <Box
        component="img"
        src={Illustration}
        alt="Employee Illustration"
        sx={{
          width: "75%",
          mt: 6,
        }}
      />

      <Typography
        sx={{
          mt: 5,
          opacity: 0.75,
          textAlign: "center",
          maxWidth: 400,
        }}
      >
        Manage employees, departments, attendance,
        payroll, and performance from one secure platform.
      </Typography>
    </Box>
  );
}

export default BrandingPanel;