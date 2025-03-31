import React, { useState, useEffect, useRef } from "react";
import Board from "./components/Board";
import "./styles/app.css";

function App() {
  const [board, setBoard] = useState(null);  
  const [mineMap, setMineMap] = useState(null); 
  const [rows] = useState(10);
  const [cols] = useState(10);
  const [gameOver, setGameOver] = useState(false);
  const [gameWon, setGameWon] = useState(false); // new state for win status
  const apiCalled = useRef(false);
  
  useEffect(() => {
    if(apiCalled.current) return;
    apiCalled.current = true;
    console.log("Fetching board...");
    fetch(`http://localhost:8080/api/game/start-game?rows=${rows}&cols=${cols}&mineCount=10`, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((data) => {
        console.log("API Response Data:", data);  
        setBoard(data.board); 
        setMineMap(data.mineMap);
      })
      .catch((err) => console.error("Error starting game:", err));
  }, [rows, cols]);  

  const handleRevealCell = (row, col) => {
    if (gameOver) return;
    fetch(`http://localhost:8080/api/game/reveal-cell?row=${row}&col=${col}`, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((data) => {
        console.log("Reveal response:", data);
        setBoard(data.board);
        if (data.gameOver) {
          setGameOver(true);
          setGameWon(data.gameWon); 
          console.log("Game Won:", data.gameWon);
        }
      })
      .catch((err) => console.error("Error revealing cell:", err));
  };

  const handleFlagCell = (row, col) => {
    if (gameOver) return;
    fetch(`http://localhost:8080/api/game/flag-cell?row=${row}&col=${col}`, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((data) => {
        console.log("Flag response:", data);
        setBoard(data.board);
      })
      .catch((err) => console.error("Error flagging cell:", err));
  };

  const startNewGame = () => {
    setGameOver(false); 
    setGameWon(false);
    apiCalled.current = false; 
    fetch(`http://localhost:8080/api/game/start-game?rows=${rows}&cols=${cols}&mineCount=10`, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((data) => {
        console.log("New Game Data:", data);
        setBoard(data.board);
        setMineMap(data.mineMap);
      })
      .catch((err) => console.error("Error starting new game:", err));
  };

  if (!board || !mineMap) {
    return <div className="loading-message">Loading...</div>;
  }
  return (
    <div className="App">
      <h1>Bashir's Minesweeper++</h1>
      <Board board={board} mineMap={mineMap} onReveal={handleRevealCell} onFlag={handleFlagCell} />  
      <button onClick={startNewGame}>Start New Game</button>
      {gameOver && (
        <div className={`game-status ${gameWon ? "win" : "lose"}`}>
          {gameWon ? "You Won! :)" : "You Lost! :("}
        </div>
      )}
    </div>
  );
}

export default App;
