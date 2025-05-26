package es.ifp.quizcraft;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.nio.file.Path;
import java.util.List;

public class QuizActivity extends AppCompatActivity {


    private QuestionViewModel questionViewModel;
    TextView txtQuestion;
    TextView textViewScore;
    TextView textViewQuestionCount;
    TextView textViewQuestionCountDownTimer;
    TextView textViewCorrect;
    TextView textViewWrong;
    RadioButton rb1, rb2,rb3,rb4;
    RadioGroup rbGroup;
    Button btNext;
    boolean answered = false;
    List<Questions> questList;
    Questions currentQ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        
        setupUI();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        questionViewModel.getAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {

                Toast.makeText(QuizActivity.this, "Get It:", Toast.LENGTH_SHORT).show();
           
                fetchContent(questions);
            }
        });
    }

    private void fetchContent(List<Questions> questions) {

        questList = questions;

        //startQuiz() method

    }


    private void setQuestions(){

    }

    void setupUI() {
        txtQuestion = findViewById(R.id.txtQuestionContainer);
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestions);
        textViewCorrect = findViewById(R.id.textCorrect);
        textViewWrong = findViewById(R.id.txtWrong);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        rbGroup = findViewById(R.id.radio_group);
        btNext = findViewById(R.id.button_Next);
    }
}