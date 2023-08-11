package com.example.tictactoe_ai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Script;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MultiPlayer extends AppCompatActivity implements View.OnClickListener{

    //for store
    private final List<String> doneBox = new ArrayList<>();
    public float[] arr = new float[4];
    ValueEventListener turnsEventListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Game");
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;

    public int round;

    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private String playerUniqueId;
    private boolean opponentFound = false;
    private String opponentUniqueId = "0";
    private String Status = "matching";
    private  Boolean playerTurn = true;
    private  Boolean winner;
    private String connectionId = "";
    private int x=100;

    final Handler handler = new Handler();

    private String count = " ";
    private int count1 = 0;

    String b;

    private String s1;

    private String UserType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        round = 0;
        SharedPreferences sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        s1 = sharedPreferences.getString("name","");
        UserType = sharedPreferences.getString("Type","");
        playerTurn = sharedPreferences.getBoolean("Turn",true);
        editor.clear();
        editor.commit();

        turnsEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    if (snapshot.hasChildren()) {

                        databaseReference.child(s1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {

                                    if (task.getResult().exists()) {

                                        DataSnapshot dataSnapshot = task.getResult();

                                        if(playerTurn){

                                            player1Turn = (Boolean) dataSnapshot.child("host").getValue();
                                        }
                                        else {

                                            player1Turn = (Boolean) dataSnapshot.child("join").getValue();
                                        }
                                        handler.postDelayed(() -> arrangeBlock(), 100);

                                    }
                                }
                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.child(s1).child("count").addValueEventListener(turnsEventListener);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        // Assign button id
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        // Button reset
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                arrangeBlock();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if(snapshot.exists()) {
//
//                    if (snapshot.hasChildren()) {
//
//                        arrangeBlock();
//
//                        databaseReference.child(s1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                if (task.isSuccessful()) {
//
//                                    if (task.getResult().exists()) {
//
//
//                                        DataSnapshot dataSnapshot = task.getResult();
//
//                                        if(playerTurn){
//
//                                            player1Turn = (Boolean) dataSnapshot.child("host").getValue();
//                                        }
//                                        else {
//
//                                            player1Turn = (Boolean) dataSnapshot.child("join").getValue();
//                                        }
//                                    }
//                                }
//                            }
//                        });
//
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    // end

//    databaseReference.child("connection").addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            if(opponentFound){
//
//                if (snapshot.hasChildren()){
//
//                    for (DataSnapshot connections: snapshot.getChildren()){
//
//                        String conId = (connections.getKey());
//
//                        int getPLayersCount = (int)connections.getChildrenCount();
//
//                        if(Status.equals("waiting")){
//
//                            if(getPLayersCount == 2){
//
////                                playerTurn = playerUniqueId;
//
//                                //something
//
//                                boolean playerFound = false;
//
//                                for(DataSnapshot players: connections.getChildren()){
//
//                                    String getPlayerUniqueId = players.getKey();
//
//                                    if(getPlayerUniqueId.equals(playerUniqueId)){
//
//                                        playerFound = true;
//                                    }
//                                    else if (playerFound) {
////                                        String getOpponent
//                                            opponentUniqueId = players.getKey();
//
//                                            connectionId = conId;
//                                            opponentFound = true;
//
//                                        databaseReference.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
//                                        databaseReference.child("won").child(connectionId).addValueEventListener(wonEventListener);
//
//                                            databaseReference.child("connection").removeEventListener(this);
//                                    }
//                                }
//                            }
//                            else {
//                                if (getPLayersCount == 1){
//
//                                    for (DataSnapshot players: connections.getChildren()){
//
//                                        opponentUniqueId = players.getKey();
//
////                                        playerTurn = opponentUniqueId;
//
//                                        //something for player turn
//
//                                        connectionId = conId;
//                                        opponentFound = true;
//
//
//                                        databaseReference.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
//                                        databaseReference.child("won").child(connectionId).addValueEventListener(wonEventListener);
//
//                                        databaseReference.child("connection").removeEventListener(this);
//
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                        if(!opponentFound && !Status.equals("waiting")){
//
//                            String connectionUniqueID = String.valueOf(System.currentTimeMillis());
//
//                            snapshot.child(connectionUniqueID).child(playerUniqueId).child("player_name").getRef().setValue("reet");
//
//                            Status = "waiting";
//                        }
//                    }
//                }
//                else {
//                    String connectionUniqueID = String.valueOf(System.currentTimeMillis());
//
//                    snapshot.child(connectionUniqueID).child(playerUniqueId).child("player_name").getRef().setValue("reet");
//
//                    Status = "waiting";
//                }
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });


    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {

            switch (v.getId()) {
                case R.id.button_00:
                    b = "button_00";
                    break;
                case R.id.button_01:
                    b = "button_01";
                    break;
                case R.id.button_02:
                    b = "button_02";
                    break;
                case R.id.button_10:
                    b = "button_10";
                    break;
                case R.id.button_11:
                    b = "button_11";
                    break;
                case R.id.button_12:
                    b = "button_12";
                    break;
                case R.id.button_20:
                    b = "button_20";
                    break;
                case R.id.button_21:
                    b = "button_21";
                    break;
                default:
                    b = "button_22";
                    break;
            }

            databaseReference.child(s1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {

                        if (task.getResult().exists()) {
                            Log.d("Multiplayer", "on click: error");

                            DataSnapshot dataSnapshot = task.getResult();

                            String count = String.valueOf(dataSnapshot.child("count").getValue());
                            Boolean host = (Boolean) dataSnapshot.child("host").getValue();
                            Boolean join = (Boolean) dataSnapshot.child("join").getValue();

                            count1 = Integer.parseInt(count);
                            count1 = count1 + 1;
                            String count2 = String.valueOf(count1);

                            Toast.makeText(MultiPlayer.this, host.toString(), Toast.LENGTH_SHORT).show();

                            databaseReference.child(s1).child("Board").child(count2).child("id").setValue(b);
                            databaseReference.child(s1).child("Board").child(count2).child("value").setValue(UserType);
                            databaseReference.child(s1).child("host").setValue(join);
                            databaseReference.child(s1).child("join").setValue(host);
                            databaseReference.child(s1).child("count").setValue(count2);

                            handler.postDelayed(() -> arrangeBlock(), 500);
//                            arrangeBlock();

                        }
                    }
                }
            });

        }
    }

    private void arrangeBlock() {

        databaseReference.child(s1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                    if (task.getResult().exists()) {
                        Log.d("Multiplayer", "arrange: error");

                        DataSnapshot dataSnapshot = task.getResult();

                        String count = String.valueOf(dataSnapshot.child("count").getValue());
                        count1 = Integer.parseInt(count);

                        for(int i = 1 ;i<=count1;i++) {
                            String id = Objects.requireNonNull(dataSnapshot.child("Board").child(String.valueOf(i)).child("id").getValue()).toString();
                            String value = Objects.requireNonNull(dataSnapshot.child("Board").child(String.valueOf(i)).child("value").getValue()).toString();

                            Button b1;
//                            buttons[0][0].setText("");

                            switch (id) {
                                case "button_00":
                                    b1 = findViewById(R.id.button_00);
                                    break;
                                case "button_01":
                                    b1 = findViewById(R.id.button_01);
                                    break;
                                case "button_02":
                                    b1 = findViewById(R.id.button_02);
                                    break;
                                case "button_10":
                                    b1 = findViewById(R.id.button_10);
                                    break;
                                case "button_11":
                                    b1 = findViewById(R.id.button_11);
                                    break;
                                case "button_12":
                                    b1 = findViewById(R.id.button_12);
                                    break;
                                case "button_20":
                                    b1 = findViewById(R.id.button_20);
                                    break;
                                case "button_21":
                                    b1 = findViewById(R.id.button_21);
                                    break;
                                default:
                                    b1 = findViewById(R.id.button_22);
                                    break;

                            }


                            if(value.equals("host")) {
                                b1.setText("X");
                            }
                            else {
                                b1.setText("O");
                            }

                        }
                        handler.postDelayed(() -> check(count1), 100);

                    }
                }
            }
        });

    }

    private void check(int count)
    {

        Log.d("Multiplayer", "check: error");
        if (checkForWin()) {

            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }

        } else if (count == 9) {
            draw();
        }

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

            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1Points++;

//        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

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

        databaseReference.child(s1).child("Board").setValue(null);
        databaseReference.child(s1).child("host").setValue(true);
        databaseReference.child(s1).child("join").setValue(false);
        databaseReference.child(s1).child("count").setValue("0");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setText("");
                        buttons[i][j].setBackgroundColor(0xFFBB86FC);
                    }
                }
            }
        }, 3000);

    }

    // Draw the game
    private void draw() {
//        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    // Player 2 wins
    private void player2Wins() {
        player2Points++;
//        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
}


