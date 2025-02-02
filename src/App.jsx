import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Configuration from './Components/Configuration';
import ControlPanel from './Components/ControlPanel';
import LogDisplay from './Components/LogDisplay';
import TicketDisplay from './Components/TicketDisplay';
import './app.css'

const App = () => {
  const [config, setConfig] = useState({
  
  });
  const [logs, setLogs] = useState([]);
  const [isRunning, setIsRunning] = useState(false);

  // Handle configuration updates
  const handleConfigUpdate = async (newConfig) => {
    setConfig(newConfig);
    try {
      await axios.post("http://localhost:8080/api/configuration/update", newConfig, {
        headers: { "Content-Type": "application/json" },
      });
      console.log("Configuration updated successfully.");

      // Automatically start the system after updating the configuration
      await handleStart();
    } catch (error) {
      console.error("Error updating configuration:", error);
    }
  };

  // Starting the simulation
  const handleStart = async () => {
    try {
      await axios.post("http://localhost:8080/api/start", config);
      setIsRunning(true);
      console.log("System started successfully.");
    } catch (error) {
      console.error("Error starting the system:", error);
    }
  };

  // Stopping the simulation
  const handleStop = async () => {
    try {
      await axios.post("http://localhost:8080/api/stop");
      setIsRunning(false);
      console.log("System stopped successfully.");
    } catch (error) {
      console.error("Error stopping the system:", error);
    }
  };

  // Fetch logs
  useEffect(() => {
    let interval;
    if (isRunning) {
      interval = setInterval(async () => {
        try {
          const response = await axios.get("http://localhost:8080/api/logs");
          setLogs(response.data);
        } catch (error) {
          console.error("Error fetching logs:", error);
        }
      }, 2000);
    }
    return () => clearInterval(interval);
  }, [isRunning]);

  return (
    <div className="appContainer">
      <h1 className='title'>TICKETING SYSTEM </h1>

      <Configuration initialConfig={config} onConfigUpdate={handleConfigUpdate} />

      <div className='flexContainer'>
        <ControlPanel onStart={handleStart} onStop={handleStop} isRunning={isRunning} />
        <TicketDisplay totalTickets={config.totalTickets} />
      </div>
      <LogDisplay logs={logs} setLogs={setLogs} />
    </div>
  );
};

export default App;