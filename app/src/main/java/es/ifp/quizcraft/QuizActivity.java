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
import androidx.core.content.ContextCompat;
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
    private void setQuestionsView() {
        rbGroup.clearCheck();

        questionTotalCount = questList.size();

        if (questionCounter < questionTotalCount) {
            currentQ = questList.get(questionCounter);

            txtQuestion.setText(currentQ.getQuestion());
            rb1.setText(currentQ.getOpA());
            rb2.setText(currentQ.getOpB());
            rb3.setText(currentQ.getOpC());
            rb4.setText(currentQ.getOpD());

            // Restaura backgrounds
            rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
            rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
            rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
            rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));

            questionCounter++;
            answered = false;

            buttonNext.setText("Confirm");
            textViewQuestionCount.setText("Questions : " + questionCounter + "/" + questionTotalCount);

        } else {
            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(intent);
        }
    }


    private void startQuiz() {

        setQuestionsView();

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

        checkSolution(answerNr, rbselected);
    }

    private void checkSolution(int answerNr, RadioButton rbselected) {
        boolean isCorrect = (currentQ.getAnswer() == answerNr);

        // Cambia color según resultado
        if (isCorrect) {
            rbselected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
            // Solo la seleccionada cambia a mock
            rbselected.setText(getRandomMockAnswer());
        } else {
            changetoIncorrectColor(rbselected);
            // Cambia la seleccionada y la correcta a mock
            rbselected.setText(getRandomMockAnswer());
            getCorrectRadioButton(currentQ.getAnswer()).setText(getRandomMockAnswer());
        }

        // Espera antes de pasar a la siguiente pregunta
        rbselected.postDelayed(new Runnable() {
            @Override
            public void run() {
                setQuestionsView();
            }
        }, 1200);
    }

    private RadioButton getCorrectRadioButton(int correctAnswer) {
        switch (correctAnswer) {
            case 1: return rb1;
            case 2: return rb2;
            case 3: return rb3;
            case 4: return rb4;
            default: return null;
        }
    }


    private void changetoIncorrectColor(RadioButton rbselected) {

        rbselected.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_wrong));
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

    private static final String[] MOCK_ANSWERS = {
            "42 is always the answer!",
            "Try again, adventurer!",
            "QuizCraft rocks!",
            "Maybe next time!",
            "Keep going!",
            "You got this!",
            "Surprise option!",
            "Mystery answer!"
    };

    private String getRandomMockAnswer() {
        int idx = (int) (Math.random() * MOCK_ANSWERS.length);
        return MOCK_ANSWERS[idx];
    }

}