package com.example.mad_finalgame;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageButton yellowButton, greenButton, redButton, blueButton;
    private Button startButton;
    private List<ImageButton> buttons;
    private List<ImageButton> sequence;

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

        // Add buttons to a list
        buttons = new ArrayList<>();
        buttons.add(yellowButton);
        buttons.add(greenButton);
        buttons.add(redButton);
        buttons.add(blueButton);

        // Set an OnClickListener on the start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a random sequence
                generateRandomSequence();

                // Start the sequence highlighting when the center button is pressed
                startSequenceHighlight();
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
}