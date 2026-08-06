import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";

function EmployeeTable({ employees }) {
  return (
    <TableContainer component={Paper}>
      <Table>

        <TableHead>
          <TableRow>
            <TableCell>
              Employee Code
            </TableCell>

            <TableCell>
              Name
            </TableCell>

            <TableCell>
              Department
            </TableCell>

            <TableCell>
              Designation
            </TableCell>

            <TableCell>
              Salary
            </TableCell>
          </TableRow>
        </TableHead>

        <TableBody>

          {employees.map((employee) => (
            <TableRow key={employee.id}>

              <TableCell>
                {employee.employeeCode}
              </TableCell>

              <TableCell>
                {employee.firstName}{" "}
                {employee.lastName}
              </TableCell>

              <TableCell>
                {employee.departmentName}
              </TableCell>

              <TableCell>
                {employee.designation}
              </TableCell>

              <TableCell>
                ₹{employee.salary}
              </TableCell>

            </TableRow>
          ))}

        </TableBody>

      </Table>
    </TableContainer>
  );
}

export default EmployeeTable;