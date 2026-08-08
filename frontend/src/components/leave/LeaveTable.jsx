const LeaveTable = ({
  leaves = [],
  loading = false,
  onEdit,
  onDelete,
}) => {
  if (loading) {
    return (
      <div className="table-loading">
        Loading leave records...
      </div>
    );
  }

  if (leaves.length === 0) {
    return (
      <div className="empty-state">
        No leave records found.
      </div>
    );
  }

  return (
    <div className="table-container">
      <table className="leave-table">
        <thead>
          <tr>
            <th>Employee</th>
            <th>Employee Code</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Reason</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {leaves.map((leave) => (
            <tr key={leave.id}>

              <td>
                {leave.employee?.firstName || ""}{" "}
                {leave.employee?.lastName || ""}
              </td>

              <td>
                {leave.employee?.employeeCode || "-"}
              </td>

              <td>
                {leave.startDate || "-"}
              </td>

              <td>
                {leave.endDate || "-"}
              </td>

              <td>
                {leave.reason || "-"}
              </td>

              <td>
                <span
                  className={`leave-status ${
                    leave.status
                      ? leave.status.toLowerCase()
                      : ""
                  }`}
                >
                  {leave.status || "-"}
                </span>
              </td>

              <td>
                <div className="table-actions">

                  {onEdit && (
                    <button
                      type="button"
                      onClick={() => onEdit(leave)}
                      title="Edit Leave"
                    >
                      Edit
                    </button>
                  )}

                  {onDelete && (
                    <button
                      type="button"
                      onClick={() => onDelete(leave)}
                      title="Delete Leave"
                    >
                      Delete
                    </button>
                  )}

                </div>
              </td>

            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default LeaveTable;