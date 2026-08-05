import React from "react";
import { TextField } from "@mui/material";

const AppInput = React.forwardRef(
  (
    {
      startIcon,
      endIcon,
      InputProps,
      ...props
    },
    ref
  ) => {
    return (
      <TextField
        fullWidth
        margin="normal"
        variant="outlined"
        inputRef={ref}
        InputProps={{
          startAdornment: startIcon,
          endAdornment: endIcon,
          ...InputProps,
        }}
        sx={{
          "& .MuiOutlinedInput-root": {
            borderRadius: "14px",
            height: 56,

            "&:hover fieldset": {
              borderColor: "#2563EB",
            },

            "&.Mui-focused fieldset": {
              borderWidth: 2,
              borderColor: "#2563EB",
            },
          },
        }}
        {...props}
      />
    );
  }
);

AppInput.displayName = "AppInput";

export default AppInput;