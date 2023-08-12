package com.example.tictactoe_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Version");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_activity);

        databaseReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                if (task.getResult().exists()) {
                    Log.d("Multiplayer", "arrange: error");

                    DataSnapshot dataSnapshot = task.getResult();

                    Boolean count = (Boolean) dataSnapshot.child("1_1").getValue();

                    if (Boolean.TRUE.equals(count)){
                        new Handler().postDelayed(() -> startActivity(new Intent(SplashScreenActivity.this,MainActivity2.class)),1000);
                    }
                    else {
                        Toast.makeText(SplashScreenActivity.this, "New Version is available Update", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}