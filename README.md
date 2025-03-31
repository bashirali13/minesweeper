## How to Run the Project:

1. Clone the repository or download zip file.

2. Backend Setup:
   - Install Java Development Kit (JDK) version 11 or later.
   - Install Maven if not already installed.
   - Navigate to the 'backend' directory and run the Spring Boot application:
     mvn spring-boot:run
   - The backend will be running on `http://localhost:8080`.

3. Frontend Setup:
   - Install Node.js and npm if not already installed.
   - Navigate to the 'frontend' directory.
   - Run the following command to install dependencies:
     npm install
   - Start the frontend with:
     npm start
   - The frontend will be running on `http://localhost:3000`.

4. Once both backend and frontend are running, you can access the Minesweeper game in your browser at `http://localhost:3000`.


## How to Play Minesweeper:

1. The objective of Minesweeper is to uncover all the non-mine cells without triggering any mines.
2. The board consists of a grid of cells, some of which contain mines.
3. Click on a cell to reveal it:
    - If the cell contains a mine, the game ends, and you lose.
    - If the cell doesn't contain a mine, it will show the number of neighboring mines.
    - If the cell has no neighboring mines, it will reveal all adjacent cells.
4. Flagging: You can flag a cell if you suspect it has a mine. Right-click/two-finger click to flag a cell.
5. The game is won when all non-mine cells are revealed, and no mines have been triggered.

Enjoy!
