import { BrowserRouter, Routes, Route } from "react-router-dom";

import LoginPage from "../pages/auth/LoginPage";
import DashboardLayout from "../layouts/DashboardLayout";

import MainContent from "../components/layout/MainContent";
import UserPage from "../pages/user/UserPage";
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

        {/* Dashboard Layout */}
        <Route element={<DashboardLayout />}>

          <Route
            path={ROUTES.DASHBOARD}
            element={<MainContent />}
          />

          <Route
            path={ROUTES.USERS}
            element={<UserPage />}
          />

          <Route
            path={ROUTES.EMPLOYEES}
            element={<EmployeePage />}
          />

        </Route>

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