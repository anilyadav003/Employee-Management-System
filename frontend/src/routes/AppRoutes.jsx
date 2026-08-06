import { BrowserRouter, Routes, Route } from "react-router-dom";

import LoginPage from "../pages/auth/LoginPage";
import DashboardLayout from "../layouts/DashboardLayout";
import EmployeePage from "../pages/employee/EmployeePage";
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

        {/* Employees */}
        <Route
          path={ROUTES.EMPLOYEES}
          element={<EmployeePage />}
        />

        {/* 404 */}
        <Route
          path="*"
          element={<NotFoundPage />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;