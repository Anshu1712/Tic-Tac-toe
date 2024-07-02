package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class PlayerList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText playerOne = findViewById(R.id.one);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText playerTwo = findViewById(R.id.two);
        MaterialButton button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPlayerOne = playerOne.getText().toString();
                String getPlayerTwo = playerTwo.getText().toString();

                if (getPlayerOne.isEmpty() || getPlayerTwo.isEmpty()) {
                    Toast.makeText(PlayerList.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                    playSound(R.raw.music);
                } else {
                    Intent intent = new Intent(PlayerList.this, MainActivity.class);
                    intent.putExtra("PlayerOne", getPlayerOne);
                    intent.putExtra("PlayerTwo", getPlayerTwo);
                    playSound(R.raw.music);
                    startActivity(intent);
                    finish();

                    // Start the music service
//                    Intent intent1 = new Intent(getApplicationContext(), MyServices.class);
//                    startService(intent1);
                }
            }
        });
    }

    public void playSound(int id) {
        MediaPlayer player1 = MediaPlayer.create(this,id);
        player1.start();
        player1.setVolume(100.0f,100.0f);
    }
}
