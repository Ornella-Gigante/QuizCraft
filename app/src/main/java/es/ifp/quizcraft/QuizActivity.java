package es.ifp.quizcraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.Collections;
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
    Button buttonNext;
    boolean answered = false;
    List<Questions> questList;
    Questions currentQ;
    private int questionCounter =0;
    private int questionTotalCount;


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

        startQuiz();

    }


    private void setQuestions(){

        rbGroup.clearCheck();

        questionTotalCount = questList.size();

        Collections.shuffle(questList);
        if(questionCounter < questionTotalCount -1){

            currentQ = questList.get(questionCounter);
            txtQuestion.setText(currentQ.getQuestion());
            rb1.setText(currentQ.getOpA());
            rb1.setText(currentQ.getOpB());
            rb1.setText(currentQ.getOpC());
            rb1.setText(currentQ.getOpD());

            questionCounter++;
            answered = false;

            buttonNext.setText("Confirm");
            textViewQuestionCount.setText("Questions : " + questionCounter +"/" + questionTotalCount);


        }else {

            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(intent);
        }
    }


    private void startQuiz() {

        setQuestions();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!answered){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOperation();
                    }else{

                        Toast.makeText(QuizActivity.this, "Please select answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

private void quizOperation(){

        answered = true;

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1;

        checkSolution();
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
        buttonNext = findViewById(R.id.button_Next);
    }
}