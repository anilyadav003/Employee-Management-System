import { useEffect, useState } from "react";

import {
  Alert,
  Box,
  CircularProgress,
  Grid,
  Typography,
} from "@mui/material";

import DashboardCards from "../dashboard/DashboardCards";
import EmployeeGrowthChart from "../dashboard/EmployeeGrowthChart";
import DepartmentChart from "../dashboard/DepartmentChart";

import { getDashboard } from "../../services/dashboardService";

function MainContent() {
  const [dashboard, setDashboard] = useState(null);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await getDashboard();

        setDashboard(data);
      } catch (err) {
        console.error(err);

        setError(
          err.response?.data?.message ||
            "Failed to load dashboard data."
        );
      } finally {
        setLoading(false);
      }
    };

    fetchDashboard();
  }, []);

  return (
    <Box
      sx={{
        flex: 1,
        p: {
          xs: 2,
          md: 4,
        },
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

      {loading && (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            py: 8,
          }}
        >
          <CircularProgress />
        </Box>
      )}

      {!loading && error && (
        <Alert
          severity="error"
          sx={{ mb: 3 }}
        >
          {error}
        </Alert>
      )}

      {!loading && !error && dashboard && (
        <>
          <DashboardCards dashboard={dashboard} />

          <Grid
            container
            spacing={3}
            sx={{ mt: 2 }}
          >
            <Grid size={{ xs: 12, md: 8 }}>
              <EmployeeGrowthChart
                employeeGrowth={dashboard.employeeGrowth}
              />
            </Grid>

            <Grid size={{ xs: 12, md: 4 }}>
              <DepartmentChart
                departmentDistribution={
                  dashboard.departmentDistribution
                }
              />
            </Grid>
          </Grid>
        </>
      )}
    </Box>
  );
}

export default MainContent;