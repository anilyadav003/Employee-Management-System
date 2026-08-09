import {
  Card,
  CardContent,
  Typography,
} from "@mui/material";

import { PieChart } from "@mui/x-charts/PieChart";

function DepartmentChart({
  departmentDistribution = [],
}) {
  const chartData = departmentDistribution.map(
    (item, index) => ({
      id: index,
      value: item.count,
      label: item.department,
    })
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
          Department Distribution
        </Typography>

        {chartData.length > 0 ? (
          <PieChart
            height={300}
            series={[
              {
                data: chartData,
              },
            ]}
          />
        ) : (
          <Typography color="text.secondary">
            No department data available.
          </Typography>
        )}
      </CardContent>
    </Card>
  );
}

export default DepartmentChart;