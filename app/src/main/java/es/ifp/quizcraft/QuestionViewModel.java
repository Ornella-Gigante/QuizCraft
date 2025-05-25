package es.ifp.quizcraft;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {

    private QuestionsRepository mRepository;
    private LiveData<List<Questions>> mAllQuestions;

    public QuestionViewModel(Application application){

        super(application);

        mRepository = new QuestionsRepository(application);
        mAllQuestions = mRepository.getAllQuestions();
    }

    LiveData<List<Questions>> getAllQuestions(){

        return mAllQuestions;
    }
}
