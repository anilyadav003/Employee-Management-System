import { TextField } from "@mui/material";

function AppInput(props) {
  return (
    <TextField
      fullWidth
      margin="normal"
      variant="outlined"
      {...props}
    />
  );
}

export default AppInput;