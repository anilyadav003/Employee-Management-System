import { Grid } from "@mui/material";

import {
  People,
  Business,
  EventAvailable,
  BeachAccess,
} from "@mui/icons-material";

import StatCard from "./StatCard";

function DashboardCards({ dashboard }) {
  return (
    <Grid container spacing={3}>

      <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
        <StatCard
          title="Total Employees"
          value={dashboard?.totalEmployees ?? 0}
          icon={<People fontSize="large" />}
          color="#2563EB"
        />
      </Grid>

      <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
        <StatCard
          title="Departments"
          value={dashboard?.totalDepartments ?? 0}
          icon={<Business fontSize="large" />}
          color="#10B981"
        />
      </Grid>

      <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
        <StatCard
          title="Attendance"
          value={`${dashboard?.attendance ?? 0}%`}
          icon={<EventAvailable fontSize="large" />}
          color="#F59E0B"
        />
      </Grid>

      <Grid size={{ xs: 12, sm: 6, lg: 3 }}>
        <StatCard
          title="Leave Requests"
          value={dashboard?.leaveRequests ?? 0}
          icon={<BeachAccess fontSize="large" />}
          color="#EF4444"
        />
      </Grid>

    </Grid>
  );
}

export default DashboardCards;