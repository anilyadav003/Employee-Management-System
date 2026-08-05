import { Grid } from "@mui/material";

import {
  People,
  Business,
  EventAvailable,
  BeachAccess,
} from "@mui/icons-material";

import StatCard from "./StatCard";

function DashboardCards() {
  return (
    <Grid container spacing={3}>
      <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
        <StatCard
          title="Total Employees"
          value="245"
          icon={<People fontSize="large" />}
          color="#2563EB"
        />
      </Grid>

      <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
        <StatCard
          title="Departments"
          value="12"
          icon={<Business fontSize="large" />}
          color="#10B981"
        />
      </Grid>

      <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
        <StatCard
          title="Attendance"
          value="98%"
          icon={<EventAvailable fontSize="large" />}
          color="#F59E0B"
        />
      </Grid>

      <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
        <StatCard
          title="Leave Requests"
          value="18"
          icon={<BeachAccess fontSize="large" />}
          color="#EF4444"
        />
      </Grid>
    </Grid>
  );
}

export default DashboardCards;