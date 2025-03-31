package com.example.backend.model;

public class Cell {
    private boolean hasMine;
    private int neighboringMines;
    private boolean revealed;
    private boolean flagged;

    //Constructor
    public Cell(){
        this.hasMine = false;
        this.neighboringMines = 0;
        this.revealed = false;
        this.flagged = false;
    }

    //Getters and setters
    public boolean hasMine() {
        return hasMine;
    }

    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public int getNeighboringMines() {
        return neighboringMines;
    }

    public void setNeighboringMines(int neighboringMines) {
        this.neighboringMines = neighboringMines;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void toggleFlag() {
        if (!this.revealed) {
            this.flagged = !this.flagged;
        }
    }
    
    @Override
public String toString() {
    return String.format("{ hasMine: %b, neighboringMines: %d, revealed: %b, flagged: %b }", 
                        hasMine, neighboringMines, revealed, flagged);
}

}
