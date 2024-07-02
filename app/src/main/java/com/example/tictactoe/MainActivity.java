package com.example.tictactoe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tictactoe.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static ActivityMainBinding binding;
    private final List<int[]> match = new ArrayList<>();
    private static int[] position = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static int turn = 1;
    private static int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize match combinations
        match.add(new int[]{0, 1, 2});
        match.add(new int[]{3, 4, 5});
        match.add(new int[]{6, 7, 8});
        match.add(new int[]{0, 3, 6});
        match.add(new int[]{1, 4, 7});
        match.add(new int[]{2, 5, 8});
        match.add(new int[]{0, 4, 8});
        match.add(new int[]{2, 4, 6});

        String playerOne = getIntent().getStringExtra("PlayerOne");
        String playerTwo = getIntent().getStringExtra("PlayerTwo");

        binding.playerOneName.setText(playerOne);
        binding.playerTwoName.setText(playerTwo);

        setupClickListeners();

        // Set up restart button listener
        binding.button10.setOnClickListener(v -> restartMatch());
    }

    private void setupClickListeners() {
        binding.button1.setOnClickListener(v -> handleClick((ImageView) v, 0));
        binding.button2.setOnClickListener(v -> handleClick((ImageView) v, 1));
        binding.button3.setOnClickListener(v -> handleClick((ImageView) v, 2));
        binding.button4.setOnClickListener(v -> handleClick((ImageView) v, 3));
        binding.button5.setOnClickListener(v -> handleClick((ImageView) v, 4));
        binding.button6.setOnClickListener(v -> handleClick((ImageView) v, 5));
        binding.button7.setOnClickListener(v -> handleClick((ImageView) v, 6));
        binding.button8.setOnClickListener(v -> handleClick((ImageView) v, 7));
        binding.button9.setOnClickListener(v -> handleClick((ImageView) v, 8));
    }

    private void handleClick(ImageView imageView, int selectedBox) {
        if (isBoxAvailable(selectedBox)) {
            start(imageView, selectedBox);
            playSound(R.raw.music);
        }
    }

    private void start(ImageView imageView, int selectedBox) {
        position[selectedBox] = turn;
        if (turn == 1) {
            imageView.setImageResource(R.drawable.cross);
            if (checkResult()) {
                showResultDialog(binding.playerOneName.getText() + " is the Winner!");
                playSound(R.raw.win);
            } else if (count == 9) {
                showResultDialog("Match Draw");
                playSound(R.raw.over);
            } else {
                changeTurn(2);
            }
        } else {
            imageView.setImageResource(R.drawable.o);
            if (checkResult()) {
                showResultDialog(binding.playerTwoName.getText() + " is the Winner!");
                playSound(R.raw.win);
            } else if (count == 9) {
                showResultDialog("Match Draw");
                playSound(R.raw.over);
            } else {
                changeTurn(1);
            }
        }
        count++;
    }

    private void changeTurn(int currentPlayer) {
        turn = currentPlayer;
        if (turn == 1) {
            binding.linearLayout4.setBackgroundResource(R.drawable.border);
            binding.linearLayout5.setBackgroundResource(0);
        } else {
            binding.linearLayout5.setBackgroundResource(R.drawable.border);
            binding.linearLayout4.setBackgroundResource(0);
        }
    }

    private boolean checkResult() {
        for (int[] combination : match) {
            if (position[combination[0]] == turn && position[combination[1]] == turn && position[combination[2]] == turn) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoxAvailable(int selectedBox) {
        return position[selectedBox] == 0;
    }

    private void showResultDialog(String message) {
        ResultDialog resultDialog = new ResultDialog(MainActivity.this, message, MainActivity.this);
        resultDialog.setCancelable(false);
        resultDialog.show();
    }

    public static void restartMatch() {
        position = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        turn = 1;
        count = 1;

        binding.button1.setImageResource(R.drawable.white_box);
        binding.button2.setImageResource(R.drawable.white_box);
        binding.button3.setImageResource(R.drawable.white_box);
        binding.button4.setImageResource(R.drawable.white_box);
        binding.button5.setImageResource(R.drawable.white_box);
        binding.button6.setImageResource(R.drawable.white_box);
        binding.button7.setImageResource(R.drawable.white_box);
        binding.button8.setImageResource(R.drawable.white_box);
        binding.button9.setImageResource(R.drawable.white_box);

        binding.linearLayout4.setBackgroundResource(R.drawable.border);
        binding.linearLayout5.setBackgroundResource(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        restartMatch();
    }

    public void restart(View view) {

        binding.button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.music);
                restartMatch();
            }
        });
    }

    public void home(View view) {
        binding.button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayerList.class);
                restartMatch();
                playSound(R.raw.music);
                startActivity(intent);
                finish();
//                Intent intent1 = new Intent(getApplicationContext(), MyServices.class);
//               stopService(intent1);
            }
        });
    }

    public void playSound(int id) {

        MediaPlayer player = MediaPlayer.create(this, id);
        player.setVolume(100.0f, 100.0f);
        player.start();
    }
}
