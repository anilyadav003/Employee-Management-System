import { Card, CardContent, Typography } from "@mui/material";
import { PieChart } from "@mui/x-charts/PieChart";

function DepartmentChart() {
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

        <PieChart
          height={300}
          series={[
            {
              data: [
                { id: 0, value: 40, label: "IT" },
                { id: 1, value: 20, label: "HR" },
                { id: 2, value: 15, label: "Finance" },
                { id: 3, value: 10, label: "Marketing" },
                { id: 4, value: 15, label: "Sales" },
              ],
            },
          ]}
        />
      </CardContent>
    </Card>
  );
}

export default DepartmentChart;