import { Card, CardContent, Typography } from "@mui/material";
import { LineChart } from "@mui/x-charts/LineChart";

function EmployeeGrowthChart() {
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

        <LineChart
          height={300}
          xAxis={[
            {
              scaleType: "point",
              data: [
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
              ],
            },
          ]}
          series={[
            {
              data: [80, 95, 110, 135, 180, 245],
              label: "Employees",
            },
          ]}
        />
      </CardContent>
    </Card>
  );
}

export default EmployeeGrowthChart;