package es.ifp.quizcraft;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionsRepository {


    private QuestionDao mQuestionDao;

    private LiveData<List<Questions>> mAllQuestions;


    public QuestionsRepository(Application application){
        
        QuestionDatabase db = QuestionDatabase.getInstance(application);
        mQuestionDao = db.questionDao();
        mAllQuestions = mQuestionDao.getAllQuestions();
    }

}
