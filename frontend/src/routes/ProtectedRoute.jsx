import { Navigate, Outlet } from "react-router-dom";

import { isAuthenticated } from "../utils/tokenStorage";

function ProtectedRoute() {
  if (!isAuthenticated()) {
    return (
      <Navigate
        to="/"
        replace
      />
    );
  }

  return <Outlet />;
}

export default ProtectedRoute;