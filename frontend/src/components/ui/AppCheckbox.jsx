import { Checkbox, FormControlLabel } from "@mui/material";

function AppCheckbox({ label, ...props }) {
  return (
    <FormControlLabel
      control={<Checkbox {...props} />}
      label={label}
    />
  );
}

export default AppCheckbox;