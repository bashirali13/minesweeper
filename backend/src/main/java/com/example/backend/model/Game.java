package com.example.backend.model;

public class Game {
    private Board board;
    private boolean gameOver;
    private boolean gameWon;

    // Constructor
    public Game(int rows, int cols, int mineCount) {
        this.board = new Board(rows, cols, mineCount);
        this.gameOver = false;
        this.gameWon = false;
    }

    public boolean revealCell(int row, int col){
        if (gameOver) return false;

        boolean isMine = board.revealCell(row, col);
        if(isMine){
            gameOver = true;
        }

        gameWon = checkWin();
        return isMine;
    }

    public void flagCell(int row, int col) {
        board.flagCell(row, col);
    }
    
    private boolean checkWin(){
        for(int i = 0; i < board.getRows(); i++){
            for(int j = 0; j < board.getCols(); j++){
                Cell cell = board.getGrid()[i][j];
                if (!cell.isRevealed() && !cell.hasMine()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public Board getBoard() {
        return this.board;
    }
    
}
