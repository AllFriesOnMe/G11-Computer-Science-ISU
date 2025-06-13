// NAME: Anderson Wang

import java.util.Scanner;

public class Main {
    // initialize global variables + scanner
    public static Scanner sc = new Scanner(System.in);
    public static int[] leaderboard = {0, 0};
    public static char player1 = 'X';
    public static char player2 = 'O';
    public static char empty = '.';
    public static int[] columns = {0, 0, 0, 0, 0, 0, 0};
    public static char[][] board = {{empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}};

    public static char[][] place(char[][] board, int move, boolean playerOneTurn) {
        if(playerOneTurn) {
            board[5 - columns[move - 1]][move - 1] = player1;
        }
        else {
            board[5 - columns[move - 1]][move - 1] = player2;
        }

        columns[move - 1] += 1;

        return board;
    }

    public static char[][] pseudoPlace(char[][] newBoard, int move) {
        newBoard[5 - columns[move]][move] = player2;

        return newBoard;
    }

    public static boolean checkValidMove(char[][] board, int move) {
        if(move < 1 || move > 7) { // move must be within column 1, 2, 3, 4, 5, 6, or 7
            return false;
        }

        if(columns[move - 1] == 6) { // column is full
            return false;
        }

        return true;
    }

    public static boolean checkDraw(char[][]board) {
        for(int c = 0; c < 6; c++) {
            if(columns[c] < 6) { // found a column that isn't full
                return false;
            }
        }

        return true; // loop didn't find a column that isn't full -> return false
    }

    public static boolean checkWin(char[][] board, char player) {
        // check horizontal
        for(int r = 0; r < 6; r++) {
            for(int c = 0; c < 4; c++) {
                // checking if 4 horizontally adjacent spaces are occupied by the player
                if(board[r][c] == player && board[r][c + 1] == player && board[r][c + 2] == player && board[r][c + 3] == player) {
                    return true;
                }
            }
        }

        // check vertical
        for(int r = 0; r <= 2; r++) {
            for(int c = 0; c < 7; c++) {
                // check if 4 vertically adjacent spaces are occupied by the player
                if(board[r][c] == player && board[r + 1][c] == player && board[r + 2][c] == player && board[r + 3][c] == player) {
                    return true;
                }
            }
        }

        // check diagonal
        for(int r = 0; r <= 2; r++) {
            for(int c = 0; c <= 3; c++) {
                // top left -> bottom right diagonal check
                if(board[r][c] == player && board[r + 1][c + 1] == player && board[r + 2][c + 2] == player && board[r + 3][c + 3] == player) {
                    return true;
                }
            }

            for(int c = 6; c >= 3; c--) {
                // top right -> bottom left check
                if(board[r][c] == player && board[r + 1][c - 1] == player && board[r + 2][c - 2] == player && board[r + 3][c - 3] == player) {
                    return true;
                }
            }
        }

        return false; // no winning move has been found
    }

    public static void printBoard(char[][] board) {
        // print column numbers
        System.out.println("  1 2 3 4 5 6 7");
        for(int i = 0; i < 6; i++) {
            System.out.print("| "); // print left edge of board

            // print every row
            for(int j = 0; j < 7; j++) {
                System.out.print(board[i][j] + " ");
            }

            System.out.print("|\n"); //print right edge of board
        }
        System.out.println(); // print a newline after for visual purposes
    }

    public static boolean avoidLosingPosition(char[][] currBoard, int next_move) {
        char[][] predictedBoard = pseudoPlace(currBoard, next_move);
        if(checkWinningMove(predictedBoard, player1) != -1) {
            return false; // going to lose
        }
        return true; // not going to lose
    }

    // all IMMEDIATELY winning moves

    public static int checkWinningMove(char[][] board, char targetPlayer) {
        int next_move;

        // check horizontal 3 in a row
        for(int r = 0; r < 6; r++) {
            for(int c = 0; c < 5; c++) {
                if(board[r][c] == targetPlayer && board[r][c + 1] == targetPlayer && board[r][c + 2] == targetPlayer) {
                    // RS check
                    if(c < 4 && board[r][c + 3] == empty && (r == 5 || board[r + 1][c + 3] != empty)) {
                        next_move = c + 3;
                        return next_move;
                    }

                    // LS check
                    if(c - 1 >= 0 && board[r][c - 1] == empty && (r == 5 || board[r + 1][c - 1] != empty)) {
                        next_move = c - 1;
                        return next_move;
                    }
                }
            }
        }

        // check vertical 3 in a row
        for(int r = 1; r <= 3; r++) {
            for(int c = 0; c < 7; c++) {
                if(board[r][c] == targetPlayer && board[r + 1][c] == targetPlayer && board[r + 2][c] == targetPlayer && board[r - 1][c] == empty) {
                    next_move = c;
                    return next_move;
                }
            }
        }

        // check diagonal 3 in a row
        for(int r = 0; r <= 3; r++) {
            for(int c = 0; c <= 4; c++) {
                // top left -> bottom right
                if(board[r][c] == targetPlayer && board[r + 1][c + 1] == targetPlayer && board[r + 2][c + 2] == targetPlayer) {
                    // RS check - considers if move is on bottom row or not
                    if(r <= 2 && c < 4 && board[r + 3][c + 3] == empty && (r == 2 || (c + 4 < 7 && board[r + 4][c + 4] != empty))) {
                        next_move = c + 3;
                        return next_move;
                    }

                    // LS check
                    if(c > 0 && r > 0 && board[r - 1][c - 1] == empty && board[r][c - 1] != empty) {
                        next_move = c - 1;
                        return next_move;
                    }
                }
            }

            // check diagonal (top right -> bottom left)
            for(int c = 6; c >= 2; c--) {
                if(board[r][c] == targetPlayer && board[r + 1][c - 1] == targetPlayer && board[r + 2][c - 2] == targetPlayer) {
                    // RS check
                    if(r > 0 && c <= 5 && board[r - 1][c + 1] == empty && board[r][c + 1] != empty) {
                        next_move = c + 1;
                        return next_move;
                    }

                    // LS check - considers if the move is on the bottom row or not
                    if(r < 3 && c >= 3 && board[r + 3][c - 3] == empty && (r == 2 || (board[r + 4][c - 3] != empty))) {
                        next_move = c - 3;
                        return next_move;
                    }
                }
            }
        }

        // check diagonal 3 in a row (top left -> bottom right)
        for(int r = 0; r <= 3; r++) {
            for(int c = 0; c <= 4; c++) {
                if(board[r][c] == targetPlayer && board[r + 1][c + 1] == targetPlayer && board[r + 2][c + 2] == targetPlayer) {
                    // RS check - considers if move is on bottom row or not
                    if(r <= 2 && c < 4 && board[r + 3][c + 3] == empty && (r == 2 || (c + 4 < 7 && board[r + 4][c + 4] != empty))) {
                        next_move = c + 3;
                        return next_move;
                    }

                    // LS check
                    if(c > 0 && r > 0 && board[r - 1][c - 1] == empty && board[r][c - 1] != empty) {
                        next_move = c - 1;
                        return next_move;
                    }
                }
            }

            // check diagonal (top right -> bottom left)
            for(int c = 6; c >= 2; c--) {
                if(board[r][c] == targetPlayer && board[r + 1][c - 1] == targetPlayer && board[r + 2][c - 2] == targetPlayer) {
                    // RS check
                    if(r > 0 && c <= 5 && board[r - 1][c + 1] == empty && board[r][c + 1] != empty) {
                        next_move = c + 1;
                        return next_move;
                    }

                    // LS check - considers if the move is on the bottom row or not
                    if(r < 3 && c >= 3 && board[r + 3][c - 3] == empty && (r == 2 || board[r + 4][c - 3] != empty)) {
                        next_move = c - 3;
                        return next_move;
                    }
                }
            }
        }

        return -1; // no winning move has been found

    }

    // moves that don't immediately lead to a win but trap the other player

    public static int checkWinningSetups(char[][] board, char targetPlayer) {
        int next_move;
        // check horizontal 2 in a row with no pieces on either side
        for(int r = 0; r < 6; r++) {
            for(int c = 1; c < 5; c++) {
                if(board[r][c] == targetPlayer && board[r][c + 1] == targetPlayer && board[r][c - 1] == empty && board[r][c + 2] == empty) {
                    // if on bottom row - no need to check if a piece is below either empty piece
                    if(r == 5) {
                        next_move = c - 1;
                        return next_move;
                    }
                    // if not on bottom row - check if a piece is below either empty piece
                    else if(board[r + 1][c - 1] != empty) {
                        next_move = c - 1;
                        return next_move;
                    }
                    else if(board[r + 1][c + 2] != empty) {
                        next_move = c + 2;
                        return next_move;
                    }
                }
            }
        }

        // check for alternating . X . X or X . X .
        for(int r = 0; r < 6; r++) {
            for(int c = 0; c < 4; c++) {
                // . X . X
                if(board[r][c] == empty && board[r][c + 2] == empty && board[r][c + 1] == targetPlayer && board[r][c + 3] == targetPlayer && (r == 5 || board[r + 1][c + 2] != empty)) {
                    next_move = c + 2;
                    return next_move;
                }
                // X . X .
                if(board[r][c] == targetPlayer && board[r][c + 2] == targetPlayer && board[r][c + 1] == empty && board[r][c + 3] == empty && (r == 5 || board[r + 1][c + 1] != empty)) {
                    next_move = c + 1;
                    return next_move;
                }
            }
        }

        return -1;
    }

    public static int checkPotentialWinning(char[][] board, char targetPlayer) {
        int next_move;
        char[][] newBoard = board;

        // check diagonal 2 in a row
        for(int r = 0; r <= 4; r++) {
            for(int c = 0; c <= 5; c++) {
                if(board[r][c] == targetPlayer && board[r + 1][c + 1] == targetPlayer) {
                    // RS check - considers if move is on bottom row or not
                    if(r <= 3 && c < 5 && board[r + 2][c + 2] == empty && (r == 3 || (c <= 3 && board[r + 3][c + 2] != empty))) {
                        next_move = c + 2;
                        if(avoidLosingPosition(newBoard, next_move)) {
                            return next_move;
                        }
                    }

                    // LS check
                    if(c > 0 && r > 0 && board[r - 1][c - 1] == empty && board[r][c - 1] != empty) {
                        next_move = c - 1;
                        if(avoidLosingPosition(newBoard, next_move)) {
                            return next_move;
                        }
                    }
                }
            }

            // check diagonal (top right -> bottom left)
            for(int c = 6; c >= 1; c--) {
                if(board[r][c] == targetPlayer && board[r + 1][c - 1] == targetPlayer) {
                    // RS check
                    if(r > 0 && board[r - 1][c + 1] == empty && board[r][c + 1] != empty) {
                        next_move = c + 1;
                        if(avoidLosingPosition(newBoard, next_move)) {
                            return next_move;
                        }
                    }

                    // LS check - considers if the move is on the bottom row or not
                    if(r < 4 && c >= 2 && board[r + 2][c - 2] == empty && (r == 3 || (c >= 3 && board[r + 3][c - 2] != empty))) {
                        next_move = c - 2;
                        if(avoidLosingPosition(newBoard, next_move)) {
                            return next_move;
                        }
                    }
                }
            }
        }

        // check vertical 2 in a row
        for(int r = 1; r <= 4; r++) {
            for(int c = 0; c < 7; c++) {
                if(board[r][c] == targetPlayer && board[r + 1][c] == targetPlayer && board[r - 1][c] == empty) {
                    next_move = c;
                    if(avoidLosingPosition(newBoard, next_move)) {
                        return next_move;
                    }
                }
            }
        }

        // check horizontal 2 in a row
        for(int r = 0; r < 6; r++) {
            // guarded on the left but not on the right
            for(int c = 0; c < 4; c++) {
                if(board[r][c] == targetPlayer && board[r][c + 1] == targetPlayer && board[r][c + 2] == empty && board[r][c + 3] == empty) {
                    if(r == 5 || board[r + 1][c + 1] != empty) {
                        next_move = c + 1;
                        if(avoidLosingPosition(newBoard, next_move)) {
                            return next_move;
                        }
                    }
                    if(board[r + 1][c + 2] != empty) {
                        next_move = c + 2;
                        if(avoidLosingPosition(newBoard, next_move)) {
                            return next_move;
                        }
                    }

                }
            }

            // guarded on the right but not on the left
            for(int c = 2; c < 6; c++) {
                if(board[r][c] == targetPlayer && board[r][c + 1] == targetPlayer && board[r][c - 1] == empty && board[r][c - 2] == empty) {
                    if(r == 5 || board[r + 1][c - 1] != empty) {
                        next_move = c - 1;
                        if(avoidLosingPosition(newBoard, next_move)) {
                            return next_move;
                        }
                    }
                    if(board[r + 1][c - 2] != empty) {
                        next_move = c - 2;
                        if(avoidLosingPosition(newBoard, next_move)) {
                            return next_move;
                        }
                    }
                }
            }
        }

        return -1;
    }

    public static int chooseNextMove(char[][] board) {
        int nextMove = checkWinningMove(board, player2); // try to capitalize on winning move

        if(nextMove != -1) {
            return nextMove;
        }

        nextMove = checkWinningMove(board, player1); // try to block winning move from player 1

        if(nextMove != -1) {
            return nextMove;
        }

        nextMove = checkWinningSetups(board, player2);

        if(nextMove != -1) {
            return nextMove;
        }

        nextMove = checkWinningSetups(board, player1);

        if(nextMove != -1) {
            return nextMove;
        }

        nextMove = checkPotentialWinning(board, player1); // check 2 in a row - defensive

        if(nextMove != -1) {
            return nextMove;
        }

        nextMove = checkPotentialWinning(board, player2); // check 2 in a row - offensive

        if(nextMove != -1) {
            return nextMove;
        }

        // no winning/promising move found - place piece at the most center column

        boolean leftValid;
        boolean rightValid;
        for(int c = 0; c <= 3; c++) {
            // leftCenter and rightCenter diverge from center column
            int leftCenter = 3 - c;
            int rightCenter = c + 3;

            leftValid = checkValidMove(board, leftCenter + 1); // check if column is full
            if(leftValid) {
                nextMove = leftCenter;
                break;
            }

            rightValid = checkValidMove(board, rightCenter + 1);
            if(rightValid) {
                nextMove = rightCenter;
                break;
            }
        }

        return nextMove;
    }

    public static void PvP() {
        printBoard(board);

        while(true) {
            int playerOneMove;
            int playerTwoMove;
            boolean playerOneTurn;
            boolean win;
            boolean isDraw;

            // get user input + check validity

            System.out.println("Player 1 " + "(" + player1 + ")" + " move: ");
            playerOneTurn = true;
            playerOneMove = sc.nextInt();

            boolean validMove = checkValidMove(board, playerOneMove);

            // prompt until valid move is entered

            while(!validMove) {
                System.out.println("Invalid move! Enter another column: ");
                playerOneMove = sc.nextInt();
                validMove = checkValidMove(board, playerOneMove);
            }

            board = place(board, playerOneMove, playerOneTurn);

            printBoard(board);

            // check if the game is a draw or if player 1 won after the move -> add points (if applicable) then print leaderboard

            isDraw = checkDraw(board);

            if(isDraw) {
                System.out.println("The game is a tie!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break; // end the game
            }

            win = checkWin(board, player1);
            if(win) {
                leaderboard[0] += 1;
                System.out.println("Player 1 wins!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

            // repeat process for player 2

            System.out.println("Player 2 " + "(" + player2 + ")" + " move: ");
            playerOneTurn = false;
            playerTwoMove = sc.nextInt();

            validMove = checkValidMove(board, playerTwoMove);

            while(!validMove) {
                System.out.println("Invalid move! Enter another column: ");
                playerTwoMove = sc.nextInt();
                validMove = checkValidMove(board, playerTwoMove);
            }

            board = place(board, playerTwoMove, playerOneTurn);

            printBoard(board);

            isDraw = checkDraw(board);

            if(isDraw) {
                System.out.println("The game is a tie!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

            win = checkWin(board, player2);
            if(win) {
                leaderboard[1] += 1;
                System.out.println("Player 2 wins!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

        }

    }

    public static void randomPvC() {
        printBoard(board);

        while(true) {
            int playerOneMove;
            int playerTwoMove;
            boolean playerOneTurn; // variable lets the place function know to which player's piece to place
            boolean win;
            boolean isDraw;

            // same user input + checking process as before

            System.out.println("Player 1 " + "(" + player1 + ")" + " move: ");
            playerOneTurn = true;
            playerOneMove = sc.nextInt();

            boolean validMove = checkValidMove(board, playerOneMove);
            while(!validMove) {
                System.out.println("Invalid move! Enter another column: ");
                playerTwoMove = sc.nextInt();
                validMove = checkValidMove(board, playerTwoMove);
            }

            board = place(board, playerOneMove, playerOneTurn);

            printBoard(board);

            isDraw = checkDraw(board);

            if(isDraw) {
                System.out.println("The game is a tie!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

            win = checkWin(board, player1);

            if(win) {
                System.out.println("Player 1 wins!");
                leaderboard[0] += 1;
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

            playerOneTurn = false;

            // generate random column from 1 - 7

            playerTwoMove = (int) (Math.random() * (7 - 1 + 1) + 1);
            boolean playerTwoValid = checkValidMove(board, playerTwoMove);

            while(!playerTwoValid) {
                playerTwoMove = (int) (Math.random() * (7 - 1 + 1) + 1);
                playerTwoValid = checkValidMove(board, playerTwoMove);
            }

            System.out.println("The computer placed their piece in column " + playerTwoMove + ".");

            board = place(board, playerTwoMove, playerOneTurn);

            printBoard(board);

            isDraw = checkDraw(board);

            if(isDraw) {
                System.out.println("The game is a tie!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

            win = checkWin(board, player2);

            if(win) {
                System.out.println("Player 2 wins!");
                leaderboard[1] += 1;
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

        }

    }

    public static void impossiblePvC() {
        printBoard(board);

        while(true) {
            int playerOneMove;
            int playerTwoMove;
            boolean playerOneTurn;
            boolean win;
            boolean isDraw;

            System.out.println("Player 1 " + "(" + player1 + ")" + " move: ");
            playerOneTurn = true;
            playerOneMove = sc.nextInt();

            boolean validMove = checkValidMove(board, playerOneMove);
            while(!validMove) {
                System.out.println("Invalid move! Enter another column: ");
                playerOneMove = sc.nextInt();
                validMove = checkValidMove(board, playerOneMove);
            }

            board = place(board, playerOneMove, playerOneTurn);

            printBoard(board);

            isDraw = checkDraw(board);

            if(isDraw) {
                System.out.println("The game is a tie!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

            // computer chooses next move

            int nextMove = chooseNextMove(board);

            playerOneTurn = false;
            place(board, nextMove + 1, playerOneTurn);

            System.out.println("The computer placed their piece in column " + (nextMove + 1) + ".");

            printBoard(board);

            isDraw = checkDraw(board);
            if(isDraw) {
                System.out.println("The game is a tie!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

            win = checkWin(board, player2);
            if(win) {
                leaderboard[1] += 1;
                System.out.println("Player 2 won!");
                System.out.println("Player 1 score: " + leaderboard[0]);
                System.out.println("Player 2 score: " + leaderboard[1]);
                break;
            }

        }

    }

    public static void main(String[] args) {
        while(true) {
            System.out.println("Pick from option 1, 2, or 3: \n1. Player vs Player\n2. Player vs Computer (Random)\n3. Player vs Computer (Never Loses)");
            int choice;

            // get user's choice - keep prompting until the user inputs a valid option
            while(true) {
                choice = sc.nextInt();

                if(1 <= choice && choice <= 3) {
                    break;
                }
                System.out.println("Invalid input! Enter 1, 2, or 3: ");
            }

            switch(choice) {
                case 1:
                    PvP();
                    break;
                case 2:
                    randomPvC();
                    break;
                case 3:
                    impossiblePvC();
                    break;
            }

            System.out.println("Would you like to play again? (Y/N)");
            sc.nextLine();
            String replayChoice = sc.nextLine();

            while(!replayChoice.equals("Y") && !replayChoice.equals("N")) {
                System.out.println("Invalid input. Enter Y or N");
                replayChoice = sc.nextLine();
            }

            if(replayChoice.equals("N")) {
                System.out.println("Thanks for playing!");
                break;
            }

            // print 10 lines to clear screen

            for(int i = 0; i < 10; i++) {
                System.out.println();
            }

            // reset column count
            for(int i = 0; i < 7; i++) {
                columns[i] = 0;
            }

            // reset board
            char[][] board = {{empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}, {empty, empty, empty, empty, empty, empty, empty}};

        }
    }
}
