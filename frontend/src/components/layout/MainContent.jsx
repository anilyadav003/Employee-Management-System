import { Box, Typography, Grid } from "@mui/material";

import DashboardCards from "../dashboard/DashboardCards";
import EmployeeGrowthChart from "../dashboard/EmployeeGrowthChart";
import DepartmentChart from "../dashboard/DepartmentChart";

function MainContent() {
  return (
    <Box
      sx={{
        flex: 1,
        p: 4,
        bgcolor: "#F5F7FA",
      }}
    >
      <Typography
        variant="h4"
        fontWeight="bold"
      >
        Employee Management Dashboard
      </Typography>

      <Typography
        color="text.secondary"
        sx={{ mb: 4 }}
      >
        Welcome to the Enterprise Employee Management System.
      </Typography>

      <DashboardCards />

      <Grid
        container
        spacing={3}
        sx={{ mt: 2 }}
      >
        <Grid size={{ xs: 12, md: 8 }}>
          <EmployeeGrowthChart />
        </Grid>

        <Grid size={{ xs: 12, md: 4 }}>
          <DepartmentChart />
        </Grid>
      </Grid>
    </Box>
  );
}

export default MainContent;