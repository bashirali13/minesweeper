import React from "react";

const Board = ({ board, mineMap, onReveal, onFlag }) => {
  return (
    <div className="board">
      {board.map((row, rowIndex) => (
        <div key={rowIndex} className="board-row">
          {row.map((cell, colIndex) => {
            let imageSrc = `${process.env.PUBLIC_URL}/images/hidden.png`; // default to hidden

            if (cell.revealed) {
              if (cell.hasMine) {
                imageSrc = `${process.env.PUBLIC_URL}/images/triggered_mine.png`; // Mine revealed
              } else if (cell.neighboringMines > 0) {
                imageSrc = `${process.env.PUBLIC_URL}/images/${cell.neighboringMines}.png`; // Revealed with neighbors
              } else {
                imageSrc = `${process.env.PUBLIC_URL}/images/revealed.png`; // Revealed with no neighbors
              }
            } else if (cell.flagged) {
              imageSrc = `${process.env.PUBLIC_URL}/images/flagged.png`; // Flagged cell
            }
            
            

            return (
              <img
                key={`${rowIndex}-${colIndex}`}
                src={imageSrc}
                alt={`cell-${rowIndex}-${colIndex}`}
                className="cell"
                onClick={() => {
                  // Only allow reveal if cell is not flagged
                  if (!cell.flagged) onReveal(rowIndex, colIndex);
                }}
                onContextMenu={(e) => {
                  e.preventDefault();
                  onFlag(rowIndex, colIndex);
                }}
              />
            );
          })}
        </div>
      ))}
    </div>
  );
};

export default Board;
