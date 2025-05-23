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

    private static final RoomDatabase.Callback RoomDBCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(() -> {
                        QuestionDao dao = INSTANCE.questionDao();

                        // Pregunta 1 (existente)
                        dao.insert(new Questions(
                                "What is Android?",
                                "OS",
                                "Browser",
                                "Software",
                                "Hard Drive",
                                1
                        ));

                        // Pregunta 2
                        dao.insert(new Questions(
                                "What is the primary language for Android development?",
                                "Java",
                                "Kotlin",
                                "C++",
                                "Python",
                                2 // Respuesta correcta: Kotlin
                        ));

                        // Pregunta 3
                        dao.insert(new Questions(
                                "What does Room provide in Android?",
                                "Local database abstraction",
                                "Networking",
                                "Image loading",
                                "GPS tracking",
                                1 // Respuesta correcta: Local database abstraction
                        ));

                        // Pregunta 4
                        dao.insert(new Questions(
                                "What is LiveData used for?",
                                "Real-time UI updates",
                                "File storage",
                                "Audio playback",
                                "Video recording",
                                1 // Respuesta correcta: Real-time UI updates
                        ));

                        // Pregunta 5
                        dao.insert(new Questions(
                                "Which component manages app navigation?",
                                "Navigation Controller",
                                "RecyclerView",
                                "SharedPreferences",
                                "Retrofit",
                                1 // Respuesta correcta: Navigation Controller
                        ));
                    });
                }
            };

};

