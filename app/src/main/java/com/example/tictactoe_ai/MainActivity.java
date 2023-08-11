package com.example.tictactoe_ai;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    private int x=100;

    public float[] arr = new float[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
//            turnPlayer();

            check();
        }
    }
    private void check()
    {
        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else if(player1Turn){
            player1Turn = false;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    turnAI();
                }
            },1000);

        }
        else
        {
            player1Turn = true;
        }
    }

    private void turnPlayer()
    {

    }


    private void turnAI()
    {
        int [][] board = new int[3][3];


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(buttons[i][j].getText().toString().equals(""))
                    board[i][j]=0;
                else if(buttons[i][j].getText().toString().equals("X"))
                    board[i][j]=1;
                else if(buttons[i][j].getText().toString().equals("O"))
                    board[i][j]=2;



//                    board[i][j] = Integer.parseInt(buttons[i][j].getText().toString());
            }
        }

        TicTacToeAI ticTacToeAI = new TicTacToeAI();
        int[] bestMove = ticTacToeAI.getBestMove(board);
        int bestRow = bestMove[0];
        int bestCol = bestMove[1];
//            buttons[bestRow][bestCol] = COMPUTER;
        buttons[bestRow][bestCol].setText("O");

        check();

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                buttons[i][0].setBackgroundColor(Color.BLUE);
                buttons[i][1].setBackgroundColor(Color.BLUE);
                buttons[i][2].setBackgroundColor(Color.BLUE);
                return true;
            }
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {

                buttons[0][i].setBackgroundColor(Color.BLUE);
                buttons[1][i].setBackgroundColor(Color.BLUE);
                buttons[2][i].setBackgroundColor(Color.BLUE);

                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {

            buttons[0][0].setBackgroundColor(Color.BLUE);
            buttons[1][1].setBackgroundColor(Color.BLUE);
            buttons[2][2].setBackgroundColor(Color.BLUE);

            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            buttons[0][2].setBackgroundColor(Color.BLUE);
            buttons[1][1].setBackgroundColor(Color.BLUE);
            buttons[2][0].setBackgroundColor(Color.BLUE);

            new Handler().postDelayed(this::resetBoard,3000);
            return true;
        }
        return false;
    }

    private void storeLocationStart(Button button)
    {

        int[] location = new int[2];
        button.getLocationOnScreen(location);
        arr[0] = location[0]+x;
        arr[1] = location[1]-x;

    }
    private void storeLocationEnd(Button button)
    {

        int[] location = new int[2];
        button.getLocationOnScreen(location);
        arr[2] = location[0]+x;
        arr[3] = location[1]-x;

    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

//        GameBoardView gameBoardView = findViewById(R.id.game_board_view);
//        gameBoardView.setLine(arr[0]-10,arr[1],arr[2],arr[3]);
//        gameBoardView.invalidate();
    }

    // Reset the game
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    // Update the text for the player scores

    private void updatePointsText() {
        textViewPlayer1.setText(player1Points+ " ");
        textViewPlayer2.setText(player2Points+ " ");
    }

    // Reset the board
    private void resetBoard() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setText("");
                        buttons[i][j].setBackgroundColor(0xFFBB86FC);
                    }
                }
                roundCount = 0;
                player1Turn = true;

            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,3000);

    }

    // Draw the game
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    // Player 2 wins
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
}

