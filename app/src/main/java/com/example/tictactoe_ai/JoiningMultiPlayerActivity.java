package com.example.tictactoe_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoiningMultiPlayerActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Players");

    public int Flag = -1;
    private int x=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining_multi_player);

        Button b1 = findViewById(R.id.Host);
        Button b2 = findViewById(R.id.Join);
        Button b3 = findViewById(R.id.Submit_button);
        EditText e1 = findViewById(R.id.editTextText);

        SharedPreferences sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();


        e1.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);

        b1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                e1.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);
                b2.setVisibility(View.INVISIBLE);

                Flag = 0;
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                b1.setVisibility(View.INVISIBLE);
                e1.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);

                Flag = 1;
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {

            final DatabaseReference d1 = database.getReference("Game");
            @Override
            public void onClick(View v) {


                if(Flag == 0) {

                    //host

                    // Edit text value
                    String s1 = String.valueOf(e1.getText());

                    //store into sharedPreference
                    editor.putString("name",s1);
                    editor.putBoolean("Turn",true);
                    editor.putString("Type","host");
                    editor.commit();

                    //value store
                    databaseReference.child(s1).setValue(s1);


                    d1.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.exists()) {

                                if (snapshot.hasChildren()) {

                                    d1.child(s1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                if (task.getResult().exists()) {

                                                    if (Flag==0) {
                                                        changeActivity(s1);
                                                    }
                                                    Flag=3;
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
                    });
                }

                else {

                    //Join
                    //setPreference(e1,editor);

                    String s1 = String.valueOf(e1.getText());

                    editor.putString("name",s1);
                    editor.putBoolean("Turn",false);
                    editor.putString("Type","join");
                    editor.commit();

                    databaseReference.child(s1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            if (task.isSuccessful()) {

                                if (task.getResult().exists()) {

                                    changeActivity(s1);
                                }
                            }
                        }
                    });
                }

            }
        });






//        databaseReference.child("connection").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(opponentFound){
//
//                    if (snapshot.hasChildren()){
//
//                        for (DataSnapshot connections: snapshot.getChildren()){
//
//                            String conId = (connections.getKey());
//
//                            int getPLayersCount = (int)connections.getChildrenCount();
//
//                            if(Status.equals("waiting")){
//
//                                if(getPLayersCount == 2){
//
//                                    playerTurn = playerUniqueId;
//
//                                    //something
//
//                                    boolean playerFound = false;
//
//                                    for(DataSnapshot players: connections.getChildren()){
//
//                                        String getPlayerUniqueId = players.getKey();
//
//                                        if(getPlayerUniqueId.equals(playerUniqueId)){
//
//                                            playerFound = true;
//                                        }
//                                        else if (playerFound) {
////                                        String getOpponent
//                                            opponentUniqueId = players.getKey();
//
//                                            connectionId = conId;
//                                            opponentFound = true;
//
//                                            databaseReference.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
//                                            databaseReference.child("won").child(connectionId).addValueEventListener(wonEventListener);
//
//                                            databaseReference.child("connection").removeEventListener(this);
//                                        }
//                                    }
//                                }
//                                else {
//                                    if (getPLayersCount == 1){
//
//                                        for (DataSnapshot players: connections.getChildren()){
//
//                                            opponentUniqueId = players.getKey();
//
//                                            playerTurn = opponentUniqueId;
//
//                                            //something for player turn
//
//                                            connectionId = conId;
//                                            opponentFound = true;
//
//
//                                            databaseReference.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
//                                            databaseReference.child("won").child(connectionId).addValueEventListener(wonEventListener);
//
//                                            databaseReference.child("connection").removeEventListener(this);
//
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            if(!opponentFound && !Status.equals("waiting")){
//
//                                String connectionUniqueID = String.valueOf(System.currentTimeMillis());
//
//                                snapshot.child(connectionUniqueID).child(playerUniqueId).child("player_name").getRef().setValue("reet");
//
//                                Status = "waiting";
//                            }
//                        }
//                    }
//                    else {
//                        String connectionUniqueID = String.valueOf(System.currentTimeMillis());
//
//                        snapshot.child(connectionUniqueID).child(playerUniqueId).child("player_name").getRef().setValue("reet");
//
//                        Status = "waiting";
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
    void clearData () {
//        EditText e1 = findViewById(R.id.editTextText);
//
//        String s1 = String.valueOf(e1.getText());
//
//        SharedPreferences sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//
//
//        //value store
//        databaseReference.child(s1).setValue(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(Flag!=0){
            clearData();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(Flag!=0){
            clearData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(Flag!=0){
            clearData();
        }
    }

    void changeActivity(String s1){

        final DatabaseReference d1 = database.getReference("Game");

        Toast.makeText(JoiningMultiPlayerActivity.this, "yes", Toast.LENGTH_SHORT).show();

        d1.child(s1).child("count").setValue("0");
        d1.child(s1).child("winner").setValue(false);
        d1.child(s1).child("host").setValue(true);
        d1.child(s1).child("join").setValue(false);
        databaseReference.child("Player").child(s1).setValue(null);


        Intent intent = new Intent(JoiningMultiPlayerActivity.this,MultiPlayer.class);
        startActivity(intent);

    }

    void setPreference(EditText e1 ,SharedPreferences.Editor editor) {

        String s1 = String.valueOf(e1.getText());

        editor.putString("name",s1);
//        editor.putBoolean()
        editor.commit();
    }


}
