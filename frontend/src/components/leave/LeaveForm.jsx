import { useEffect, useState } from "react";

const initialForm = {
  employeeId: "",
  startDate: "",
  endDate: "",
  reason: "",
};

const LeaveForm = ({
  employees = [],
  initialData = null,
  onSubmit,
  onCancel,
  loading = false,
}) => {
  const [formData, setFormData] = useState(initialForm);

  useEffect(() => {
    if (initialData) {
      setFormData({
        employeeId: initialData.employeeId ?? "",
        startDate: initialData.startDate ?? "",
        endDate: initialData.endDate ?? "",
        reason: initialData.reason ?? "",
      });
    } else {
      setFormData(initialForm);
    }
  }, [initialData]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    await onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="leave-form">

      <div className="form-group">
        <label htmlFor="employeeId">
          Employee
        </label>

        <select
          id="employeeId"
          name="employeeId"
          value={formData.employeeId}
          onChange={handleChange}
          required
        >
          <option value="">
            Select Employee
          </option>

          {employees.map((employee) => (
            <option
              key={employee.id}
              value={employee.id}
            >
              {employee.firstName}{" "}
              {employee.lastName}
              {employee.employeeCode
                ? ` (${employee.employeeCode})`
                : ""}
            </option>
          ))}
        </select>
      </div>

      <div className="form-group">
        <label htmlFor="startDate">
          Start Date
        </label>

        <input
          id="startDate"
          type="date"
          name="startDate"
          value={formData.startDate}
          onChange={handleChange}
          required
        />
      </div>

      <div className="form-group">
        <label htmlFor="endDate">
          End Date
        </label>

        <input
          id="endDate"
          type="date"
          name="endDate"
          value={formData.endDate}
          onChange={handleChange}
          required
        />
      </div>

      <div className="form-group">
        <label htmlFor="reason">
          Reason
        </label>

        <textarea
          id="reason"
          name="reason"
          value={formData.reason}
          onChange={handleChange}
          rows="4"
          required
        />
      </div>

      <div className="form-actions">
        <button
          type="submit"
          disabled={loading}
        >
          {loading
            ? "Saving..."
            : initialData
              ? "Update Leave"
              : "Apply Leave"}
        </button>

        {onCancel && (
          <button
            type="button"
            onClick={onCancel}
            disabled={loading}
          >
            Cancel
          </button>
        )}
      </div>

    </form>
  );
};

export default LeaveForm;