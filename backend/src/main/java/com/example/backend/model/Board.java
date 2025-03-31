package com.example.backend.model;
import java.util.Random;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private Cell[][] grid;
    private int rows;
    private int cols;
    private int mineCount;

    //Constructor
    public Board(int rows, int cols, int mineCount){
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        this.grid = new Cell[rows][cols];
        generateBoard();
        updateNeighboringMines();
    }

    private void generateBoard(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j] = new Cell();
            }
        }
        Random rand = new Random();
        int placedMines = 0;
        while(placedMines < mineCount){
            int randomRow = rand.nextInt(rows);
            int randomCol = rand.nextInt(cols);
            if(!grid[randomRow][randomCol].hasMine()){
                grid[randomRow][randomCol].setMine(true);
                placedMines++;
            }
        }
    }

    private void updateNeighboringMines(){
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                if (grid[row][col].hasMine()) {
                    continue; 
                }
    
                int neighboringMines = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int neighborRow = row + i;
                        int neighborCol = col + j;
                        if (neighborRow < 0 || neighborRow >= rows || neighborCol < 0 || neighborCol >= cols) {
                            continue;
                        }
                        if (grid[neighborRow][neighborCol].hasMine()) {
                            neighboringMines++;
                        }
                    }
                }
    
                grid[row][col].setNeighboringMines(neighboringMines);
            }
        }
    }

    private boolean firstClick = true;
    public boolean revealCell(int row, int col) {
        if (grid[row][col].isRevealed()) {
            return false;
        }
        if (grid[row][col].getNeighboringMines() == 0 && !grid[row][col].hasMine()) {
            floodFill(row, col);
            firstClick = false;
            return grid[row][col].hasMine();
        } else {
            if (firstClick) {
                firstClick = false; 
                
                if (grid[row][col].getNeighboringMines() == 0 && !grid[row][col].hasMine()) {
                    floodFill(row, col);
                    return grid[row][col].hasMine();
                } else {
                    // if non empty cell, find random non empty cell to start game around
                    int[] randomEmptyCell = findRandomEmptyCell();
                    if (randomEmptyCell != null) {
                        floodFill(randomEmptyCell[0], randomEmptyCell[1]);
                    }
                    // dont reveal original cell
                    return false;
                }
            } else {
                grid[row][col].reveal();
                return grid[row][col].hasMine();
            }
        }
    }
    

    private int[] findRandomEmptyCell(){
        Random rand = new Random();
        List<int[]> emptyCells = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!grid[i][j].hasMine() && grid[i][j].getNeighboringMines() == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            return null; 
        }
    
        return emptyCells.get(rand.nextInt(emptyCells.size()));
    }

    private void floodFill(int row, int col){
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{row, col});
        boolean[][] visited = new boolean[this.rows][this.cols];
    
        while(!stack.isEmpty()){
            int[] currentCell = stack.pop();
            int currentRow = currentCell[0];
            int currentCol = currentCell[1];
    
            // Skip if out of bounds, already revealed, or already visited
            if(currentRow < 0 || currentRow >= this.rows || currentCol < 0 || currentCol >= this.cols 
                || grid[currentRow][currentCol].isRevealed() || visited[currentRow][currentCol]) {
                continue;
            }
            
            // Reveal the current cell
            grid[currentRow][currentCol].reveal();
    
            // If this cell has no neighboring mines, we need to flood fill neighboring cells
            if(grid[currentRow][currentCol].getNeighboringMines() == 0){
                // Visit neighboring cells
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if( i == 0 && j == 0) continue; // Skip the current cell itself
                        int newRow = currentRow + i;
                        int newCol = currentCol + j;
                        // Ensure the neighbor is within bounds and is not already visited or revealed
                        if (newRow >= 0 && newRow < this.rows && newCol >= 0 && newCol < this.cols 
                            && !visited[newRow][newCol] && !grid[newRow][newCol].isRevealed()) {
                            stack.push(new int[]{newRow, newCol});
                        }
                    }
                }
            }
            
            // Mark this cell as visited
            visited[currentRow][currentCol] = true;
        }
    }
    
    public void flagCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            Cell cell = grid[row][col];
            if (!cell.isRevealed()) {
                cell.toggleFlag();
            }
        }
    }
    
    // Getters for grid dimensions
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Cell[][] getGrid() {
        return grid;
    }

}
