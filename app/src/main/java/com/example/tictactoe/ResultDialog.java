package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ResultDialog extends Dialog {

    private final String message;
    private final MainActivity mainActivity;

    public ResultDialog(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);
        this.message = message;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        TextView textView = findViewById(R.id.messageText);
        Button button = findViewById(R.id.startAgainButton);

        textView.setText(message);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.restartMatch();
                playSound(R.raw.music);
                dismiss();
            }
        });
    }

    public void playSound(int id) {

        MediaPlayer player1 = MediaPlayer.create(getContext(), id);
        player1.setVolume(100.0f, 100.0f);
        player1.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
