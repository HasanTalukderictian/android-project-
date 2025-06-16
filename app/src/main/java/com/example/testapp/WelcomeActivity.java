package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView logoImage;
    private TextView appName;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        logoImage = findViewById(R.id.logoImage);
        appName = findViewById(R.id.appName);
        progressBar = findViewById(R.id.progressBar);

        // Start zoom-in animation on logo
        Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        logoImage.startAnimation(zoomIn);

        // Fade in app name and progress bar after delay
        new Handler().postDelayed(() -> {
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
            appName.setVisibility(TextView.VISIBLE);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            appName.startAnimation(fadeIn);
            progressBar.startAnimation(fadeIn);

            // Animate progress from 0 to 100
            progressBar.setMax(100);
            progressBar.setProgress(0);

            Handler progressHandler = new Handler();
            final int[] progressStatus = {0};

            progressHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressStatus[0] <= 100) {
                        progressBar.setProgress(progressStatus[0]);
                        progressStatus[0]++;
                        progressHandler.postDelayed(this, 30); // 100 * 30ms = ~3 seconds
                    } else {
                        // Go to MainActivity after progress completes
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 30);

        }, 1000); // 1-second delay before showing app name and progress
    }
}
