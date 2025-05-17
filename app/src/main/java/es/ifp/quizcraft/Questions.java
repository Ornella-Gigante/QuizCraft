package es.ifp.quizcraft;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "questions_table")
public class Questions {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    private String question;
    private String opA;
    private String opB;
    private String opC;
    private int answer;



}
