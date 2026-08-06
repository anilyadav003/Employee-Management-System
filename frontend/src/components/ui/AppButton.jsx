import { Button } from "@mui/material";

function AppButton({
  children,
  variant = "contained",
  fullWidth = true,
  ...props
}) {
  return (
    <Button
      variant={variant}
      fullWidth={fullWidth}
      size="large"
      sx={{
        borderRadius: "12px",
        py: 1.5,
        fontWeight: 600,
      }}
      {...props}
    >
      {children}
    </Button>
  );
}

export default AppButton;