package com.example.mad_finalgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterPlayerActivity extends AppCompatActivity {

    private EditText nameEditText;
    int score;

    private PlayersDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_player);

        nameEditText = findViewById(R.id.nameEditText);

        dataSource = new PlayersDataSource(this);
        dataSource.open();

        // Retrieve the score from the Intent
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0); // Get score passed from previous activity
    }

    public void playAgain(View view) {
        String name = nameEditText.getText().toString();

        // Save player with name and score
        Player player = dataSource.createPlayer(name, score);

        // Display success message
        Toast.makeText(this, "Player saved: " + player.toString(), Toast.LENGTH_SHORT).show();

        // Clear input fields
        nameEditText.setText("");

        startActivity(new Intent(this, MainActivity.class));
    }

    public void viewBoard(View view) {
        String name = nameEditText.getText().toString();

        // Save player with name and score
        Player player = dataSource.createPlayer(name, score);

        // Display success message
        Toast.makeText(this, "Player saved: " + player.toString(), Toast.LENGTH_SHORT).show();

        // Clear input fields
        nameEditText.setText("");

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}