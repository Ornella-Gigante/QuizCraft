package es.ifp.quizcraft;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "questions_table")
public class Questions {

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "opA")
    private String opA;
    @ColumnInfo(name = "opB")
    private String opB;
    @ColumnInfo(name = "opC")
    private String opC;
    @ColumnInfo(name = "opD")
    private String opD;
    @ColumnInfo(name = "answer")
    private int answer;



}
