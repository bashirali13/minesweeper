package com.example.backend.controller;

import com.example.backend.model.Cell;
import com.example.backend.model.Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {

    private Game currentGame;

    // Create game endpoint
    @PostMapping("/start-game")
    public Map<String, Object> startGame(@RequestParam int rows, @RequestParam int cols, @RequestParam int mineCount) {
        currentGame = new Game(rows, cols, mineCount);
        Map<String, Object> response = new HashMap<>();

        // starting game status goes in response
        response.put("game", currentGame);

        // putting grid into response like above^ misses the hasMine attr for some reason
        // manually map all needed attr into response t
        Cell[][] grid = currentGame.getBoard().getGrid();
        List<List<Map<String, Object>>> boardWithMines = new ArrayList<>();
        for (Cell[] row : grid) {
            List<Map<String, Object>> rowList = new ArrayList<>();
            for (Cell cell : row) {
                Map<String, Object> cellData = new HashMap<>();
                cellData.put("hasMine", cell.hasMine());
                cellData.put("neighboringMines", cell.getNeighboringMines());
                cellData.put("revealed", cell.isRevealed());
                cellData.put("flagged", cell.isFlagged());
                rowList.add(cellData);
            }
            boardWithMines.add(rowList);
        }
        response.put("board", boardWithMines);

        // put current mine status in response
        boolean[][] mineMap = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                mineMap[row][col] = currentGame.getBoard().getGrid()[row][col].hasMine();
            }
        }
        response.put("mineMap", mineMap);

        return response;
    }

    // Reveal cell endpoint
    @PostMapping("/reveal-cell")
    public Map<String, Object> revealCell(@RequestParam int row, @RequestParam int col) {
        Map<String, Object> response = new HashMap<>();
        if (currentGame == null) {
            response.put("message", "No game started yet.");
            return response;
        }

        boolean isMine = currentGame.revealCell(row, col);
        boolean gameOver = currentGame.isGameOver() || currentGame.isGameWon();
        boolean gameWon = currentGame.isGameWon();
        if (isMine) {
            response.put("message", "Game Over! You hit a mine.");
        } else if (gameWon) {
            response.put("message", "Congratulations! You won the game.");
        } else {
            response.put("message", "Cell revealed successfully.");
        }
        response.put("gameOver", gameOver);
        response.put("gameWon", gameWon);
        // manually map grid into response
        Cell[][] grid = currentGame.getBoard().getGrid();
        List<List<Map<String, Object>>> boardWithMines = new ArrayList<>();
        for (Cell[] rowCells : grid) {
            List<Map<String, Object>> rowList = new ArrayList<>();
            for (Cell cell : rowCells) {
                Map<String, Object> cellData = new HashMap<>();
                cellData.put("hasMine", cell.hasMine());
                cellData.put("neighboringMines", cell.getNeighboringMines());
                cellData.put("revealed", cell.isRevealed());
                cellData.put("flagged", cell.isFlagged());
                rowList.add(cellData);
            }
            boardWithMines.add(rowList);
        }
        response.put("board", boardWithMines);

        return response;
    }

    // Flag cell endpoint
    @PostMapping("/flag-cell")
    public Map<String, Object> flagCell(@RequestParam int row, @RequestParam int col) {
        Map<String, Object> response = new HashMap<>();
        if (currentGame == null) {
            response.put("message", "No game started yet.");
            return response;
        }

        currentGame.flagCell(row, col);

        // manually map grid into response
        Cell[][] grid = currentGame.getBoard().getGrid();
        List<List<Map<String, Object>>> boardWithMines = new ArrayList<>();
        for (Cell[] rowCells : grid) {
            List<Map<String, Object>> rowList = new ArrayList<>();
            for (Cell cell : rowCells) {
                Map<String, Object> cellData = new HashMap<>();
                cellData.put("hasMine", cell.hasMine());
                cellData.put("neighboringMines", cell.getNeighboringMines());
                cellData.put("revealed", cell.isRevealed());
                cellData.put("flagged", cell.isFlagged());
                rowList.add(cellData);
            }
            boardWithMines.add(rowList);
        }
        response.put("board", boardWithMines);
        response.put("message", "Cell flag toggled successfully.");
        return response;
    }

    // Get game status endpoint
    @GetMapping("/game-status")
    public String gameStatus() {
        if (currentGame == null) {
            return "No game started yet.";
        }

        if (currentGame.isGameOver()) {
            return "Game Over! You lost.";
        }

        if (currentGame.isGameWon()) {
            return "Congratulations! You won!";
        }

        return "Game is still in progress.";
    }
}
