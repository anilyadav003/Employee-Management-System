import { Card } from "@mui/material";

function AppCard({ children }) {
  return (
    <Card
      elevation={8}
      sx={{
        borderRadius: 4,
        p: 4,
      }}
    >
      {children}
    </Card>
  );
}

export default AppCard;