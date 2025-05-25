package es.ifp.quizcraft;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionsRepository {


    private QuestionDao mQuestionDao;

    private LiveData<List<Questions>> mAllQuestions;


}
