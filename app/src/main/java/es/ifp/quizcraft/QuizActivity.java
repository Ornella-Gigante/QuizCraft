package es.ifp.quizcraft;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;
    TextView txtQuestion;
    TextView textViewScore;
    TextView textViewQuestionCount;
    TextView textViewQuestionCountDownTimer;
    TextView textViewCorrect;
    TextView textViewWrong;
    RadioButton rb1, rb2, rb3, rb4;
    RadioGroup rbGroup;
    Button buttonNext;
    boolean answered = false;
    List<Questions> questList;
    Questions currentQ;
    private int questionCounter = 0;
    private int questionTotalCount = 0;
    private int correctAns = 0;
    private int wrongAns = 0;

    private int FLAG = 0;
    private PlayAudioForAnswers playAudioForAnswers;

    // CAMBIO: Timer global de 60 segundos para todo el quiz
    private static final long COUNTDOWN_IN_MILLIS = 60000; // 60 segundos
    private CountDownTimer countDownTimer;
    private long timeLeftinMillis;

    private int textColorof; // Color original del timer

    private static final String[] INCORRECT_FEEDBACKS = {
            "Not quite, maybe next time",
            "Incorrect! Try again",
            "Oops, that wasn't it",
            "Keep trying!",
            "Wrong answer, don't give up!",
            "Try again, adventurer!",
    };

    private static final String[] CORRECT_FEEDBACKS = {
            "Correct! Well done!",
            "Nice job!",
            "Awesome! You nailed it!",
            "That's right! Keep it up!",
            "You got it!",
            "Great work!",
            "You rock!",
            "Bingo! That’s the one!"
    };

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

        textViewCorrect.setText("Correctas: 0");
        textViewWrong.setText("Incorrectas: 0");
        textViewScore.setText("Score: 0");
        textViewQuestionCount.setText("Pregunta 1/1");

        playAudioForAnswers = new PlayAudioForAnswers(this);

        // Inicializa el color original del timer después de asociar el TextView
        textColorof = textViewQuestionCountDownTimer.getCurrentTextColor();

        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        questionViewModel.getAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                fetchContent(questions);
            }
        });
    }

    private void fetchContent(List<Questions> questions) {
        questList = questions;
        questionTotalCount = questList.size();
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
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionTotalCount);

            // ELIMINADO: No reiniciar el timer aquí
            // timeLeftinMillis = COUNTDOWN_IN_MILLIS;
            // startCountDown();

        } else {
            // Al terminar el quiz, lanza la pantalla de score final
            finishQuiz();
        }
    }

    // INICIO DEL TIMER GLOBAL SOLO UNA VEZ
    private void startQuiz() {
        setQuestionsView();

        // Timer global de 60 segundos para todo el quiz
        timeLeftinMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        quizOperation();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftinMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftinMillis = 0;
                updateCountDownText();
                // Si el tiempo llega a cero, termina el quiz y muestra el score actual
                finishQuiz();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftinMillis / 1000) / 60;
        int seconds = (int) (timeLeftinMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewQuestionCountDownTimer.setText(timeFormatted);

        if (timeLeftinMillis <= 0) {
            textViewQuestionCountDownTimer.setTextColor(Color.RED);
            FLAG = 3;
            playAudioForAnswers.setAudioforAnswers(FLAG);
        } else {
            textViewQuestionCountDownTimer.setTextColor(textColorof);
        }
    }

    private void finishQuiz() {
        // Cancela el timer si sigue corriendo
        if (countDownTimer != null) countDownTimer.cancel();
        Intent intent = new Intent(QuizActivity.this, FinalScoreActivity.class);
        intent.putExtra("SCORE", correctAns);
        intent.putExtra("TOTAL", questionTotalCount);
        startActivity(intent);
        finish();
    }

    private String getRandomFeedback() {
        int idx = (int) (Math.random() * INCORRECT_FEEDBACKS.length);
        return INCORRECT_FEEDBACKS[idx];
    }

    private String getRandomMockAnswer() {
        int idx = (int) (Math.random() * CORRECT_FEEDBACKS.length);
        return CORRECT_FEEDBACKS[idx];
    }

    private void quizOperation() {
        answered = true;

        // Si el usuario responde antes de tiempo, cancela el timer solo si es la última pregunta
        if (questionCounter >= questionTotalCount) {
            if (countDownTimer != null) countDownTimer.cancel();
        }

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1;

        checkSolution(answerNr, rbselected);
    }

    private void checkSolution(int answerNr, RadioButton rbselected) {
        boolean isCorrect = (currentQ.getAnswer() == answerNr);
        RadioButton correctRb = getCorrectRadioButton(currentQ.getAnswer());

        if (isCorrect) {
            FLAG = 1;
            playAudioForAnswers.setAudioforAnswers(FLAG);
            rbselected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
            correctAns++;
            textViewCorrect.setText("Correct: " + correctAns);
            textViewScore.setText("Score: " + correctAns);
            rbselected.setText(getRandomMockAnswer());
            rbselected.postDelayed(this::setQuestionsView, 1200);
        } else {
            FLAG = 2;
            playAudioForAnswers.setAudioforAnswers(FLAG);
            changetoIncorrectColor(rbselected);
            wrongAns++;
            textViewWrong.setText("Incorrect: " + wrongAns);
            rbselected.setText(getRandomFeedback());
            correctRb.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
            correctRb.setText(currentQ.getCorrectOptionText());
            rbselected.postDelayed(() -> {
                rbselected.setText(getRandomMockAnswer());
                correctRb.setText(getRandomMockAnswer());
                setQuestionsView();
            }, 1600);
        }
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
        rbselected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
    }

    void setupUI() {
        txtQuestion = findViewById(R.id.txtQuestionContainer);
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestions);
        textViewCorrect = findViewById(R.id.textCorrect);
        textViewWrong = findViewById(R.id.txtWrong);
        textViewQuestionCountDownTimer = findViewById(R.id.txtTimer); // Asegúrate de tener este ID en tu XML
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        rbGroup = findViewById(R.id.radio_group);
        buttonNext = findViewById(R.id.button_Next);
    }
}
