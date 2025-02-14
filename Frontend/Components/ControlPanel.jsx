import React from "react";
import axios from "axios";

const ControlPanel = ({ onStart, onStop, isRunning }) => {
  const handleStart = async () => {
    try {
      await axios.post("http://localhost:8080/api/start");
      onStart();
    } catch (error) {
      console.error("Error starting the system:", error);
    }
  };

  const handleStop = async () => {
    try {
      await axios.post("http://localhost:8080/api/stop");
      onStop();
    } catch (error) {
      console.error("Error stopping the system:", error);
    }
  };

  

  return (
    <div className="panel controlPanel">
      <h2>Control Panel</h2>
      <button className="startBtn" onClick={handleStart} disabled={isRunning}>
        Start System
      </button>
      <button className="stopBtn" onClick={handleStop} disabled={!isRunning}>
        Stop System
      </button>
    </div>
  );
};

export default ControlPanel;
