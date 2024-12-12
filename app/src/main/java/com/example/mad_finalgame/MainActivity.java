package com.example.mad_finalgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageButton yellowButton, greenButton, redButton, blueButton;
    private Button startButton;
    private TextView scoreTextView;
    private List<ImageButton> buttons;
    private List<ImageButton> sequence;

    private int currentStep = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        yellowButton = findViewById(R.id.yellow);
        greenButton = findViewById(R.id.green);
        redButton = findViewById(R.id.red);
        blueButton = findViewById(R.id.blue);
        startButton = findViewById(R.id.startButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        // Add buttons to a list
        buttons = new ArrayList<>();
        buttons.add(yellowButton);
        buttons.add(greenButton);
        buttons.add(redButton);
        buttons.add(blueButton);

        // Set initial visibility for buttons (make them unclickable initially)
        setButtonsClickable(false);

        // Set an OnClickListener on the start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startButton.setVisibility(View.GONE);
                updateScore(); // Update the score display

                // Generate a random sequence
                generateRandomSequence();

                // Start the sequence highlighting when the center button is pressed
                startSequenceHighlight();

                // After the sequence is ready, enable the buttons
                setButtonsClickable(true);
            }
        });

        // Set OnClickListeners for each button
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(yellowButton);
            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(greenButton);
            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(redButton);
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(blueButton);
            }
        });
    }

    private void generateRandomSequence() {
        sequence = new ArrayList<>();
        Random random = new Random();

        // Create a sequence of 4 buttons with possible repeats
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(buttons.size()); // Randomly pick a button
            sequence.add(buttons.get(index)); // Add it to the sequence
        }
    }

    private void startSequenceHighlight() {
        final Handler handler = new Handler();
        final int delay = 500; // 500ms delay between highlights

        for (int i = 0; i < sequence.size(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    highlightButton(sequence.get(index));

                    // Reset the button after a delay
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resetButton(sequence.get(index));
                        }
                    }, delay);
                }
            }, delay * i); // Add a delay between each button highlight
        }
    }

    private void highlightButton(ImageButton button) {
        // Simulate pressing the button to show the ripple effect
        button.setPressed(true);

        // Create a delay to simulate the button press duration
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Release the button after a short delay to end the ripple effect
                button.setPressed(false);
            }
        }, 200); // Ripple duration (200ms)
    }

    // Reset the button to its original state (no need to change the background)
    private void resetButton(ImageButton button) {
        // No need to reset the button's background as it's handled by the ripple effect
        button.setPressed(false);
    }

    private void setButtonsClickable(boolean clickable) {
        // Set each button's clickable state based on the parameter
        yellowButton.setClickable(clickable);
        greenButton.setClickable(clickable);
        redButton.setClickable(clickable);
        blueButton.setClickable(clickable);
    }

    private void checkButton(ImageButton pressedButton) {
        if (currentStep < sequence.size()) {
            ImageButton targetButton = sequence.get(currentStep);

            if (pressedButton == targetButton) {
                // Correct button pressed, progress to the next step
                currentStep++;
                score++; // Increase the score
                updateScore(); // Update the score display
                highlightButton(pressedButton);  // Show the ripple effect for the pressed button
                if (currentStep == sequence.size()) {
                    // Sequence completed, show the start button again
                    startButton.setVisibility(View.VISIBLE);
                    currentStep = 0; // Reset the sequence

                    // Disable the buttons again after sequence completion
                    setButtonsClickable(false);
                }
            } else {
                // Wrong button pressed, reset the game
                currentStep = 0; // Reset the sequence progress
                setButtonsClickable(false); // Disable buttons
                startButton.setVisibility(View.VISIBLE); // Show the start button again
                Intent intent = new Intent(this, EnterPlayerActivity.class);
                intent.putExtra("score", score); // Pass score to the next activity
                startActivity(intent);
            }
        }
    }

    private void updateScore() {
        // Update the score display
        scoreTextView.setText("Score: " + score);
    }
}