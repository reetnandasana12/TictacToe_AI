package com.example.tictactoe_ai;

public class TicTacToeAI extends Thread{
    private static final int EMPTY = 0;
    private static final int PLAYER = 1;
    private static final int COMPUTER = 2;

    public int[] getBestMove(int[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove[] = new int[2];

        // Loop through all possible moves
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;

            // Check if the current move is valid
            if (board[row][col] == EMPTY) {

                board[row][col] = PLAYER;
                int mark = checkWinner(board);
                if(mark == -1){
                    bestMove[0] = row;
                    bestMove[1] = col;
                    break;
                }
                // Make the move and evaluate the score
                board[row][col] = COMPUTER;
                int score = minimax(board, 0, false);
                board[row][col] = EMPTY;

                // Update the best move if necessary
                if (score > bestScore) {
                    bestScore = score;
                    bestMove[0] = row;
                    bestMove[1] = col;
                }
            }
        }

        return bestMove;
    }

    private int minimax(int[][] board, int depth, boolean isMaximizingPlayer) {
        // Check if the game is over
        int result = checkWinner(board);
        if (result != EMPTY) {
            return result;
        }


        // Check if we have reached the maximum depth
        if (depth == 5) {
            return 0;
        }

        // Recursive case: evaluate all possible moves
        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                int row = i / 3;
                int col = i % 3;
                if (board[row][col] == EMPTY) {
                    board[row][col] = COMPUTER;
                    int score = minimax(board, depth + 1, false);
                    board[row][col] = EMPTY;
                    bestScore = Math.max(bestScore, score);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                int row = i / 3;
                int col = i % 3;
                if (board[row][col] == EMPTY) {
                    board[row][col] = PLAYER;
                    int score = minimax(board, depth + 1, true);
                    board[row][col] = EMPTY;
                    bestScore = Math.min(bestScore, score);
                }
            }
            return bestScore;
        }
    }

    public int checkWinner(int[][] board) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == PLAYER) {
                    return -1;
                } else if (board[i][0] == COMPUTER) {
                    return 1;
                }
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                if (board[0][j] == PLAYER) {
                    return -1;
                } else if (board[0][j] == COMPUTER) {
                    return 1;
                }
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == PLAYER) {
                return -1;
            } else if (board[0][0] == COMPUTER) {
                return 1;
            }
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == PLAYER) {
                return -1;
            } else if (board[0][2] == COMPUTER) {
                return 1;
            }
        }

        // No winner
        return 0;
    }

}