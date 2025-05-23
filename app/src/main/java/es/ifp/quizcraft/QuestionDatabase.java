package es.ifp.quizcraft;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Questions.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {

    private static volatile QuestionDatabase INSTANCE;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(1);

    public abstract QuestionDao questionDao();

    public static synchronized QuestionDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            QuestionDatabase.class,
                            "questions_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback)
                    .build();
        }
        return INSTANCE;
    }

    // Callback usando ExecutorService
    private static final RoomDatabase.Callback RoomDBCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    // Usar el Executor para operaciones en background
                    databaseWriteExecutor.execute(() -> {
                        QuestionDao dao = INSTANCE.questionDao();
                        // Insertar datos iniciales
                        dao.insert(new Questions(
                                "What is Android?",
                                "OS",
                                "Browser",
                                "Software",
                                "Hard Drive",
                                1
                        ));
                    });
                }
            };
}
