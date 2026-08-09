import {
  Card,
  CardContent,
  Typography,
} from "@mui/material";

import { LineChart } from "@mui/x-charts/LineChart";

const MONTHS = [
  "Jan",
  "Feb",
  "Mar",
  "Apr",
  "May",
  "Jun",
  "Jul",
  "Aug",
  "Sep",
  "Oct",
  "Nov",
  "Dec",
];

function EmployeeGrowthChart({
  employeeGrowth = [],
}) {
  const labels = MONTHS.slice(
    0,
    employeeGrowth.length
  );

  return (
    <Card
      elevation={4}
      sx={{
        borderRadius: 4,
        height: "100%",
      }}
    >
      <CardContent>
        <Typography
          variant="h6"
          fontWeight="bold"
          mb={2}
        >
          Employee Growth
        </Typography>

        {employeeGrowth.length > 0 ? (
          <LineChart
            height={300}
            xAxis={[
              {
                scaleType: "point",
                data: labels,
              },
            ]}
            series={[
              {
                data: employeeGrowth,
                label: "Employees",
              },
            ]}
          />
        ) : (
          <Typography color="text.secondary">
            No employee growth data available.
          </Typography>
        )}
      </CardContent>
    </Card>
  );
}

export default EmployeeGrowthChart;