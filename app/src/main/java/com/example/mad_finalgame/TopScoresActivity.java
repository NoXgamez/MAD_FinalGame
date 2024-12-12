package com.example.mad_finalgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TopScoresActivity extends AppCompatActivity {

    private ListView scoresListView;
    private PlayersDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);

        // Initialize the ListView and DataSource
        scoresListView = findViewById(R.id.scoresListView);
        dataSource = new PlayersDataSource(this);
        dataSource.open();

        // Get the top 5 scores
        List<Player> topScores = dataSource.getTop5Scores();

        // Create an adapter to display the scores
        ArrayAdapter<Player> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, topScores);

        // Set the adapter to the ListView
        scoresListView.setAdapter(adapter);
    }

    public void playAgain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
