package es.ifp.quizcraft;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Questions.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {

    private static QuestionDatabase INSTANCE;

    public abstract QuestionDao questionDao();



    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
