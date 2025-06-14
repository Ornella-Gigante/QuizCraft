package es.ifp.quizcraft;

import android.content.Context;
import android.media.MediaPlayer;

public class PlayAudioForAnswers {

    private Context mContext;
    private MediaPlayer mediaPlayer;

    public PlayAudioForAnswers(Context mContext) {
        this.mContext = mContext;
    }

    // CORREGIDO: Quitar 'static'
    public void setAudioforAnswers(final int flag) {
        switch (flag) {
            case 1:
                int correctAudio = R.raw.correct;
                playMusic(correctAudio);
                break;
            case 2:
                int wrongAudio = R.raw.wrong;
                playMusic(wrongAudio);
                break;
            case 3:
                int timeAudio = R.raw.timetick;
                playMusic(timeAudio);
                break;
        }
    }

    private void playMusic(int audioFile) {
        // Libera el MediaPlayer anterior si existe
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = MediaPlayer.create(mContext, audioFile);
        if (mediaPlayer == null) return;

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
    }
}
