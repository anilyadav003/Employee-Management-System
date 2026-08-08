import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  Typography,
  Stack,
  Link,
  InputAdornment,
  IconButton,
} from "@mui/material";

import {
  PersonOutlineOutlined,
  LockOutlined,
  Visibility,
  VisibilityOff,
} from "@mui/icons-material";

import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";

import { toast } from "react-toastify";

import loginSchema from "../../validation/loginSchema";
import { login } from "../../services/authService";
import { saveAuthData } from "../../utils/tokenStorage";

import {
  AppButton,
  AppCard,
  AppInput,
  AppCheckbox,
} from "../ui";

function LoginForm() {
  const navigate = useNavigate();

  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(loginSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const onSubmit = async (data) => {
    try {
      setLoading(true);

      const response = await login(data);

      console.log("Backend Response:", response);

      saveAuthData(response);

      toast.success("Login successful!");

      navigate("/dashboard", {
        replace: true,
      });
    } catch (error) {
      console.error(error);

      toast.error(
        error.response?.data?.message ||
          error.response?.data?.error ||
          "Invalid username or password."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <AppCard>
      <Typography
        variant="h4"
        fontWeight={700}
        gutterBottom
      >
        Welcome Back 👋
      </Typography>

      <Typography
        color="text.secondary"
        sx={{ mb: 3 }}
      >
        Sign in to continue to Employee Management System.
      </Typography>

      <form
        onSubmit={handleSubmit(onSubmit)}
        noValidate
      >
        <AppInput
          label="Username"
          type="text"
          startIcon={
            <InputAdornment position="start">
              <PersonOutlineOutlined />
            </InputAdornment>
          }
          error={!!errors.username}
          helperText={errors.username?.message}
          {...register("username")}
        />

        <AppInput
          label="Password"
          type={showPassword ? "text" : "password"}
          startIcon={
            <InputAdornment position="start">
              <LockOutlined />
            </InputAdornment>
          }
          endIcon={
            <InputAdornment position="end">
              <IconButton
                edge="end"
                type="button"
                onClick={() =>
                  setShowPassword((previous) => !previous)
                }
              >
                {showPassword ? (
                  <VisibilityOff />
                ) : (
                  <Visibility />
                )}
              </IconButton>
            </InputAdornment>
          }
          error={!!errors.password}
          helperText={errors.password?.message}
          {...register("password")}
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
            onClick={(event) => event.preventDefault()}
          >
            Forgot Password?
          </Link>
        </Stack>

        <AppButton
          type="submit"
          disabled={loading}
        >
          {loading
            ? "Signing In..."
            : "Sign In →"}
        </AppButton>
      </form>
    </AppCard>
  );
}

export default LoginForm;