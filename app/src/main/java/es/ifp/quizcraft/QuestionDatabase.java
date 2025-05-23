package es.ifp.quizcraft;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Questions.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {

    private static QuestionDatabase INSTANCE;

    public abstract QuestionDao questionDao();

   public static synchronized QuestionDatabase getInstance(final Context context){

       if(INSTANCE == null){
           INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
           QuestionDatabase.class, "questions_database")
                   .fallbackToDestructiveMigration()
                   .addCallback(RoomDBCallback)
                   .build();
       }

       return INSTANCE;
   }


   private static RoomDatabase.Callback RoomDBCallback = new RoomDatabase.Callback(){

       @Override
       public void onCreate(@NonNull SupportSQLiteDatabase db){
           super.onCreate(db);
       }
   };

}
