import { createTheme } from "@mui/material/styles";

import colors from "./colors";
import typography from "./typography";
import components from "./components";
import shadows from "./shadows";

const theme = createTheme({
  palette: colors,
  typography,
  components,
  shadows,
});

export default theme;