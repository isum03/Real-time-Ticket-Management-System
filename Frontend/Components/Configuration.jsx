import React, { useState } from "react";
import axios from "axios";

const Configuration = ({ initialConfig, onConfigUpdate }) => {
  const [formData, setFormData] = useState(initialConfig);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: Number(value) });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Send updated configuration to the backend
      const response = await axios.post("http://localhost:8080/api/configuration/update", formData, {
        headers: { "Content-Type": "application/json" },
      });

      // Log response data from the server
      console.log(response.data); 
      alert("Configuration updated successfully!");
      onConfigUpdate(formData); 
    } catch (error) {
      console.error("Error updating configuration:", error);
      alert("Failed to update configuration.");
    }
  };

  return (
    <div className="panel configPage">
      <form onSubmit={handleSubmit} className="form">
        <h2>Configuration</h2>

        <div className="inputBox">
          <label>
            Ticket Releasing Rate:
            <input
              type="number"
              name="ticketReleaseRate"
              value={formData.ticketReleaseRate}
              onChange={handleChange}
            />
          </label>
        </div>
        <br />
        <div className="inputBox">
          <label>
            Total Tickets:
            <input
              type="number"
              name="totalTickets"
              value={formData.totalTickets}
              onChange={handleChange}
            />
          </label>
        </div>
        <br />
        <div className="inputBox">
          <label>
            Retrieving Rate:
            <input
              type="number"
              name="retrievalRate"
              value={formData.retrievalRate}
              onChange={handleChange}
            />
          </label>
        </div>
        <br />
        <div className="inputBox">
          <label>
            Maximum Ticket Capacity:
            <input
              type="number"
              name="maxTicketCapacity"
              value={formData.maxTicketCapacity}
              onChange={handleChange}
            />
          </label>
        </div>
        <div className="confgBtn">
          <button type="submit">Update Configuration</button>
        </div>
      </form>
    </div>
  );
};

export default Configuration;
