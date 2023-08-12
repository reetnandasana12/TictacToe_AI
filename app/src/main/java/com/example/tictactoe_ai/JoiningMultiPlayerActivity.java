package com.example.tictactoe_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class JoiningMultiPlayerActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Multiplayer").child("Players");
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String s1;
    Boolean flag1;
    public int Flag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining_multi_player);

        Button b1 = findViewById(R.id.Host);
        Button b2 = findViewById(R.id.Join);
        Button b3 = findViewById(R.id.Submit_button);
        EditText e1 = findViewById(R.id.editTextText);
        TextView t1 = findViewById(R.id.textView);

        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //set invisible
        e1.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        t1.setVisibility(View.INVISIBLE);

        //host click
        b1.setOnClickListener(v -> {

            e1.setVisibility(View.VISIBLE);
            b3.setVisibility(View.VISIBLE);
            b2.setVisibility(View.INVISIBLE);

            Flag = 0;
        });

        //join click
        b2.setOnClickListener(v -> {

            b1.setVisibility(View.INVISIBLE);
            e1.setVisibility(View.VISIBLE);
            b3.setVisibility(View.VISIBLE);

            Flag = 1;
        });

        //submit click
        b3.setOnClickListener(new View.OnClickListener() {
            final DatabaseReference d1 = database.getReference("Multiplayer").child("Game");

            @Override
            public void onClick(View v) {

                s1 = String.valueOf(e1.getText());
                flag1 = true;

                databaseReference.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (task.getResult().exists()) {

                            DataSnapshot dataSnapshot = task.getResult();
                            Boolean check = (Boolean) dataSnapshot.child(s1).getValue();

                            //true means games is running
                            if (Boolean.TRUE.equals(check)) {
                                t1.setVisibility(View.VISIBLE);
                                flag1 = false;
                                Toast.makeText(JoiningMultiPlayerActivity.this, "true check", Toast.LENGTH_SHORT).show();
                            }
                            //false means game is not started
                            else {
                                t1.setVisibility(View.INVISIBLE);
                                Toast.makeText(JoiningMultiPlayerActivity.this, "false check", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                if (Flag == 0 && flag1) {
                    //host
                    databaseReference.child(s1).setValue(false);
                    d1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                if (snapshot.hasChildren()) {

                                    d1.child(s1).get().addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {

                                            if (task.getResult().exists()) {

                                                if (Flag == 0) {

                                                    editor.putString("name", s1);
                                                    editor.putBoolean("Turn", true);
                                                    editor.putString("Type", "host");
                                                    editor.commit();

                                                    changeActivity(s1);
                                                }
                                                Flag = -1;
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

                } else if (flag1) {
                    //Join
                    String s1 = String.valueOf(e1.getText());

                    databaseReference.child(s1).get().addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            if (task.getResult().exists()) {

                                editor.putString("name", s1);
                                editor.putBoolean("Turn", false);
                                editor.putString("Type", "join");
                                editor.commit();

                                changeActivity(s1);
                            }
                        }
                    });
                }
            }
        });
    }

    void changeActivity(String s1) {

        final DatabaseReference d1 = database.getReference("Multiplayer").child("Game");

        Toast.makeText(JoiningMultiPlayerActivity.this, "yes", Toast.LENGTH_SHORT).show();

        d1.child(s1).child("count").child("count").setValue("0");
        d1.child(s1).child("winner").setValue(false);
        d1.child(s1).child("host").setValue(true);
        d1.child(s1).child("join").setValue(false);

        Intent intent = new Intent(JoiningMultiPlayerActivity.this, MultiPlayer.class);
        startActivity(intent);

    }
}
