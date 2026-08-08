import {
  BrowserRouter,
  Routes,
  Route,
} from "react-router-dom";

import LoginPage from "../pages/auth/LoginPage";
import DashboardLayout from "../layouts/DashboardLayout";

import DashboardPage from "../pages/dashboard/DashboardPage";
import UserPage from "../pages/user/UserPage";
import EmployeePage from "../pages/employee/EmployeePage";
import DepartmentPage from "../pages/department/DepartmentPage";
import AttendancePage from "../pages/attendance/AttendancePage";
import LeavePage from "../pages/leave/LeavePage";

import NotFoundPage from "../pages/error/NotFoundPage";

import ProtectedRoute from "./ProtectedRoute";

import ROUTES from "../constants/routes";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>

        {/* =========================
            PUBLIC ROUTES
        ========================== */}

        <Route
          path={ROUTES.LOGIN}
          element={<LoginPage />}
        />


        {/* =========================
            PROTECTED APPLICATION
        ========================== */}

        <Route element={<ProtectedRoute />}>
          <Route element={<DashboardLayout />}>

            {/* Dashboard */}
            <Route
              path={ROUTES.DASHBOARD}
              element={<DashboardPage />}
            />

            {/* Users */}
            <Route
              path={ROUTES.USERS}
              element={<UserPage />}
            />

            {/* Employees */}
            <Route
              path={ROUTES.EMPLOYEES}
              element={<EmployeePage />}
            />

            {/* Departments */}
            <Route
              path={ROUTES.DEPARTMENTS}
              element={<DepartmentPage />}
            />

            {/* Attendance */}
            <Route
              path={ROUTES.ATTENDANCE}
              element={<AttendancePage />}
            />

            {/* Leave */}
            <Route
              path={ROUTES.LEAVE}
              element={<LeavePage />}
            />

          </Route>
        </Route>


        {/* =========================
            UNKNOWN ROUTE
        ========================== */}

        <Route
          path="*"
          element={<NotFoundPage />}
        />

      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;