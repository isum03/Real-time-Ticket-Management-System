import React, { useEffect } from "react";
import { Client } from "@stomp/stompjs";
import axios from "axios";

const LogDisplay = ({ logs, setLogs, isRunning, handleStop }) => {
  useEffect(() => {
    // Fetch initial logs from backend
    const fetchLogs = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/logs");
        setLogs(response.data);
      } catch (error) {
        console.error("Error fetching initial logs:", error);
      }
    };

    // Only fetch logs if the system is running
    if (isRunning) {
      fetchLogs();

      // Set up WebSocket client
      const client = new Client({
        brokerURL: "ws://localhost:8080/ws",
        onConnect: () => {
          client.subscribe("/topic/logs", (message) => {
            setLogs((prevLogs) => [...prevLogs, message.body]);
          });
        },
        debug: (str) => {
          console.log(str);
        },
      });

      client.activate();

      return () => {
        client.deactivate();
      };
    }
  }, [isRunning, setLogs]);

  const stopLogging = async () => {
    try {
      await axios.post("http://localhost:8080/api/stop");
      handleStop(); // Call the stop handler to update the app state
      console.log("Logging and system stopped successfully.");
    } catch (error) {
      console.error("Error stopping the system:", error);
    }
  };

  return (
    <div className="panel logPanel">
      <h2>Logs</h2>
      <div>
        {isRunning ? (
          <button onClick={stopLogging}>Stop Logging</button>
        ) : (
          <p>System is stopped. No logs available.</p>
        )}

        {logs.length > 0 &&
          logs.map((log, index) => <p key={index}>{log}</p>)}
      </div>  
    </div>
  );
};

export default LogDisplay;
