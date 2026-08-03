import {
  Typography,
  Stack,
  Link,
} from "@mui/material";

import {
  AppButton,
  AppCard,
  AppInput,
  AppCheckbox,
} from "../ui";

function LoginForm() {
  return (
    <AppCard>
      <Typography variant="h4" fontWeight={700} gutterBottom>
        Welcome Back 👋
      </Typography>

      <Typography
        color="text.secondary"
        sx={{ mb: 3 }}
      >
        Sign in to continue to Employee Management System.
      </Typography>

      <AppInput
        label="Email Address"
        type="email"
      />

      <AppInput
        label="Password"
        type="password"
      />

      <Stack
        direction="row"
        justifyContent="space-between"
        alignItems="center"
        sx={{ mt: 1, mb: 3 }}
      >
        <AppCheckbox label="Remember me" />

        <Link
          href="#"
          underline="hover"
        >
          Forgot Password?
        </Link>
      </Stack>

      <AppButton>
        Sign In
      </AppButton>
    </AppCard>
  );
}

export default LoginForm;