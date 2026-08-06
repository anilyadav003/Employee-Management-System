import { Avatar } from "@mui/material";
import BusinessIcon from "@mui/icons-material/Business";

function AppLogo() {
  return (
    <Avatar
      sx={{
        bgcolor: "white",
        color: "#2563EB",
        width: 72,
        height: 72,
      }}
    >
      <BusinessIcon sx={{ fontSize: 42 }} />
    </Avatar>
  );
}

export default AppLogo;