package es.ifp.quizcraft;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionViewModel {

    private QuestionsRepository mRepository;
    private LiveData<List<Questions>> mAllQuestions;

    public QuestionViewModel(Application application){

        mRepository = new QuestionsRepository(application);
        mAllQuestions = mRepository.getAllQuestions();
    }
}
