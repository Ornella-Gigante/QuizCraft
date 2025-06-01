package es.ifp.quizcraft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinalScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_score_dialog);

        // Recibe el score y total desde el Intent
        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 0);

        TextView txtFinalScore = findViewById(R.id.txtFinalScore);
        txtFinalScore.setText("You got " + score + " out of " + total + " correct!");

        Button btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(v -> {
            // Reinicia el quiz (puedes volver a la MainActivity o QuizActivity)
            Intent intent = new Intent(FinalScoreActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();
        });
    }

}