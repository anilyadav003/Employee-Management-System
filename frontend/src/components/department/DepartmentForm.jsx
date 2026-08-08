import { useEffect, useState } from "react";

import {
  Grid,
  TextField,
  Button,
} from "@mui/material";

const initialState = {
  name: "",
  code: "",
  description: "",
};

function DepartmentForm({
  department,
  onSubmit,
  submitLabel = "Save Department",
}) {
  const [formData, setFormData] =
    useState(initialState);

  const [loading, setLoading] =
    useState(false);

  useEffect(() => {
    if (department) {
      setFormData({
        name: department.name || "",
        code: department.code || "",
        description:
          department.description || "",
      });
    } else {
      setFormData({
        ...initialState,
      });
    }
  }, [department]);

  const handleChange = (event) => {
    const {
      name,
      value,
    } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);

      await onSubmit({
        name: formData.name.trim(),
        code: formData.code.trim(),
        description:
          formData.description.trim(),
      });
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
            label="Department Name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Information Technology"
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            fullWidth
            required
            label="Department Code"
            name="code"
            value={formData.code}
            onChange={handleChange}
            placeholder="IT"
            inputProps={{
              maxLength: 20,
            }}
          />
        </Grid>

        <Grid size={{ xs: 12 }}>
          <TextField
            fullWidth
            multiline
            minRows={3}
            label="Description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            placeholder="Department description"
          />
        </Grid>

        <Grid size={{ xs: 12 }}>
          <Button
            fullWidth
            type="submit"
            variant="contained"
            disabled={loading}
            sx={{
              mt: 1,
              py: 1.5,
              borderRadius: 2,
            }}
          >
            {loading
              ? "Saving..."
              : submitLabel}
          </Button>
        </Grid>
      </Grid>
    </form>
  );
}

export default DepartmentForm;