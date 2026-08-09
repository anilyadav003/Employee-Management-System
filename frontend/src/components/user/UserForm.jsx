import { useState, useEffect } from "react";

import {
  Grid,
  TextField,
  Button,
  MenuItem,
} from "@mui/material";

const initialState = {
  username: "",
  email: "",
  password: "",
  role: "EMPLOYEE",
};

function UserForm({
  user,
  onSubmit,
}) {
  const [formData, setFormData] = useState(initialState);

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (user) {
      setFormData({
        username: user.username,
        email: user.email,
        password: "",
        role: user.role,
      });
    } else {
      setFormData(initialState);
    }
  }, [user]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);

      await onSubmit(formData);

      if (!user) {
        setFormData(initialState);
      }

    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <Grid
        container
        spacing={2}
        sx={{ mt: 1 }}
      >
        <Grid size={{ xs: 12 }}>
          <TextField
            fullWidth
            required
            label="Username"
            name="username"
            value={formData.username}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12 }}>
          <TextField
            fullWidth
            required
            label="Email"
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12 }}>
          <TextField
            fullWidth
            type="password"
            label={
              user
                ? "New Password (Optional)"
                : "Password"
            }
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </Grid>

        <Grid size={{ xs: 12 }}>
          <TextField
            select
            fullWidth
            label="Role"
            name="role"
            value={formData.role}
            onChange={handleChange}
          >
            <MenuItem value="ADMIN">
              ADMIN
            </MenuItem>

            <MenuItem value="HR">
              HR
            </MenuItem>

            <MenuItem value="EMPLOYEE">
              EMPLOYEE
            </MenuItem>
          </TextField>
        </Grid>

        <Grid size={{ xs: 12 }}>
          <Button
            fullWidth
            type="submit"
            variant="contained"
            disabled={loading}
            sx={{
              mt: 2,
              py: 1.5,
              borderRadius: 2,
            }}
          >
            {loading
              ? "Saving..."
              : user
                ? "Update User"
                : "Save User"}
          </Button>
        </Grid>
      </Grid>
    </form>
  );
}

export default UserForm;