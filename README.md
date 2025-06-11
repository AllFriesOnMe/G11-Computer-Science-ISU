# README

## Overview

The program implements a Connect4 game that takes place between two players, represented by ‘X’ and ‘Y’ on a 6 by 7 board, where the two players take turns dropping pieces onto the board. There are three game modes:

- Player versus player  
- Player versus computer, where the AI generates moves randomly  
- Player versus computer, where the AI makes it impossible for the player to win  

---

## What Happens When You Run the Program

1. The program prompts the user to enter option 1, 2, or 3 alongside a list of options  
2. For every game mode:
    - a. The board is printed as a 6 by 7 grid with empty cells represented by periods  
    - b. Players alternate inputting a column from 1 to 7 (inclusive) until someone connects 4 pieces in a row or if the board is full  
    - c. After each move, the board is updated and printed  
    - d. When the game ends, the program outputs the winner if a winner was detected, or states that the game is a draw, as well as the leaderboard  
3. For **PvP**:
    - a. Fully based on players’ inputs  
4. For **Player vs Computer (Random)**:
    - a. Player 1 is the user  
    - b. Player 2 is the computer  
    - c. The computer chooses a random column from 1 to 7 each turn  
5. For **Player vs Computer (Impossible)**:
    - a. Player 1 is the user  
    - b. Player 2 is the computer  
    - c. The computer tries to win or prevent a loss by checking for winning moves or potentially winning moves, and capitalizes or defends them. If no such moves are found, it places moves in the most center column available

---

## Key Variables & Functions

### `public static int[] columns = {0, 0, 0, 0, 0, 0, 0};`

- Tracks the number of pieces in each column  

### `printBoard(char[][] board)`

- Uses a nested for loop to print every row of the board, separating each period and printing `|` before and after the row  

### `checkValidMove(char[][] board, int move)`

- First checks if the move is between 1 and 7 → if not returns false (move is not valid)  
- Secondly checks if the chosen column is not full → if full returns false  
- Returns true if both checks have been passed  

### `place(char[][] board, int move, boolean playerOneTurn)`

- Places a piece in the chosen column  
- Uses the boolean `playerOneTurn` to determine whether to place an ‘X’ or ‘Y’  
- Places the piece in `board[r][c]` where `r` is the number of pieces already in the chosen column (`columns[move - 1]`) and `c` is the chosen column (`move - 1`)  
- Increments `columns[move - 1]` by 1  

### `checkDraw(char[][] board)`

- Checks if the board is full  
- Loops through all columns in `int[] columns` – if a column that is not full is found, the function returns false, otherwise it returns true (draw detected)  

### `checkWin(char[][] board, char player)`

- Uses multiple nested for loops to check for horizontal, vertical and diagonal wins  
- Uses `char player` to determine which player to check  

### `checkWinningMove(char[][] board, char targetPlayer)`

- Uses nested for loops to check for winning formations to block or capitalize on  
- Uses `targetPlayer` to determine which player’s piece to check → block or capitalize  

### `checkPotentialWinning(char[][] board, char targetPlayer)`

- Uses nested for loops to check for two in a row formations to block or capitalize on  
- Also uses `targetPlayer` to determine which player’s piece to check  

### `chooseNextMove(char[][] board)`

- Condensed function where the computer chooses the next move  
- First tries to capitalize on its own win, then tries to block a winning move – both use `checkWinningMove(board, targetPlayer)`  
- Secondly tries to block then tries to capitalize on 2 in a row formations – both use `checkPotentialWinning(board, targetPlayer)`  
- If all else fails it chooses the most center available column in the order `{3, 2, 4, 1, 5, 0, 6}`  
