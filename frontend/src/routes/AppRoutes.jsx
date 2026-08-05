import { BrowserRouter, Routes, Route } from "react-router-dom";

import LoginPage from "../pages/auth/LoginPage";
import DashboardLayout from "../layouts/DashboardLayout";
import NotFoundPage from "../pages/error/NotFoundPage";

import ROUTES from "../constants/routes";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Login */}
        <Route
          path={ROUTES.LOGIN}
          element={<LoginPage />}
        />

        {/* Dashboard */}
        <Route
          path={ROUTES.DASHBOARD}
          element={<DashboardLayout />}
        />

        {/* 404 Page */}
        <Route
          path="*"
          element={<NotFoundPage />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;