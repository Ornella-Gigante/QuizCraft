package es.ifp.quizcraft;

import android.content.Context;
import android.media.MediaPlayer;

public class PlayAudioForAnswers {

    private Context mContext;
    private MediaPlayer mediaPlayer;


    public PlayAudioForAnswers(Context mContext) {
        this.mContext = mContext;
    }


    public void setAudioforAnswers(final int flag){

        switch (flag){

            case 1:
                int correctAudio = R.raw.correct;
                playMusic(correctAudio);
            break; 

            case 2:
                int wrongAudio = R.raw.wrong;
                playMusic(wrongAudio);
            break;
        }
    }

    private void playMusic(int correctAudio) {

    }
}
