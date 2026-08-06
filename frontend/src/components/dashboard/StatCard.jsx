import {
  Card,
  CardContent,
  Typography,
  Box,
} from "@mui/material";

function StatCard({
  title,
  value,
  icon,
  color,
}) {
  return (
    <Card
      elevation={4}
      sx={{
        borderRadius: 4,
        transition: "0.3s",
        cursor: "pointer",
        overflow: "hidden",

        "&:hover": {
          transform: "translateY(-6px)",
          boxShadow: 10,
        },
      }}
    >
      <Box
        sx={{
          height: 8,
          bgcolor: color,
        }}
      />

      <CardContent>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <Box>
            <Typography
              variant="body2"
              color="text.secondary"
            >
              {title}
            </Typography>

            <Typography
              variant="h4"
              fontWeight="bold"
              sx={{ mt: 1 }}
            >
              {value}
            </Typography>
          </Box>

          <Box
            sx={{
              width: 60,
              height: 60,
              borderRadius: "50%",
              bgcolor: `${color}20`,
              color: color,
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              fontSize: 30,
            }}
          >
            {icon}
          </Box>
        </Box>
      </CardContent>
    </Card>
  );
}

export default StatCard;