package com.example.tictactoe_ai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {

    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Version");

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button b1 = findViewById(R.id.SinglePlayer);
        Button b2= findViewById(R.id.MultiPlayer);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = 0;
                Toast.makeText(MainActivity2.this, "Make sure internet is connected", Toast.LENGTH_SHORT).show();
                databaseReference.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (task.getResult().exists()) {
                            Log.d("Multiplayer", "arrange: error");

                            DataSnapshot dataSnapshot = task.getResult();

                            Boolean count = (Boolean) dataSnapshot.child("1_5").getValue();

                            if (Boolean.TRUE.equals(count)){
                                flag =1;
                                startActivity(new Intent(MainActivity2.this,JoiningMultiPlayerActivity.class));
                            }
                            else {
                                Toast.makeText(MainActivity2.this, "New Version is available Update", Toast.LENGTH_LONG).show();
                                flag=1;
                            }
                        }
                    }
                });
            }
        });
    }
}