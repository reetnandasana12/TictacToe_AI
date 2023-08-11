package com.example.tictactoe_ai;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class WinningLineView  {
//    private Paint paint;
//
//    private static final int EMPTY = 0;
//    private static final int PLAYER = 1;
//    private static final int COMPUTER = 2;
//
//    private int startX, startY, endX, endY;
//
//    public WinningLineView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(20);
//    }
//
//    public void setStart(int x, int y) {
//        startX = x;
//        startY = y;
//    }
//
//    public void setEnd(int x, int y) {
//        endX = x;
//        endY = y;
//    }
//
//    private void showResult(int winner,Button[][] buttons) {
////        if (winner == PLAYER) {
////            Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show();
////        } else if (winner == COMPUTER) {
////            Toast.makeText(this, "Computer wins!", Toast.LENGTH_SHORT).show();
////        } else {
////            Toast.makeText(this, "Tie game!", Toast.LENGTH_SHORT).show();
////        }
//
//        // Get the winning positions
//        int[] winPos = getWinningPosition(buttons);
//        if (winPos != null) {
//            // Calculate the start and end points of the winning position
//            int startX = buttons[winPos[0]][winPos[1]].getLeft() + buttons[winPos[0]][winPos[1]].getWidth() / 2;
//            int startY = buttons[winPos[0]][winPos[1]].getTop() + buttons[winPos[0]][winPos[1]].getHeight() / 2;
//            int endX = buttons[winPos[2]][winPos[3]].getLeft() + buttons[winPos[2]][winPos[3]].getWidth() / 2;
//            int endY = buttons[winPos[2]][winPos[3]].getTop() + buttons[winPos[2]][winPos[3]].getHeight() / 2;
//
//            // Update the WinningLineView with the start and end points of the winning position
//            WinningLineView lineView = findViewById(R.id.winning_line_view);
//            lineView.setStart(startX, startY);
//            lineView.setEnd(endX, endY);
//
//            // Make the WinningLineView visible
//            lineView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private int[] getWinningPosition(Button[][] buttons) {
//        // Check rows
//        for (int i = 0; i < 3; i++) {
//            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
//                    buttons[i][0].getText().equals(buttons[i][2].getText()) &&
//                    !buttons[i][0].getText().equals("")) {
//                return new int[]{i, 0, i, 2};
//            }
//        }
//
//        // Check columns
//        for (int j = 0; j < 3; j++) {
//            if (buttons[0][j].getText().equals(buttons[1][j].getText()) &&
//                    buttons[0][j].getText().equals(buttons[2][j].getText()) &&
//                    !buttons[0][j].getText().equals("")) {
//                return new int[]{0, j, 2, j};
//            }
//        }
//
//        // Check diagonal
//        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
//                buttons[0][0].getText().equals(buttons[2][2].getText()) &&
//                !buttons[0][0].getText().equals("")) {
//            return new int[]{0, 0, 2, 2};
//        }
//
//        // Check opposite diagonal
//        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
//                buttons[0][2].getText().equals(buttons[2][0].getText()) &&
//                !buttons[0][2].getText().equals("")) {
//            return new int[]{0, 2, 2, 0};
//        }
//
//        // No winning position found
//        return null;
//    }
//
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawLine(startX, startY, endX, endY, paint);
//    }
}


